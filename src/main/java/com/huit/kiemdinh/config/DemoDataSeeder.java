package com.huit.kiemdinh.config;

import com.huit.kiemdinh.model.*;
import com.huit.kiemdinh.security.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Order(2)
public class DemoDataSeeder implements ApplicationRunner {
    private static final String PLAN_ID = "KH001";
    private final MongoTemplate mongo;
    private final AuthService auth;
    private final boolean enabled;
    private final DemoDataState dataState;

    public DemoDataSeeder(MongoTemplate mongo, AuthService auth, DemoDataState dataState,
                          @Value("${app.seed-demo-data:true}") boolean enabled) {
        this.mongo = mongo;
        this.auth = auth;
        this.dataState = dataState;
        this.enabled = enabled;
    }

    @Override
    public void run(ApplicationArguments args) {
        dataState.markNotReady();
        if (!enabled) {
            System.out.println("[SEED] app.seed-demo-data=false -> bỏ qua dữ liệu demo.");
            dataState.markReady();
            return;
        }
        try {
            seedRolesAndUsers();
            boolean repair = needsRepair();
            if (!repair) {
                ensureIndexes();
                validateSeed();
                printSummary();
                dataState.markReady();
                return;
            }
            System.out.println("[SEED] Dữ liệu nghiệp vụ thiếu/không đồng bộ -> tạo lại bộ demo chuẩn trên các collection nghiệp vụ.");
            clearOperationalDemoData();
            seedMoet();
            seedCtdt();
            seedPlan();
            seedGroupsAndAssignments();
            seedCommonEvidence();
            seedCriterionEvidenceAndReports();
            seedGroupReports();
            seedMasterReport();
            seedReportAssignmentsAndParts();
            seedProgress();
            seedEvaluations();
            seedActivityLogs();
            ensureIndexes();
            validateSeed();
            printSummary();
            dataState.markReady();
        } catch (Exception e) {
            System.err.println("[SEED ERROR] Không thể chuẩn bị dữ liệu HeThongKiemDinh29: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private boolean needsRepair() {
        try {
            // Các collection demo cốt lõi phải đúng số lượng chuẩn.
            // Dùng != thay vì < để phát hiện cả dữ liệu cũ/thừa (ví dụ còn 6 nhóm).
            if (mongo.getCollection("boTieuChuan").countDocuments() != 1) return true;
            if (mongo.getCollection("tieuChuan").countDocuments() != 8) return true;
            if (mongo.getCollection("tieuChi").countDocuments() != 52) return true;
            if (mongo.getCollection("chuongTrinhDaoTao").countDocuments() != 1) return true;
            if (mongo.getCollection("nhomTieuChi").countDocuments() != 5) return true;
            if (mongo.getCollection("phanCong").countDocuments() != 60) return true;
            if (mongo.getCollection("minhChung").countDocuments() != 52) return true;
            if (mongo.getCollection("minhChungChung").countDocuments() != 10) return true;
            if (mongo.getCollection("baoCaoTieuChi").countDocuments() != 52) return true;
            if (mongo.getCollection("baoCaoNhom").countDocuments() != 8) return true;
            if (mongo.getCollection("baoCaoTuDanhGia").countDocuments() != 1) return true;
            if (mongo.getCollection("baoCaoPhan").countDocuments() != 3) return true;
            if (mongo.getCollection("phanCongBaoCao").countDocuments() != 3) return true;

            ChuongTrinhDaoTao c = mongo.findById("CTDT001", ChuongTrinhDaoTao.class);
            if (c == null || c.getPhienBan() == null || c.getPhienBan().size() < 3) return true;
            for (PhienBanCTDT p : c.getPhienBan()) {
                if (p == null || p.getNamPhienBan() == null || p.getNamPhienBan() % 2 != 0) return true;
            }

            BoTieuChuan b = mongo.findById("MOET", BoTieuChuan.class);
            return b == null || !Objects.equals(b.getTongSoTieuChuan(), 8) || !Objects.equals(b.getTongSoTieuChi(), 52);
        } catch (Exception ex) {
            System.err.println("[SEED] Phát hiện dữ liệu cũ không đọc được bằng model hiện tại: " + ex.getMessage());
            return true;
        }
    }

    private void clearOperationalDemoData() {
        String[] collections = {
                "chuongTrinhDaoTao","boTieuChuan","tieuChuan","tieuChi","nhomTieuChi",
                "phanCong","minhChung","minhChungChung","danhGiaMinhChung",
                "baoCaoTieuChi","baoCaoNhom","baoCaoTuDanhGia","baoCaoPhan","phanCongBaoCao",
                "danhGiaTieuChi","tienDo","thongBao","lichSuThaoTac","keHoachTuDanhGia"
        };
        for (String c : collections) {
            if (mongo.collectionExists(c)) mongo.getCollection(c).deleteMany(new org.bson.Document());
        }
    }

    private void seedRolesAndUsers() {
        String[][] roles = {
                {"VT001","Quản trị","Quản trị hệ thống, người dùng, CTĐT, nhóm và phân công"},
                {"VT002","Hội đồng tự đánh giá","Góp ý, duyệt báo cáo và đánh giá tiêu chí"},
                {"VT003","Trưởng nhóm tiêu chí","Điều hành nhóm, phân công tiêu chí và duyệt cấp nhóm"},
                {"VT004","Thành viên nhóm","Thu thập minh chứng và viết báo cáo tiêu chí; có thể là Thư ký nhóm"},
                {"VT005","Thư ký chương trình","Lập kế hoạch, quản lý minh chứng và tổng hợp hồ sơ"},
                {"VT006","Bộ phận khảo thí","Phê duyệt kế hoạch và theo dõi hoạt động tự đánh giá"},
                {"VT007","Nhân sự phụ trách báo cáo","Nhân sự ngoài nhóm phụ trách Phần 1, Phần 3 hoặc Phần 4"}
        };
        for (String[] r : roles) {
            if (mongo.findById(r[0], VaiTro.class) == null) {
                VaiTro v = new VaiTro();
                v.setId(r[0]); v.setTenVaiTro(r[1]); v.setMoTa(r[2]);
                mongo.save(v);
            }
        }

        List<String[]> users = new ArrayList<>();
        users.add(new String[]{"ND001","Nguyễn Văn Quản Trị","admin@huit.edu.vn","VT001","Trung tâm Công nghệ Thông tin","Thạc sĩ"});
        users.add(new String[]{"ND002","Trần Quốc Tuấn","admin2@huit.edu.vn","VT001","Trung tâm Công nghệ Thông tin","Thạc sĩ"});
        users.add(new String[]{"ND003","Trần Thanh Bình","hoidong1@huit.edu.vn","VT002","Khoa Công nghệ Thông tin","Tiến sĩ"});
        users.add(new String[]{"ND004","Nguyễn Văn Hùng","hoidong2@huit.edu.vn","VT002","Khoa Công nghệ Thông tin","Tiến sĩ"});
        users.add(new String[]{"ND005","Trương Thị Kim Oanh","hoidong3@huit.edu.vn","VT002","Khoa Công nghệ Thông tin","Tiến sĩ"});
        users.add(new String[]{"ND006","Nguyễn Thị Lan","thuky@huit.edu.vn","VT005","Khoa Công nghệ Thông tin","Thạc sĩ"});
        users.add(new String[]{"ND007","Phạm Thị Thanh Hương","thuky2@huit.edu.vn","VT005","Khoa Công nghệ Thông tin","Thạc sĩ"});
        users.add(new String[]{"ND008","Võ Quốc Huy","khaothi@huit.edu.vn","VT006","Trung tâm Quản lý chất lượng","Thạc sĩ"});
        users.add(new String[]{"ND009","Trần Quốc Việt","khaothi2@huit.edu.vn","VT006","Trung tâm Quản lý chất lượng","Thạc sĩ"});

        String[] leaders={"Lê Hoàng Nam","Nguyễn Hoàng Phúc","Trần Thị Ngọc Mai","Lê Quốc Bảo","Nguyễn Thành Long"};
        for(int i=0;i<5;i++) users.add(new String[]{String.format("ND%03d",10+i),leaders[i],"truongnhom"+(i+1)+"@huit.edu.vn","VT003","Khoa Công nghệ Thông tin","Tiến sĩ"});

        String[] secretaries={"Phạm Thu Hà","Nguyễn Thị Thu Trang","Trần Minh Quân","Phan Thị Mỹ Linh","Võ Thị Thanh Thảo"};
        for(int i=0;i<5;i++) users.add(new String[]{String.format("ND%03d",15+i),secretaries[i],"thukynhom"+(i+1)+"@huit.edu.vn","VT004","Khoa Công nghệ Thông tin","Thạc sĩ"});

        String[] members={
                "Nguyễn Quốc Anh","Trần Thị Mai Anh","Phạm Minh Khoa","Lê Thị Quỳnh Như","Nguyễn Đức Thịnh",
                "Trần Hoài Nam","Nguyễn Thị Kim Ngân","Phạm Quốc Trung","Lê Minh Thành","Võ Thị Bích Ngọc",
                "Nguyễn Anh Tuấn","Trần Thị Thu Uyên","Lê Minh Trí","Phạm Thị Thanh Trúc","Nguyễn Quốc Cường",
                "Trần Gia Bảo","Nguyễn Thị Thùy Dương","Võ Minh Hải","Lê Thị Thanh Nga","Phạm Hoàng Việt",
                "Nguyễn Nhật Minh","Trần Thị Khánh Linh","Lê Quốc Khải","Nguyễn Thị Thu Phương","Phạm Thanh Tâm"
        };
        for(int i=0;i<members.length;i++) users.add(new String[]{String.format("ND%03d",20+i),members[i],String.format("tv%02d@huit.edu.vn",i+1),"VT004","Khoa Công nghệ Thông tin","Thạc sĩ"});

        users.add(new String[]{"ND045","Trần Quốc Hưng","truongnhomduphong1@huit.edu.vn","VT003","Khoa Công nghệ Thông tin","Tiến sĩ"});
        users.add(new String[]{"ND046","Nguyễn Hoàng Anh","truongnhomduphong2@huit.edu.vn","VT003","Khoa Công nghệ Thông tin","Tiến sĩ"});

        String[] reserve={"Nguyễn Thị Bảo Châu","Trần Minh Nhật","Lê Thị Kim Chi","Phạm Thành Công","Nguyễn Thị Minh Thư","Võ Quốc Thịnh","Trần Thị Thùy Trang","Nguyễn Minh Phát","Phạm Thị Thanh Vân"};
        for(int i=0;i<reserve.length;i++) users.add(new String[]{String.format("ND%03d",47+i),reserve[i],String.format("duphong%02d@huit.edu.vn",i+1),"VT004","Khoa Công nghệ Thông tin","Thạc sĩ"});

        users.add(new String[]{"ND056","Nguyễn Thị Hoài An","baocao01@huit.edu.vn","VT007","Trung tâm Quản lý chất lượng","Thạc sĩ"});
        users.add(new String[]{"ND057","Trần Minh Châu","baocao02@huit.edu.vn","VT007","Phòng Đào tạo","Thạc sĩ"});
        users.add(new String[]{"ND058","Phạm Thị Ngọc Hương","baocao03@huit.edu.vn","VT007","Khoa Công nghệ Thông tin","Thạc sĩ"});
        users.add(new String[]{"ND059","Lê Hoàng Khôi","baocao04@huit.edu.vn","VT007","Khoa Công nghệ Thông tin","Tiến sĩ"});
        users.add(new String[]{"ND060","Võ Thị Thanh Uyên","baocao05@huit.edu.vn","VT007","Trung tâm Quản lý chất lượng","Thạc sĩ"});
        users.add(new String[]{"ND061","Nguyễn Quốc Thái","baocao06@huit.edu.vn","VT007","Phòng Đào tạo","Thạc sĩ"});

        for (String[] r : users) ensureUser(r);
    }

    private void ensureUser(String[] r) {
        Query byEmail = Query.query(Criteria.where("email").is(r[2]));
        NguoiDung byId = mongo.findById(r[0], NguoiDung.class);
        NguoiDung byMail = mongo.findOne(byEmail, NguoiDung.class);
        if (byId != null || byMail != null) return;
        NguoiDung u = new NguoiDung();
        u.setId(r[0]); u.setHoTen(r[1]); u.setEmail(r[2]); u.setMatKhau(auth.encodePassword("123456"));
        u.setSoDienThoai(String.format("09%08d", Integer.parseInt(r[0].substring(2))));
        u.setKhoa(r[4]); u.setTrinhDo(r[5]); u.setTrangThai("Hoạt động");
        u.setPhanQuyen(new ArrayList<>(List.of(new PhanQuyen(r[3]))));
        mongo.save(u);
    }

    private static final String[][] STANDARDS = {
            {"TC1","1","Mục tiêu và chuẩn đầu ra của chương trình đào tạo"},
            {"TC2","2","Cấu trúc và nội dung chương trình đào tạo"},
            {"TC3","3","Hoạt động dạy và học"},
            {"TC4","4","Đánh giá kết quả học tập"},
            {"TC5","5","Đội ngũ giảng viên, nghiên cứu viên"},
            {"TC6","6","Các dịch vụ hỗ trợ người học"},
            {"TC7","7","Cơ sở hạ tầng, cơ sở vật chất và trang thiết bị"},
            {"TC8","8","Đầu ra và kết quả đầu ra"}
    };

    private static final String[][] CRITERIA = {
            {"TC1","1","Mục tiêu của chương trình đào tạo được xác định rõ ràng, phù hợp và gắn kết với sứ mạng, tầm nhìn, chiến lược phát triển của cơ sở đào tạo; phù hợp với mục tiêu của giáo dục đại học.","Không"},
            {"TC1","2","Chuẩn đầu ra của chương trình đào tạo được xây dựng rõ ràng; phù hợp với sứ mạng, tầm nhìn và mục tiêu chiến lược của cơ sở đào tạo và được phổ biến đến các bên liên quan.","Không"},
            {"TC1","3","Chuẩn đầu ra của chương trình đào tạo phù hợp với Khung trình độ quốc gia Việt Nam và chuẩn chương trình đào tạo nhóm ngành.","Có"},
            {"TC1","4","Chuẩn đầu ra của tất cả các học phần được xây dựng phù hợp và tương thích với chuẩn đầu ra của chương trình đào tạo đã công bố.","Không"},
            {"TC1","5","Chuẩn đầu ra của chương trình đào tạo phản ánh rõ yêu cầu của các bên liên quan, đặc biệt là các bên liên quan bên ngoài.","Không"},
            {"TC1","6","Chuẩn đầu ra của chương trình đào tạo được đo lường, đánh giá tại thời điểm người học tốt nghiệp.","Có"},
            {"TC2","1","Bản mô tả chương trình đào tạo và đề cương các học phần có đủ thông tin, được cập nhật, phê duyệt và công bố công khai.","Không"},
            {"TC2","2","Cấu trúc và nội dung chương trình đào tạo được thiết kế và phát triển để bảo đảm người học đạt chuẩn đầu ra và có khối lượng học tập phù hợp.","Có"},
            {"TC2","3","Cấu trúc và nội dung chương trình đào tạo được thiết kế và phát triển dựa trên phản hồi và nhu cầu của các bên liên quan.","Không"},
            {"TC2","4","Đóng góp của từng học phần trong việc đạt được chuẩn đầu ra của chương trình đào tạo là rõ ràng.","Có"},
            {"TC2","5","Chương trình đào tạo có cấu trúc logic, trình tự hợp lý, có tính linh hoạt và tích hợp.","Không"},
            {"TC2","6","Cấu trúc và nội dung chương trình đào tạo thể hiện rõ các học phần bắt buộc, tự chọn, lý thuyết, thực hành, trải nghiệm và nghiên cứu khoa học.","Không"},
            {"TC2","7","Cấu trúc và nội dung chương trình đào tạo được rà soát, đánh giá và cải tiến theo quy trình, bảo đảm tính cập nhật.","Không"},
            {"TC3","1","Triết lý giáo dục của cơ sở đào tạo được tuyên bố rõ ràng, phổ biến đến các bên liên quan và truyền tải vào hoạt động dạy và học.","Không"},
            {"TC3","2","Hoạt động dạy và học được thiết kế tương thích với chuẩn đầu ra của chương trình đào tạo.","Có"},
            {"TC3","3","Hoạt động dạy và học thể hiện việc học tập chủ động và phát triển khả năng học tập suốt đời của người học.","Không"},
            {"TC3","4","Hoạt động dạy và học thúc đẩy người học đưa ra ý tưởng mới, sáng kiến, đổi mới sáng tạo và tinh thần khởi nghiệp.","Không"},
            {"TC3","5","Quá trình dạy và học được cải tiến thường xuyên để đáp ứng yêu cầu của thị trường lao động và chuẩn đầu ra.","Không"},
            {"TC4","1","Các phương pháp đánh giá kết quả học tập của người học đa dạng, tương thích với chuẩn đầu ra của chương trình đào tạo.","Không"},
            {"TC4","2","Có các quy định rõ ràng về đánh giá kết quả học tập, quy trình phúc khảo, được phổ biến và triển khai nhất quán.","Không"},
            {"TC4","3","Các tiêu chuẩn và quy trình đánh giá kết quả học tập, công nhận kết quả học tập và xét tốt nghiệp được phổ biến, triển khai nhất quán.","Không"},
            {"TC4","4","Các phương pháp đánh giá được thể hiện qua ma trận, tiêu chí, mức độ đánh giá, đáp án, thang điểm và kế hoạch đánh giá.","Không"},
            {"TC4","5","Các phương pháp đánh giá bảo đảm đo lường được mức độ đạt chuẩn đầu ra học phần và chuẩn đầu ra chương trình đào tạo.","Có"},
            {"TC4","6","Kết quả đánh giá được phản hồi kịp thời cho người học để cải thiện việc học tập.","Không"},
            {"TC4","7","Việc đánh giá kết quả học tập được định kỳ rà soát và cải tiến để bảo đảm đo lường chuẩn đầu ra.","Không"},
            {"TC5","1","Kế hoạch phát triển đội ngũ giảng viên, nghiên cứu viên được thực hiện nhằm bảo đảm số lượng và chất lượng.","Không"},
            {"TC5","2","Số lượng và chất lượng đội ngũ giảng viên, nghiên cứu viên đáp ứng yêu cầu thực hiện chương trình đào tạo; khối lượng công việc được đo lường và giám sát.","Có"},
            {"TC5","3","Năng lực của đội ngũ giảng viên, nghiên cứu viên được xác định, đánh giá và thông tin tới các bên liên quan trực tiếp.","Không"},
            {"TC5","4","Giảng viên, nghiên cứu viên được phân công nhiệm vụ phù hợp với trình độ, năng lực và kinh nghiệm.","Không"},
            {"TC5","5","Việc bổ nhiệm, thăng tiến dựa trên hệ thống đánh giá năng lực và kết quả thực hiện nhiệm vụ.","Không"},
            {"TC5","6","Trách nhiệm, quyền hạn và nghĩa vụ của giảng viên, nghiên cứu viên được xác định rõ ràng và phổ biến.","Không"},
            {"TC5","7","Nhu cầu đào tạo, bồi dưỡng và phát triển chuyên môn được xác định có hệ thống và được triển khai đáp ứng nhu cầu.","Không"},
            {"TC5","8","Công tác quản lý để đánh giá chất lượng giảng dạy, nghiên cứu khoa học và phục vụ cộng đồng được triển khai theo quy định.","Không"},
            {"TC6","1","Chính sách, tiêu chí và quy trình tuyển sinh được xác định rõ ràng, công bố công khai và cập nhật.","Có"},
            {"TC6","2","Năng lực đội ngũ cung cấp dịch vụ hỗ trợ được xác định rõ ràng và được đánh giá để bảo đảm phù hợp nhu cầu các bên liên quan.","Không"},
            {"TC6","3","Kế hoạch ngắn hạn và dài hạn đối với dịch vụ hỗ trợ người học được xây dựng và triển khai thực hiện.","Không"},
            {"TC6","4","Có hệ thống quản lý đào tạo phù hợp để giám sát, ghi nhận tiến độ, kết quả và khối lượng học tập của người học.","Không"},
            {"TC6","5","Có các hoạt động tư vấn học tập, ngoại khóa, cuộc thi và dịch vụ hỗ trợ giúp cải thiện việc học và khả năng có việc làm.","Không"},
            {"TC6","6","Các dịch vụ hỗ trợ người học được định kỳ đánh giá, đối sánh và cải tiến chất lượng.","Không"},
            {"TC7","1","Có hệ thống phòng làm việc, phòng học và các phòng chức năng với trang thiết bị phù hợp để triển khai chương trình đào tạo.","Không"},
            {"TC7","2","Các phòng thí nghiệm, phòng thực hành và trang thiết bị đầy đủ, cập nhật và được sử dụng hiệu quả.","Không"},
            {"TC7","3","Có thư viện, thư viện số và nguồn học liệu được cập nhật đáp ứng nhu cầu đào tạo, nghiên cứu khoa học.","Có"},
            {"TC7","4","Có hệ thống công nghệ thông tin, hạ tầng mạng và máy tính dễ tiếp cận, đáp ứng nhu cầu giảng dạy, nghiên cứu và quản lý.","Không"},
            {"TC7","5","Môi trường tâm lý, xã hội và cảnh quan thiên nhiên tạo thuận lợi cho đào tạo, nghiên cứu và sự thoải mái của người học.","Không"},
            {"TC7","6","Các tiêu chuẩn về môi trường, sức khỏe, an toàn được xác định và triển khai phù hợp.","Không"},
            {"TC7","7","Năng lực đội ngũ hỗ trợ về cơ sở vật chất và trang thiết bị được xác định và đánh giá đáp ứng nhu cầu.","Không"},
            {"TC7","8","Chất lượng cơ sở vật chất phục vụ chương trình đào tạo được định kỳ đánh giá và cải tiến.","Không"},
            {"TC8","1","Tỉ lệ tốt nghiệp, thôi học và thời gian tốt nghiệp trung bình của người học được xác lập, giám sát, đối sánh để cải tiến chất lượng.","Không"},
            {"TC8","2","Tỉ lệ việc làm, tự tạo việc làm, khởi nghiệp và học tập nâng cao trình độ của người học tốt nghiệp được xác lập, giám sát và đối sánh.","Có"},
            {"TC8","3","Hoạt động nghiên cứu khoa học và sản phẩm sáng tạo của người học, giảng viên, nghiên cứu viên được xác lập, giám sát và đối sánh.","Không"},
            {"TC8","4","Dữ liệu về mức độ người học đạt chuẩn đầu ra của chương trình đào tạo được xác lập và giám sát để cải tiến chất lượng.","Không"},
            {"TC8","5","Mức độ hài lòng của các bên liên quan được xác lập, giám sát, đối sánh để cải tiến chất lượng.","Không"}
    };


    private void seedMoet() {
        BoTieuChuan b = new BoTieuChuan();
        b.setId("MOET"); b.setMaBoTieuChuan("MOET"); b.setTenBoTieuChuan("Bộ tiêu chuẩn MOET đánh giá chất lượng chương trình đào tạo");
        b.setTenVietTat("MOET"); b.setTongSoTieuChuan(8); b.setTongSoTieuChi(52); b.setTrangThai("Đang áp dụng");
        b.setMoTa("Bộ tiêu chuẩn MOET sử dụng trong hệ thống gồm 8 tiêu chuẩn và 52 tiêu chí.");
        mongo.save(b);

        for (String[] r : STANDARDS) {
            TieuChuan t = new TieuChuan();
            int n = Integer.parseInt(r[1]);
            t.setId(r[0]); t.setMaBoTieuChuan("MOET"); t.setSoTieuChuan(n); t.setTenTieuChuan(r[2]);
            t.setMoTa("Tiêu chuẩn " + n + " thuộc bộ MOET"); t.setThuTu(n); t.setTrangThai("Đang áp dụng");
            mongo.save(t);
        }

        int nowIndex = 0;
        for (String[] r : CRITERIA) {
            TieuChi t = new TieuChi();
            int n = Integer.parseInt(r[1]);
            String id = r[0] + "." + r[1];
            t.setId(id); t.setMaTieuChuan(r[0]); t.setSoTieuChi(n); t.setTenTieuChi(r[2]);
            t.setMoTa("Tiêu chí " + id + " thuộc bộ MOET"); t.setTieuChiDieuKien(r[3]); t.setThuTu(n);
            t.setChiBao(new ArrayList<>()); t.setMinhChungGoiY(new ArrayList<>()); t.setMaNguoiTao("ND001");
            t.setNgayTao(LocalDateTime.of(2026,9,1,8,0).plusMinutes(nowIndex));
            t.setNgayCapNhat(t.getNgayTao());
            mongo.save(t);
            nowIndex++;
        }
    }

    private void seedCtdt() {
        ChuongTrinhDaoTao c = new ChuongTrinhDaoTao();
        c.setId("CTDT001"); c.setTenCTDT("Chương trình đào tạo đại học ngành Công nghệ thông tin"); c.setMaNganh("7480201");
        c.setTrinhDoDaoTao("Đại học"); c.setLoaiHinhDaoTao("Chính quy"); c.setThoiGianDaoTao("4 năm");
        c.setMoTa("Chương trình đào tạo trình độ đại học ngành Công nghệ thông tin."); c.setTrangThai("Đang áp dụng");
        c.setNamBatDau(2022); c.setMaNguoiTao("ND001"); c.setNgayTao(LocalDateTime.of(2026,9,1,8,0)); c.setNgayCapNhat(c.getNgayTao());

        List<PhienBanCTDT> versions = new ArrayList<>();
        versions.add(version(2022,1,"Đã ban hành",new ArrayList<>()));
        versions.add(version(2024,2,"Đã ban hành",new ArrayList<>()));

        List<ChuanDauRaPLO> plos = new ArrayList<>();
        plos.add(plo("PLO1","Kiến thức chuyên môn","Vận dụng kiến thức chuyên môn để giải quyết vấn đề.","Kiến thức",4,1));
        plos.add(plo("PLO2","Kỹ năng nghề nghiệp","Phân tích, thiết kế và triển khai giải pháp công nghệ thông tin.","Kỹ năng",4,2));
        plos.add(plo("PLO3","Giao tiếp và làm việc nhóm","Giao tiếp và phối hợp hiệu quả trong môi trường nghề nghiệp.","Kỹ năng",4,3));
        plos.add(plo("PLO4","Tự chủ và trách nhiệm","Có khả năng tự học, thích ứng và chịu trách nhiệm nghề nghiệp.","Mức tự chủ và trách nhiệm",4,4));
        versions.add(version(2026,3,"Đang áp dụng",plos));
        c.setPhienBan(versions);
        mongo.save(c);
    }

    private PhienBanCTDT version(int year,int no,String status,List<ChuanDauRaPLO> plos) {
        PhienBanCTDT p = new PhienBanCTDT();
        p.setMaPhienBan("PB"+year); p.setNamHoc(String.valueOf(year)); p.setNamPhienBan(year); p.setSoPhienBan(no);
        p.setTenPhienBan("Phiên bản CTĐT năm "+year); p.setNoiDungCapNhat(no==1?"Phiên bản ban đầu":"Rà soát, cập nhật CTĐT năm "+year);
        p.setNgayBanHanh(LocalDate.of(year,8,15)); p.setTrangThai(status); p.setMaNguoiCapNhat("ND001");
        p.setChuanDauRa(plos);
        return p;
    }

    private ChuanDauRaPLO plo(String ma,String ten,String moTa,String loai,int capDo,int thuTu) {
        ChuanDauRaPLO p = new ChuanDauRaPLO();
        p.setMaPLO(ma); p.setTenPLO(ten); p.setMoTaPLO(moTa); p.setLoaiPLO(loai); p.setCapDo(capDo); p.setThuTu(thuTu);
        return p;
    }

    private void seedPlan() {
        KeHoachTuDanhGia k = new KeHoachTuDanhGia();
        k.setId(PLAN_ID); k.setTenKeHoach("Kế hoạch tự đánh giá CTĐT ngành Công nghệ thông tin năm 2026");
        k.setMaCTDT("CTDT001"); k.setMaPhienBanCTDT("PB2026"); k.setMaBoTieuChuan("MOET");
        k.setNgayBatDau(LocalDate.of(2026,9,1)); k.setNgayKetThuc(LocalDate.of(2026,12,31)); k.setTrangThai("Đã phê duyệt");
        Map<String,Object> review = new LinkedHashMap<>();
        review.put("capDuyet","Bộ phận khảo thí"); review.put("maNguoiDuyet","ND008"); review.put("ketQua","Duyệt");
        review.put("nhanXet","Kế hoạch đáp ứng yêu cầu triển khai tự đánh giá."); review.put("ngayDuyet",LocalDateTime.of(2026,9,2,8,30));
        k.setQuyTrinhDuyet(new ArrayList<>(List.of(review)));
        mongo.save(k);
    }

    private static final String[][] GROUPS = {
            {"NT001","Nhóm công tác 1","ND010","ND015","ND020,ND021,ND022,ND023,ND024","TC1,TC6"},
            {"NT002","Nhóm công tác 2","ND011","ND016","ND025,ND026,ND027,ND028,ND029","TC2,TC8"},
            {"NT003","Nhóm công tác 3","ND012","ND017","ND030,ND031,ND032,ND033,ND034","TC3,TC4"},
            {"NT004","Nhóm công tác 4","ND013","ND018","ND035,ND036,ND037,ND038,ND039","TC5"},
            {"NT005","Nhóm công tác 5","ND014","ND019","ND040,ND041,ND042,ND043,ND044","TC7"}
    };

    private final Map<String,String> standardToGroup = new LinkedHashMap<>();
    private final Map<String,String[]> groupById = new LinkedHashMap<>();
    private final Map<String,PhanCong> criterionAssignment = new LinkedHashMap<>();

    private void seedGroupsAndAssignments() {
        standardToGroup.clear(); groupById.clear(); criterionAssignment.clear();
        for(String[] g:GROUPS) {
            groupById.put(g[0],g);
            for(String std:g[5].split(",")) standardToGroup.put(std,g[0]);

            NhomTieuChi n = new NhomTieuChi();
            n.setId(g[0]); n.setTenNhom(g[1]); n.setMaTruongNhom(g[2]); n.setMaThuKyNhom(g[3]);
            n.setTieuChuanPhuTrach(new ArrayList<>(Arrays.asList(g[5].split(","))));
            List<String> members = new ArrayList<>(); members.add(g[2]); members.add(g[3]); members.addAll(Arrays.asList(g[4].split(",")));
            n.setThanhVien(members);
            List<ThanhVienNhom> detail = new ArrayList<>();
            detail.add(new ThanhVienNhom(g[2],"Trưởng nhóm")); detail.add(new ThanhVienNhom(g[3],"Thư ký nhóm"));
            for(String m:g[4].split(",")) detail.add(new ThanhVienNhom(m,"Thành viên"));
            n.setThanhVienChiTiet(detail); n.setTieuChi(new ArrayList<>()); n.setTrangThai("Hoạt động");
            mongo.save(n);
        }

        int pcNo = 1;
        for(String[] s:STANDARDS) {
            String gid = standardToGroup.get(s[0]); String[] g = groupById.get(gid);
            PhanCong p = new PhanCong();
            p.setId(String.format("PC%03d",pcNo++)); p.setMaKeHoach(PLAN_ID); p.setMaNguoiDung(g[2]);
            p.setMaTieuChuan(s[0]); p.setMaTieuChi(null); p.setMaNhomTieuChi(gid); p.setVaiTroTrongNhom("Trưởng nhóm");
            p.setNoiDungCongViec("Phụ trách "+s[0]+" - "+s[2]); p.setNgayBatDau(LocalDate.of(2026,9,3));
            p.setNgayKetThucDuKien(LocalDate.of(2026,11,30)); p.setTrangThai("Đang thực hiện"); p.setMaNguoiTao("ND001");
            mongo.save(p);
        }

        Map<String,Integer> used = new HashMap<>();
        for(String[] c:CRITERIA) {
            String gid=standardToGroup.get(c[0]); String[] g=groupById.get(gid);
            List<String> workers=new ArrayList<>(); workers.add(g[3]); workers.addAll(Arrays.asList(g[4].split(",")));
            String assignee=null;
            for(String w:workers) {
                String key=gid+"|"+w;
                if(used.getOrDefault(key,0)<2) { assignee=w; used.put(key,used.getOrDefault(key,0)+1); break; }
            }
            if(assignee==null) throw new IllegalStateException("Không đủ nhân sự cho "+c[0]+"."+c[1]);
            String critId=c[0]+"."+c[1];
            PhanCong p = new PhanCong();
            p.setId(String.format("PC%03d",pcNo++)); p.setMaKeHoach(PLAN_ID); p.setMaNguoiDung(assignee);
            p.setMaTieuChuan(c[0]); p.setMaTieuChi(critId); p.setMaNhomTieuChi(gid);
            p.setVaiTroTrongNhom(assignee.equals(g[3])?"Thư ký nhóm":"Thành viên");
            p.setNoiDungCongViec("Thu thập minh chứng và viết báo cáo tự đánh giá cho "+critId);
            p.setNgayBatDau(LocalDate.of(2026,9,5)); p.setNgayKetThucDuKien(LocalDate.of(2026,11,15));
            p.setTrangThai("Đang thực hiện"); p.setMaNguoiTao(g[2]);
            mongo.save(p); criterionAssignment.put(critId,p);
        }
    }

    private void seedCommonEvidence() {
        String[][] rows={
                {"MCC001","Hồ sơ hoạt động ngoại khóa cấp Trường","Cấp Trường","Trường Đại học Công Thương Thành phố Hồ Chí Minh"},
                {"MCC002","Hồ sơ hoạt động thể thao sinh viên cấp Trường","Cấp Trường","Trường Đại học Công Thương Thành phố Hồ Chí Minh"},
                {"MCC003","Hồ sơ IT Cup","Cấp Khoa","Khoa Công nghệ Thông tin"},
                {"MCC004","Hồ sơ hội trại sinh viên","Cấp Khoa","Khoa Công nghệ Thông tin"},
                {"MCC005","Hồ sơ seminar chuyên môn","Cấp Khoa","Khoa Công nghệ Thông tin"},
                {"MCC006","Đề cương học phần","Cấp Khoa","Khoa Công nghệ Thông tin"},
                {"MCC007","Giáo án giảng dạy","Cấp Khoa","Khoa Công nghệ Thông tin"},
                {"MCC008","Giáo trình phục vụ chương trình đào tạo","Cấp Khoa","Khoa Công nghệ Thông tin"},
                {"MCC009","Hồ sơ giảng viên","Cấp Khoa","Khoa Công nghệ Thông tin"},
                {"MCC010","Quy định giảng dạy của Khoa","Cấp Khoa","Khoa Công nghệ Thông tin"}
        };
        for(String[] r:rows) {
            MinhChungChung m = new MinhChungChung();
            m.setId(r[0]); m.setMaMinhChung(r[0]); m.setTenMinhChung(r[1]); m.setCapQuanLy(r[2]); m.setDonViBanHanh(r[3]);
            m.setMoTa("Dữ liệu minh chứng dùng chung phục vụ tự đánh giá."); m.setMaNguoiTao("ND001");
            m.setNgayTao(LocalDateTime.of(2026,9,1,8,0)); m.setNgayCapNhat(m.getNgayTao());
            mongo.save(m);
        }
    }

    private void seedCriterionEvidenceAndReports() {
        String[][] states={{"Đang soạn","25","Thành viên"},{"Chờ Trưởng nhóm duyệt","25","Thành viên"},{"Chờ Hội đồng cấp khoa duyệt","50","Trưởng nhóm"},{"Chờ TT QLCL duyệt","75","Hội đồng cấp khoa"},{"Đã duyệt","100","TT QLCL"}};
        int i=1;
        for(String[] c:CRITERIA) {
            String critId=c[0]+"."+c[1];
            PhanCong pc = criterionAssignment.get(critId);
            if(pc==null) throw new IllegalStateException("Thiếu phân công cho "+critId);

            MinhChung m = new MinhChung();
            m.setId(String.format("MC%03d",i)); m.setLoaiMinhChung("TIEU_CHI");
            m.setMaMinhChung("H"+Integer.parseInt(c[0].substring(2))+"."+c[1]+".01");
            m.setMaTieuChuan(c[0]); m.setMaTieuChi(critId); m.setMaPhanCong(pc.getId()); m.setMaKeHoach(PLAN_ID);
            m.setTenMinhChung("Minh chứng phục vụ "+critId); m.setMoTa("Minh chứng demo phục vụ quá trình tự đánh giá "+critId+".");
            m.setNgayUpload(LocalDateTime.of(2026,9,20,8,0).plusMinutes(i)); m.setMaNguoiUpload(pc.getMaNguoiDung());
            m.setTrangThai(i%3==0?"Đã duyệt":(i%3==1?"Chờ Trưởng nhóm duyệt":"Chờ Thư ký duyệt"));
            mongo.save(m);

            String[] st=states[(i-1)%states.length];
            BaoCaoTieuChi b = new BaoCaoTieuChi();
            b.setId(String.format("BC%03d",i)); b.setMaPhanCong(pc.getId()); b.setMaKeHoach(PLAN_ID); b.setMaTieuChuan(c[0]); b.setMaTieuChi(critId);
            b.setNhanDeTieuChi(critId+" - "+c[2]);
            b.setMoTaHienTrang("Nhóm công tác đã rà soát hiện trạng, hồ sơ quản lý, tài liệu chuyên môn và các minh chứng liên quan đến "+critId+".");
            b.setDiemManh("Đơn vị đã triển khai các hoạt động liên quan và có dữ liệu phục vụ tự đánh giá.");
            b.setDiemTonTai("Một số dữ liệu cần tiếp tục được cập nhật, chuẩn hóa và hoàn thiện.");
            b.setKeHoachHanhDong("Tiếp tục rà soát, bổ sung minh chứng và thực hiện các hoạt động cải tiến trong chu kỳ tiếp theo.");
            b.setTrangThai(st[0]); b.setMucDoDuyet(Integer.parseInt(st[1])); b.setCapDuyetHienTai(st[2]); b.setMaNguoiViet(pc.getMaNguoiDung());
            b.setDuyetBaoCao(new ArrayList<>()); b.setNgayTao(LocalDateTime.of(2026,9,25,8,0).plusMinutes(i)); b.setNgayCapNhat(b.getNgayTao());
            mongo.save(b);
            i++;
        }
    }

    private void seedGroupReports() {
        int no=1;
        for(String[] g:GROUPS) {
            for(String std:g[5].split(",")) {
                List<String> ids = mongo.find(Query.query(Criteria.where("maKeHoach").is(PLAN_ID).and("maTieuChuan").is(std)), BaoCaoTieuChi.class)
                        .stream().map(BaoCaoTieuChi::getId).toList();
                String stdName = Arrays.stream(STANDARDS).filter(x->x[0].equals(std)).map(x->x[2]).findFirst().orElse(std);
                BaoCaoNhom b = new BaoCaoNhom();
                b.setId(String.format("BCN%03d",no++)); b.setMaKeHoach(PLAN_ID); b.setMaNhomTieuChi(g[0]); b.setMaTieuChuan(std);
                b.setTenBaoCao("Báo cáo tổng hợp "+std+" - "+stdName); b.setDanhSachBaoCaoTieuChi(new ArrayList<>(ids));
                b.setMoTaTongHop("Tổng hợp hiện trạng và kết quả tự đánh giá các tiêu chí thuộc "+std+".");
                b.setDiemManhTongHop("Các tiêu chí được phân công rõ ràng và có nội dung tự đánh giá tương ứng.");
                b.setTonTaiTongHop("Một số dữ liệu và minh chứng cần tiếp tục hoàn thiện.");
                b.setKeHoachCaiTien("Tiếp tục rà soát, cập nhật và hoàn thiện các nội dung theo phản hồi.");
                b.setMaThuKyNhom(g[3]); b.setMaTruongNhom(g[2]); b.setTrangThai("Đang tổng hợp");
                b.setNgayTao(LocalDateTime.of(2026,10,1,8,0)); b.setNgayCapNhat(b.getNgayTao());
                mongo.save(b);
            }
        }
    }

    private void seedMasterReport() {
        BaoCaoTuDanhGia b = new BaoCaoTuDanhGia();
        b.setId("BCTDG001"); b.setMaKeHoach(PLAN_ID); b.setMaCTDT("CTDT001"); b.setMaPhienBanCTDT("PB2026");
        b.setTenBaoCao("Báo cáo tự đánh giá chương trình đào tạo ngành Công nghệ thông tin năm 2026"); b.setPhienBanBaoCao(1);
        List<Map<String,Object>> structure = new ArrayList<>();
        structure.add(new LinkedHashMap<>(Map.of("soPhan",1,"tenPhan","Khái quát","loai","PHAN_CHUNG","nguonDuLieu","baoCaoPhan")));
        structure.add(new LinkedHashMap<>(Map.of("soPhan",2,"tenPhan","Tự đánh giá theo các tiêu chuẩn, tiêu chí","loai","NHOM_CONG_TAC","nguonDuLieu","baoCaoNhom")));
        structure.add(new LinkedHashMap<>(Map.of("soPhan",3,"tenPhan","Kết luận","loai","PHAN_CHUNG","nguonDuLieu","baoCaoPhan")));
        structure.add(new LinkedHashMap<>(Map.of("soPhan",4,"tenPhan","Phụ lục","loai","PHAN_CHUNG","nguonDuLieu","baoCaoPhan")));
        b.setCauTruc(structure); b.setNguonPhan2(new LinkedHashMap<>(Map.of("collection","baoCaoNhom","maKeHoach",PLAN_ID)));
        b.setTrangThai("Đang thực hiện"); b.setMucDoHoanThanh(45); b.setMaNguoiTao("ND001");
        b.setNgayTao(LocalDateTime.of(2026,10,1,8,0)); b.setNgayCapNhat(b.getNgayTao());
        mongo.save(b);
    }

    private void seedReportAssignmentsAndParts() {
        Object[][] jobs={
                {"PCB001",1,"Biên soạn Phần 1 - Khái quát","ND056",List.of("ND057"),"Tổng hợp đặt vấn đề, tổng quan cơ sở giáo dục, đơn vị đào tạo, CTĐT và quá trình tự đánh giá.",60},
                {"PCB002",3,"Biên soạn Phần 3 - Kết luận","ND058",List.of("ND059"),"Tổng hợp điểm mạnh, tồn tại, kế hoạch cải tiến và kết quả tự đánh giá.",40},
                {"PCB003",4,"Biên soạn Phần 4 - Phụ lục","ND060",List.of("ND061"),"Tổng hợp bảng kết quả, cơ sở dữ liệu kiểm định, quyết định, văn bản và danh mục minh chứng.",35}
        };
        for(Object[] r:jobs) {
            PhanCongBaoCao p = new PhanCongBaoCao();
            p.setId((String)r[0]); p.setMaBaoCaoTuDanhGia("BCTDG001"); p.setMaKeHoach(PLAN_ID); p.setSoPhan((Integer)r[1]);
            p.setTenCongViec((String)r[2]); p.setMaNguoiDung((String)r[3]); p.setMaNhanSuHoTroList(new ArrayList<>((List<String>)r[4]));
            p.setNoiDungCongViec((String)r[5]); p.setNgayBatDau(LocalDate.of(2026,10,1)); p.setNgayKetThucDuKien(LocalDate.of(2026,11,20));
            p.setTrangThai("Đang thực hiện"); p.setPhanTramHoanThanh((Integer)r[6]); p.setMaNguoiTao("ND001");
            p.setNgayTao(LocalDateTime.of(2026,10,1,8,0)); p.setNgayCapNhat(p.getNgayTao());
            mongo.save(p);
        }

        BaoCaoPhan p1 = new BaoCaoPhan();
        p1.setId("BCP001"); p1.setMaBaoCaoTuDanhGia("BCTDG001"); p1.setMaKeHoach(PLAN_ID); p1.setSoPhan(1); p1.setTenPhan("Phần 1 - Khái quát");
        p1.setMaPhanCong("PCB001"); p1.setMaNguoiPhuTrach("ND056");
        Map<String,Object> c1 = new LinkedHashMap<>();
        c1.put("datVanDe","Chương trình đào tạo thực hiện tự đánh giá nhằm rà soát mức độ đáp ứng các yêu cầu bảo đảm chất lượng.");
        c1.put("tongQuanCoSoGiaoDuc","Nội dung tổng quan về cơ sở giáo dục.");
        c1.put("tongQuanDonViDaoTao","Nội dung giới thiệu Khoa Công nghệ Thông tin.");
        c1.put("tongQuanCTDT","Nội dung tổng quan chương trình đào tạo.");
        c1.put("phuongPhapTuDanhGia","Thu thập dữ liệu, minh chứng, phân tích hiện trạng, điểm mạnh, tồn tại và kế hoạch cải tiến.");
        p1.setNoiDung(c1); initPart(p1); mongo.save(p1);

        BaoCaoPhan p3 = new BaoCaoPhan();
        p3.setId("BCP003"); p3.setMaBaoCaoTuDanhGia("BCTDG001"); p3.setMaKeHoach(PLAN_ID); p3.setSoPhan(3); p3.setTenPhan("Phần 3 - Kết luận");
        p3.setMaPhanCong("PCB002"); p3.setMaNguoiPhuTrach("ND058");
        Map<String,Object> c3 = new LinkedHashMap<>();
        c3.put("tongHopDiemManh","Tổng hợp điểm mạnh từ các tiêu chuẩn.");
        c3.put("tongHopTonTai","Tổng hợp tồn tại và nội dung cần cải tiến.");
        c3.put("tongHopKeHoachCaiTien","Tổng hợp kế hoạch cải tiến chất lượng chương trình đào tạo.");
        c3.put("ketQuaTuDanhGia",new LinkedHashMap<>(Map.of("tongSoTieuChi",52,"daDanhGia",0,"dat",0,"khongDat",0)));
        p3.setNoiDung(c3); initPart(p3); mongo.save(p3);

        BaoCaoPhan p4 = new BaoCaoPhan();
        p4.setId("BCP004"); p4.setMaBaoCaoTuDanhGia("BCTDG001"); p4.setMaKeHoach(PLAN_ID); p4.setSoPhan(4); p4.setTenPhan("Phần 4 - Phụ lục");
        p4.setMaPhanCong("PCB003"); p4.setMaNguoiPhuTrach("ND060");
        p4.setNoiDung(new LinkedHashMap<>(Map.of("bangTongHopKetQua",true,"coSoDuLieuKiemDinh",true,"danhMucMinhChung",true,"cacQuyetDinhLienQuan",true)));
        p4.setDanhSachMinhChungCapTruong(mongo.find(Query.query(Criteria.where("capQuanLy").is("Cấp Trường")),MinhChungChung.class).stream().map(MinhChungChung::getId).toList());
        p4.setDanhSachMinhChungCapKhoa(mongo.find(Query.query(Criteria.where("capQuanLy").is("Cấp Khoa")),MinhChungChung.class).stream().map(MinhChungChung::getId).toList());
        p4.setDanhSachMinhChungTieuChi(mongo.find(Query.query(Criteria.where("maKeHoach").is(PLAN_ID)),MinhChung.class).stream().map(MinhChung::getId).toList());
        initPart(p4); mongo.save(p4);
    }

    private void initPart(BaoCaoPhan p) {
        p.setTrangThai("Đang soạn"); p.setMucDoDuyet(25); p.setCapDuyetHienTai("Người phụ trách"); p.setFeedback(new ArrayList<>());
        p.setNgayTao(LocalDateTime.of(2026,10,1,8,0)); p.setNgayCapNhat(p.getNgayTao());
    }

    private void seedProgress() {
        List<PhanCong> all = mongo.findAll(PhanCong.class);
        all.sort(Comparator.comparing(PhanCong::getId));
        int i=1;
        for(PhanCong pc:all) {
            TienDo t = new TienDo();
            t.setId(String.format("TD%03d",i++)); t.setMaKeHoach(pc.getMaKeHoach()); t.setMaPhanCong(pc.getId());
            t.setPhanTramHoanThanh(pc.getMaTieuChi()==null?50:30); t.setTrangThai(pc.getTrangThai());
            mongo.save(t);
        }
    }

    private void seedEvaluations() {
        int no=1;
        for(int i:List.of(15,30,45)) {
            BaoCaoTieuChi bc = mongo.findById(String.format("BC%03d",i),BaoCaoTieuChi.class);
            if(bc==null) continue;
            DanhGiaTieuChi d = new DanhGiaTieuChi();
            d.setId(String.format("DGTC%03d",no)); d.setMaKeHoach(PLAN_ID); d.setMaTieuChi(bc.getMaTieuChi()); d.setLanDanhGia(1);
            d.setKetQua(no==3?"Không đạt":"Đạt"); d.setNhanXet("Hội đồng đã xem xét báo cáo và minh chứng của tiêu chí.");
            d.setMaNguoiDanhGia(String.format("ND%03d",2+no)); d.setNgayDanhGia(LocalDateTime.of(2026,10,5,8,0).plusMinutes(no));
            mongo.save(d); no++;
        }
    }

    private void seedActivityLogs() {
        Object[][] rows={
                {"LS001","ND001","Khởi tạo dữ liệu","Hệ thống","MOET","Khởi tạo bộ MOET gồm 8 tiêu chuẩn và 52 tiêu chí."},
                {"LS002","ND006","Lập kế hoạch","Kế hoạch tự đánh giá","KH001","Lập kế hoạch tự đánh giá CTĐT năm 2026."},
                {"LS003","ND008","Phê duyệt","Kế hoạch tự đánh giá","KH001","Phê duyệt kế hoạch tự đánh giá."},
                {"LS004","ND001","Phân công","Nhóm công tác","NT001","Phân công tiêu chuẩn cho các nhóm công tác."},
                {"LS005","ND010","Phân công","Tiêu chí","TC1.1","Phân công tiêu chí cho thành viên nhóm."},
                {"LS006","ND020","Nộp minh chứng","Minh chứng","MC001","Nộp minh chứng phục vụ báo cáo tiêu chí."}
        };
        int i=0;
        for(Object[] r:rows) {
            LichSuThaoTac l = new LichSuThaoTac();
            l.setId((String)r[0]); l.setMaNguoiDung((String)r[1]); l.setHanhDong((String)r[2]); l.setDoiTuongTacDong((String)r[3]);
            l.setMaBanGhi((String)r[4]); l.setNoiDung((String)r[5]); l.setThoiGian(LocalDateTime.of(2026,10,1,8,0).plusHours(i++));
            mongo.save(l);
        }
    }

    private void ensureIndexes() {
        try { mongo.indexOps("nguoiDung").ensureIndex(new Index().on("email", Sort.Direction.ASC).unique()); } catch (Exception e) { System.err.println("[SEED] Không tạo được unique index email: "+e.getMessage()); }
        try { mongo.indexOps("phanCong").ensureIndex(new Index().on("maKeHoach",Sort.Direction.ASC).on("maNhomTieuChi",Sort.Direction.ASC).on("maTieuChuan",Sort.Direction.ASC).on("maTieuChi",Sort.Direction.ASC)); } catch (Exception ignored) {}
        try { mongo.indexOps("baoCaoTieuChi").ensureIndex(new Index().on("maKeHoach",Sort.Direction.ASC).on("maTieuChi",Sort.Direction.ASC)); } catch (Exception ignored) {}
        try { mongo.indexOps("minhChung").ensureIndex(new Index().on("maKeHoach",Sort.Direction.ASC).on("maTieuChi",Sort.Direction.ASC)); } catch (Exception ignored) {}
    }

    private void validateSeed() {
        long tc = mongo.getCollection("tieuChuan").countDocuments();
        long criterion = mongo.getCollection("tieuChi").countDocuments();
        long groups = mongo.getCollection("nhomTieuChi").countDocuments();
        long assignments = mongo.getCollection("phanCong").countDocuments();
        if (tc != 8 || criterion != 52 || groups != 5 || assignments != 60)
            throw new IllegalStateException("Dữ liệu demo không đạt yêu cầu: tieuChuan="+tc+", tieuChi="+criterion+", nhom="+groups+", phanCong="+assignments);

        List<PhanCong> overload = mongo.findAll(PhanCong.class).stream().filter(x->x.getMaTieuChi()!=null).toList();
        Map<String,Integer> counts = new HashMap<>();
        for(PhanCong p:overload) {
            String key=p.getMaKeHoach()+"|"+p.getMaNhomTieuChi()+"|"+p.getMaNguoiDung();
            counts.put(key,counts.getOrDefault(key,0)+1);
        }
        if(counts.values().stream().anyMatch(x->x>2)) throw new IllegalStateException("Có người bị giao quá 2 tiêu chí trong một nhóm/kế hoạch");
    }

    private void printSummary() {
        System.out.println("[SEED OK] HeThongKiemDinh29 ready -> "
                +"CTDT="+mongo.getCollection("chuongTrinhDaoTao").countDocuments()
                +", MOET="+mongo.getCollection("boTieuChuan").countDocuments()
                +", TC="+mongo.getCollection("tieuChuan").countDocuments()
                +", criteria="+mongo.getCollection("tieuChi").countDocuments()
                +", groups="+mongo.getCollection("nhomTieuChi").countDocuments()
                +", assignments="+mongo.getCollection("phanCong").countDocuments()
                +", users="+mongo.getCollection("nguoiDung").countDocuments());
    }
}
