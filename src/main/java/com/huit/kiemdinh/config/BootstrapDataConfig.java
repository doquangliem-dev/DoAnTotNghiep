package com.huit.kiemdinh.config;

import com.huit.kiemdinh.model.NguoiDung;
import com.huit.kiemdinh.model.PhanQuyen;
import com.huit.kiemdinh.model.VaiTro;
import com.huit.kiemdinh.model.BoTieuChuan;
import com.huit.kiemdinh.model.TieuChuan;
import com.huit.kiemdinh.model.TieuChi;
import com.huit.kiemdinh.model.NhomTieuChi;
import com.huit.kiemdinh.model.PhanCong;
import com.huit.kiemdinh.model.BaoCaoTieuChi;
import com.huit.kiemdinh.repository.NguoiDungRepository;
import com.huit.kiemdinh.repository.VaiTroRepository;
import com.huit.kiemdinh.repository.BoTieuChuanRepository;
import com.huit.kiemdinh.repository.TieuChuanRepository;
import com.huit.kiemdinh.repository.TieuChiRepository;
import com.huit.kiemdinh.repository.NhomTieuChiRepository;
import com.huit.kiemdinh.repository.PhanCongRepository;
import com.huit.kiemdinh.repository.BaoCaoTieuChiRepository;
import com.huit.kiemdinh.security.AuthService;
import com.huit.kiemdinh.service.CodeGeneratorService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.time.LocalDateTime;

@Component
public class BootstrapDataConfig implements ApplicationRunner {
    private static final String DEFAULT_ADMIN_EMAIL = "admin@huit.edu.vn";
    private static final String DEFAULT_ADMIN_PASSWORD = "123456";

    private final VaiTroRepository roleRepo;
    private final NguoiDungRepository userRepo;
    private final CodeGeneratorService codes;
    private final AuthService auth;
    private final BoTieuChuanRepository boRepo;
    private final TieuChuanRepository tieuChuanRepo;
    private final TieuChiRepository tieuChiRepo;
    private final NhomTieuChiRepository nhomRepo;
    private final PhanCongRepository phanCongRepo;
    private final BaoCaoTieuChiRepository baoCaoRepo;

    public BootstrapDataConfig(VaiTroRepository roleRepo, NguoiDungRepository userRepo,
                               CodeGeneratorService codes, AuthService auth,
                               BoTieuChuanRepository boRepo, TieuChuanRepository tieuChuanRepo,
                               TieuChiRepository tieuChiRepo, NhomTieuChiRepository nhomRepo,
                               PhanCongRepository phanCongRepo, BaoCaoTieuChiRepository baoCaoRepo){
        this.roleRepo=roleRepo;
        this.userRepo=userRepo;
        this.codes=codes;
        this.auth=auth;
        this.boRepo=boRepo;
        this.tieuChuanRepo=tieuChuanRepo;
        this.tieuChiRepo=tieuChiRepo;
        this.nhomRepo=nhomRepo;
        this.phanCongRepo=phanCongRepo;
        this.baoCaoRepo=baoCaoRepo;
    }

    @Override
    public void run(ApplicationArguments args) {
        ensureRoles();
        consolidateDuplicateRoles();
        migrateLegacyStandardSetCodes();
        migrateAssignmentStructureV8();
        migrateReportStructureV15();
        ensureDefaultAdmin();
    }

    private void ensureRoles(){
        List<VaiTro> existing = roleRepo.findAll();
        Map<String,VaiTro> byId = new HashMap<>();
        for (VaiTro v : existing) byId.put(v.getId(), v);

        List<VaiTro> defaults = List.of(
                role("VT001","Quản trị","Quản trị toàn bộ hệ thống"),
                role("VT002","Hội đồng tự đánh giá","Theo dõi, đánh giá và duyệt nội dung"),
                role("VT003","Trưởng nhóm tiêu chí","Quản lý nhóm, phân công và duyệt minh chứng"),
                role("VT004","Thành viên nhóm","Thu thập minh chứng và viết báo cáo tiêu chí"),
                role("VT005","Thư ký chương trình","Lập kế hoạch và tổng hợp báo cáo"),
                role("VT006","Bộ phận khảo thí","Thực hiện bước phê duyệt theo quy trình")
        );

        for (VaiTro d : defaults) {
            if (!byId.containsKey(d.getId())) roleRepo.save(d);
        }
    }


    /** Gom các vai trò trùng tên về mã chuẩn VT001..VT006 để combobox không bị lặp. */
    private void consolidateDuplicateRoles(){
        Map<String,String> canonical=new LinkedHashMap<>();
        for(VaiTro v:roleRepo.findAll()){
            String key=normalize(v.getTenVaiTro());
            if(v.getId()!=null && v.getId().matches("VT00[1-6]")) canonical.put(key,v.getId());
        }
        for(NguoiDung u:userRepo.findAll()){
            boolean changed=false;
            if(u.getPhanQuyen()!=null){
                for(PhanQuyen pq:u.getPhanQuyen()){
                    if(pq==null||pq.getMaVaiTro()==null)continue;
                    VaiTro old=roleRepo.findById(pq.getMaVaiTro()).orElse(null);
                    if(old==null)continue;
                    String target=canonical.get(normalize(old.getTenVaiTro()));
                    if(target!=null && !target.equals(pq.getMaVaiTro())){pq.setMaVaiTro(target);changed=true;}
                }
            }
            if(changed)userRepo.save(u);
        }
        Set<String> keep=new HashSet<>(canonical.values());
        for(VaiTro v:roleRepo.findAll()){
            String target=canonical.get(normalize(v.getTenVaiTro()));
            if(target!=null && !keep.contains(v.getId())) roleRepo.deleteById(v.getId());
        }
    }

    /**
     * Dữ liệu cũ từng dùng BTC001/BTC002. Với các bộ nhận diện được theo tên/văn bản,
     * tự chuyển sang mã tên kiểm định như AUN-QA, ABET, FIBAA, ASIIN hoặc TT04-2025.
     */
    private void migrateLegacyStandardSetCodes(){
        List<BoTieuChuan> all=new ArrayList<>(boRepo.findAll());
        for(BoTieuChuan old:all){
            if(old.getId()==null || !old.getId().matches("BTC\\d+")) continue;
            String target=detectStandardSetCode(old);
            if(target==null || target.equals(old.getId())) continue;
            if(!boRepo.existsById(target)){
                BoTieuChuan n=new BoTieuChuan();
                n.setId(target); n.setTenBoTieuChuan(old.getTenBoTieuChuan()); n.setMaSoVanBan(old.getMaSoVanBan());
                n.setNgayBanHanh(old.getNgayBanHanh()); n.setNamApDung(old.getNamApDung()); n.setTrangThai(old.getTrangThai()); n.setMoTa(old.getMoTa());
                boRepo.save(n);
            }
            for(TieuChuan tc:tieuChuanRepo.findAll()){
                if(old.getId().equals(tc.getMaBoTieuChuan())){tc.setMaBoTieuChuan(target);tieuChuanRepo.save(tc);}
            }
            boRepo.deleteById(old.getId());
        }
    }

    private String detectStandardSetCode(BoTieuChuan b){
        String text=normalize((b.getTenBoTieuChuan()==null?"":b.getTenBoTieuChuan())+" "+(b.getMaSoVanBan()==null?"":b.getMaSoVanBan()));
        if(text.contains("aun") && text.contains("qa"))return "AUN-QA";
        if(text.contains("abet"))return "ABET";
        if(text.contains("fibaa"))return "FIBAA";
        if(text.contains("asiin"))return "ASIIN";
        if(text.contains("04/2025") || text.contains("thong tu 04"))return "TT04-2025";
        return null;
    }

    /**
     * V8: Nhóm chỉ còn tên + thành viên. Phân công của Admin chuyển từ Tiêu chí sang Tiêu chuẩn.
     * Không xóa lịch sử: các phân công cũ trùng cùng Nhóm + Trưởng nhóm + Tiêu chuẩn được đánh dấu Hủy phân công.
     */
    private void migrateAssignmentStructureV8(){
        for(NhomTieuChi g:nhomRepo.findAll()){
            boolean changed=false;
            if(g.getMaTieuChuan()!=null){g.setMaTieuChuan(null);changed=true;}
            if(g.getTieuChi()!=null && !g.getTieuChi().isEmpty()){g.setTieuChi(new ArrayList<>());changed=true;}
            if(changed)nhomRepo.save(g);
        }

        // HeThongKiemDinh24: phân công cấp Tiêu chí là dữ liệu nghiệp vụ hợp lệ do Trưởng nhóm tạo.
        // Tuyệt đối không chuyển các bản ghi này thành phân công cấp Tiêu chuẩn khi khởi động,
        // nếu không sẽ làm mất công việc của Thành viên sau mỗi lần restart.
        // Chỉ bổ sung maTieuChuan còn thiếu để hỗ trợ dữ liệu cũ.
        List<PhanCong> all=new ArrayList<>(phanCongRepo.findAll());
        for(PhanCong p:all){
            if((p.getMaTieuChuan()==null||p.getMaTieuChuan().isBlank()) && p.getMaTieuChi()!=null && !p.getMaTieuChi().isBlank()){
                TieuChi tc=tieuChiRepo.findById(p.getMaTieuChi()).orElse(null);
                if(tc!=null){p.setMaTieuChuan(tc.getMaTieuChuan());phanCongRepo.save(p);}
            }
        }

        // Chỉ gom trùng các phân công CẤP TIÊU CHUẨN do Admin giao cho Trưởng nhóm.
        // Nhiều tiêu chí khác nhau của cùng một thành viên trong cùng tiêu chuẩn phải được giữ nguyên.
        Map<String,String> firstActive=new LinkedHashMap<>();
        for(PhanCong p:phanCongRepo.findAll()){
            if("Hủy phân công".equalsIgnoreCase(p.getTrangThai()))continue;
            if(p.getMaTieuChuan()==null||p.getMaTieuChuan().isBlank())continue;
            if(p.getMaTieuChi()!=null&&!p.getMaTieuChi().isBlank())continue;
            String key=String.valueOf(p.getMaKeHoach())+"|"+String.valueOf(p.getMaNhomTieuChi())+"|"+String.valueOf(p.getMaNguoiDung())+"|"+p.getMaTieuChuan();
            if(!firstActive.containsKey(key))firstActive.put(key,p.getId());
            else{p.setTrangThai("Hủy phân công");phanCongRepo.save(p);}
        }
    }


    /**
     * V15: chuẩn hóa báo cáo tự đánh giá về đúng 4 phần:
     * Mô tả hiện trạng - Điểm mạnh - Điểm tồn tại - Kế hoạch hành động.
     * Đồng thời chuyển dữ liệu cũ noiDungBaoCao/diemYeu sang field mới để giao diện không bị trống.
     */
    private void migrateReportStructureV15(){
        for(BaoCaoTieuChi b:baoCaoRepo.findAll()){
            boolean changed=false;
            if((b.getMoTaHienTrang()==null||b.getMoTaHienTrang().isBlank()) && b.getNoiDungBaoCao()!=null && !b.getNoiDungBaoCao().isBlank()){
                b.setMoTaHienTrang(b.getNoiDungBaoCao()); changed=true;
            }
            if((b.getDiemTonTai()==null||b.getDiemTonTai().isBlank()) && b.getDiemYeu()!=null && !b.getDiemYeu().isBlank()){
                b.setDiemTonTai(b.getDiemYeu()); changed=true;
            }
            if((b.getMaTieuChuan()==null||b.getMaTieuChuan().isBlank()) && b.getMaTieuChi()!=null){
                TieuChi tc=tieuChiRepo.findById(b.getMaTieuChi()).orElse(null);
                if(tc!=null)b.setMaTieuChuan(tc.getMaTieuChuan());
                else{String[] a=b.getMaTieuChi().split("\\."); if(a.length>0)b.setMaTieuChuan(a[0]);}
                changed=true;
            }
            if(b.getNgayTao()==null){b.setNgayTao(LocalDateTime.now());changed=true;}
            if(b.getNgayCapNhat()==null){b.setNgayCapNhat(LocalDateTime.now());changed=true;}
            if(b.getNoiDungBaoCao()!=null){b.setNoiDungBaoCao(null);changed=true;}
            if(b.getDiemYeu()!=null){b.setDiemYeu(null);changed=true;}
            if(changed)baoCaoRepo.save(b);
        }
    }

    private VaiTro role(String id,String name,String desc){
        VaiTro v=new VaiTro();
        v.setId(id);
        v.setTenVaiTro(name);
        v.setMoTa(desc);
        return v;
    }

    /**
     * Tạo tài khoản Admin mặc định đúng một lần.
     * Nếu tài khoản đã tồn tại thì không đổi mật khẩu mỗi lần khởi động.
     * Chỉ khi tài khoản cũ chưa có mật khẩu/vai trò thì mới bổ sung phần còn thiếu.
     */
    private void ensureDefaultAdmin(){
        VaiTro adminRole = roleRepo.findAll().stream()
                .filter(v -> "VT001".equals(v.getId()) ||
                        (v.getTenVaiTro()!=null && normalize(v.getTenVaiTro()).contains("quan tri")))
                .findFirst()
                .orElse(null);
        if (adminRole == null) return;

        Optional<NguoiDung> existing = userRepo.findByEmailIgnoreCase(DEFAULT_ADMIN_EMAIL);

        if (existing.isEmpty()) {
            NguoiDung admin = new NguoiDung();
            admin.setId(codes.nextNguoiDung());
            admin.setHoTen("Quản trị hệ thống");
            admin.setEmail(DEFAULT_ADMIN_EMAIL);
            admin.setMatKhau(auth.encodePassword(DEFAULT_ADMIN_PASSWORD));
            admin.setKhoa("Công nghệ Thông tin");
            admin.setTrinhDo("");
            admin.setTrangThai("Hoạt động");
            admin.setPhanQuyen(new ArrayList<>(List.of(new PhanQuyen(adminRole.getId()))));
            userRepo.save(admin);
            System.out.println("Đã tạo tài khoản Admin mặc định: " + DEFAULT_ADMIN_EMAIL);
            return;
        }

        NguoiDung admin = existing.get();
        boolean changed = false;

        if (admin.getMatKhau() == null || admin.getMatKhau().isBlank()) {
            admin.setMatKhau(auth.encodePassword(DEFAULT_ADMIN_PASSWORD));
            changed = true;
        }
        if (admin.getTrangThai() == null || admin.getTrangThai().isBlank()) {
            admin.setTrangThai("Hoạt động");
            changed = true;
        }

        List<PhanQuyen> pq = admin.getPhanQuyen();
        if (pq == null) pq = new ArrayList<>();
        boolean hasAdmin = pq.stream().anyMatch(x -> x != null && adminRole.getId().equals(x.getMaVaiTro()));
        if (!hasAdmin) {
            pq.add(new PhanQuyen(adminRole.getId()));
            admin.setPhanQuyen(pq);
            changed = true;
        }

        if (changed) userRepo.save(admin);
    }

    private String normalize(String s) {
        if (s == null) return "";
        return java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replace('đ','d').replace('Đ','D')
                .toLowerCase(Locale.ROOT);
    }
}
