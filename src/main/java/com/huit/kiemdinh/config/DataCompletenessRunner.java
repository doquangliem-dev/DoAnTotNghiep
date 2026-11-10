package com.huit.kiemdinh.config;

import com.huit.kiemdinh.model.*;

import com.huit.kiemdinh.repository.*;

import org.springframework.boot.context.event.ApplicationReadyEvent;

import org.springframework.context.event.EventListener;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import java.util.*;

@Component
public class DataCompletenessRunner {
    private final TieuChiRepository tieuChiRepo;
    private final MinhChungRepository minhChungRepo;
    private final MinhChungChungRepository minhChungChungRepo;
    private final BaoCaoTieuChiRepository baoCaoTieuChiRepo;
    private final BaoCaoNhomRepository baoCaoNhomRepo;
    private final BaoCaoPhanRepository baoCaoPhanRepo;
    private final NhomTieuChiRepository nhomTieuChiRepo;
    public DataCompletenessRunner(TieuChiRepository tieuChiRepo,
    MinhChungRepository minhChungRepo,
    MinhChungChungRepository minhChungChungRepo,
    BaoCaoTieuChiRepository baoCaoTieuChiRepo,
    BaoCaoNhomRepository baoCaoNhomRepo,
    BaoCaoPhanRepository baoCaoPhanRepo,
    NhomTieuChiRepository nhomTieuChiRepo) {
        this.tieuChiRepo = tieuChiRepo;
        this.minhChungRepo = minhChungRepo;
        this.minhChungChungRepo = minhChungChungRepo;
        this.baoCaoTieuChiRepo = baoCaoTieuChiRepo;
        this.baoCaoNhomRepo = baoCaoNhomRepo;
        this.baoCaoPhanRepo = baoCaoPhanRepo;
        this.nhomTieuChiRepo = nhomTieuChiRepo;
    }
    @EventListener(ApplicationReadyEvent.class)
    public void schedule() {
        Thread worker = new Thread(this::fillSafely, "data-completeness-repair");
        worker.setDaemon(true);
        worker.start();
    }
    private void fillSafely() {
        try {
            Map<String,TieuChi> criteria = new HashMap<>();
            for (TieuChi c : tieuChiRepo.findAll()) {
                criteria.put(c.getId(), c);
                boolean changed = false;
                if (c.getChiBao() == null || c.getChiBao().isEmpty()) {
                    c.setChiBao(defaultIndicators(c));
                    changed = true;
                }
                if (c.getMinhChungGoiY() == null || c.getMinhChungGoiY().isEmpty()) {
                    c.setMinhChungGoiY(defaultEvidenceSuggestions(c));
                    changed = true;
                }
                if (changed) tieuChiRepo.save(c);
            }
            int mcChanged = 0;
            for (MinhChung mc : minhChungRepo.findAll()) {
                TieuChi c = criteria.get(mc.getMaTieuChi());
                boolean changed = false;
                if (c != null) {
                    String fullName = "Hồ sơ minh chứng " + c.getId() + " - " + c.getTenTieuChi();
                    if (blank(mc.getTenMinhChung()) || mc.getTenMinhChung().startsWith("Minh chứng phục vụ ")) {
                        mc.setTenMinhChung(fullName);
                        changed = true;
                    }
                    if (blank(mc.getMoTa()) || mc.getMoTa().startsWith("Dữ liệu minh chứng")) {
                        mc.setMoTa("Hồ sơ dùng để đối chiếu yêu cầu của " + c.getId() + ": " + c.getTenTieuChi());
                        changed = true;
                    }
                }
                if (blank(mc.getLoaiFile())) {
                    mc.setLoaiFile("pdf");
                    changed = true;
                }
                if (changed) {
                    minhChungRepo.save(mc);
                    mcChanged++;
                }
            }
            int commonChanged = 0;
            for (MinhChungChung x : minhChungChungRepo.findAll()) {
                boolean changed = false;
                if (blank(x.getSoHieuVanBan())) {
                    String prefix = "Cấp Trường".equalsIgnoreCase(x.getCapQuanLy()) ? "MC-TRUONG" : "MC-KCNTT";
                    x.setSoHieuVanBan(prefix + "-2026-" + suffix(x.getId()));
                    changed = true;
                }
                if (x.getNgayBanHanh() == null) {
                    x.setNgayBanHanh(LocalDateTime.of(2026, 1, 15, 0, 0));
                    changed = true;
                }
                if (blank(x.getLoaiFile())) {
                    x.setLoaiFile("pdf");
                    changed = true;
                }
                if (changed) {
                    minhChungChungRepo.save(x);
                    commonChanged++;
                }
            }
            int reportChanged = 0;
            for (BaoCaoTieuChi r : baoCaoTieuChiRepo.findAll()) {
                boolean changed = false;
                String code = r.getMaTieuChi() == null ? "tiêu chí" : r.getMaTieuChi();
                if (blank(r.getMoTaHienTrang())) {
                    r.setMoTaHienTrang("Nhóm công tác đã rà soát hiện trạng, hồ sơ và dữ liệu liên quan đến " + code + ".");
                    changed = true;
                }
                if (blank(r.getDiemManh())) {
                    r.setDiemManh("Đơn vị đã triển khai các hoạt động liên quan và có dữ liệu phục vụ tự đánh giá.");
                    changed = true;
                }
                if (blank(r.getDiemTonTai())) {
                    r.setDiemTonTai("Một số dữ liệu cần tiếp tục được chuẩn hóa, cập nhật và theo dõi định kỳ.");
                    changed = true;
                }
                if (blank(r.getKeHoachHanhDong())) {
                    r.setKeHoachHanhDong("Tiếp tục cập nhật minh chứng, theo dõi kết quả và thực hiện cải tiến trong chu kỳ tiếp theo.");
                    changed = true;
                }
                if (changed) {
                    baoCaoTieuChiRepo.save(r);
                    reportChanged++;
                }
            }
            int groupChanged = 0;
            Map<String, String> canonicalStandard = Map.of(
                    "BCN001", "TC1", "BCN002", "TC2", "BCN003", "TC3", "BCN004", "TC4",
                    "BCN005", "TC5", "BCN006", "TC6", "BCN007", "TC7", "BCN008", "TC8"
            );
            Map<String, String> canonicalGroup = Map.of(
                    "TC1", "NT001", "TC2", "NT001",
                    "TC3", "NT002", "TC4", "NT002",
                    "TC5", "NT003",
                    "TC6", "NT004", "TC7", "NT004",
                    "TC8", "NT005"
            );
            for (BaoCaoNhom g : baoCaoNhomRepo.findAll()) {
                boolean changed = false;
                String expectedStandard = canonicalStandard.getOrDefault(g.getId(), g.getMaTieuChuan());
                String expectedGroup = canonicalGroup.get(expectedStandard);
                if (!Objects.equals(expectedStandard, g.getMaTieuChuan())) {
                    g.setMaTieuChuan(expectedStandard);
                    changed = true;
                }
                if (expectedGroup != null && !expectedGroup.equals(g.getMaNhomTieuChi())) {
                    g.setMaNhomTieuChi(expectedGroup);
                    changed = true;
                }
                if (expectedGroup != null) {
                    NhomTieuChi nhom = nhomTieuChiRepo.findById(expectedGroup).orElse(null);
                    if (nhom != null) {
                        if (!Objects.equals(g.getMaThuKyNhom(), nhom.getMaThuKyNhom())) {
                            g.setMaThuKyNhom(nhom.getMaThuKyNhom());
                            changed = true;
                        }
                        if (!Objects.equals(g.getMaTruongNhom(), nhom.getMaTruongNhom())) {
                            g.setMaTruongNhom(nhom.getMaTruongNhom());
                            changed = true;
                        }
                    }
                }
                List<String> expectedReports = baoCaoTieuChiRepo.findAll().stream()
                        .filter(r -> Objects.equals(expectedStandard, r.getMaTieuChuan()))
                        .map(BaoCaoTieuChi::getId)
                        .filter(Objects::nonNull)
                        .sorted()
                        .toList();
                if (!Objects.equals(g.getDanhSachBaoCaoTieuChi(), expectedReports)) {
                    g.setDanhSachBaoCaoTieuChi(new ArrayList<>(expectedReports));
                    changed = true;
                }
                String expectedName = "Báo cáo tổng hợp " + expectedStandard + " - chương trình Công nghệ thông tin";
                if (blank(g.getTenBaoCao()) || !g.getTenBaoCao().contains(expectedStandard)) {
                    g.setTenBaoCao(expectedName);
                    changed = true;
                }
                if (blank(g.getMoTaTongHop())) {
                    g.setMoTaTongHop("Tổng hợp hiện trạng và kết quả rà soát các tiêu chí thuộc " + expectedStandard + " của chương trình Công nghệ thông tin.");
                    changed = true;
                }
                if (blank(g.getDiemManhTongHop())) {
                    g.setDiemManhTongHop("Các tiêu chí có báo cáo riêng, minh chứng liên kết và đầu mối phụ trách để phục vụ tổng hợp theo tiêu chuẩn.");
                    changed = true;
                }
                if (blank(g.getTonTaiTongHop())) {
                    g.setTonTaiTongHop("Cần duy trì cập nhật tài liệu gốc, dữ liệu đo lường và kết quả cải tiến theo từng chu kỳ đánh giá.");
                    changed = true;
                }
                if (blank(g.getKeHoachCaiTien())) {
                    g.setKeHoachCaiTien("Rà soát định kỳ, cập nhật minh chứng và tổng hợp kết quả cải tiến theo kế hoạch tự đánh giá chương trình CNTT.");
                    changed = true;
                }
                if (g.getMucDoDuyet() == null) {
                    String state = Optional.ofNullable(g.getTrangThai()).orElse("Đang tổng hợp");
                    g.setMucDoDuyet("Đã duyệt".equalsIgnoreCase(state) ? 100 : state.contains("TT QLCL") ? 75 : state.contains("Trưởng nhóm") || "Đã Trưởng nhóm duyệt".equalsIgnoreCase(state) ? 50 : 25);
                    changed = true;
                }
                if (blank(g.getCapDuyetHienTai())) {
                    int pct = Optional.ofNullable(g.getMucDoDuyet()).orElse(25);
                    g.setCapDuyetHienTai(pct >= 75 ? "TT Quản lý chất lượng" : pct >= 50 ? "Trưởng nhóm" : "Thư ký nhóm");
                    changed = true;
                }
                if (changed) {
                    baoCaoNhomRepo.save(g);
                    groupChanged++;
                }
            }

            int partChanged = 0;
            for (BaoCaoPhan p : baoCaoPhanRepo.findAll()) {
                boolean changed = false;
                if (p.getSoPhan() != null && (p.getSoPhan() == 1 || p.getSoPhan() == 3)) {
                    if (p.getDanhSachMinhChungCapTruong() == null || p.getDanhSachMinhChungCapTruong().isEmpty()) {
                        p.setDanhSachMinhChungCapTruong(new ArrayList<>(List.of("MCC001", "MCC002")));
                        changed = true;
                    }
                    if (p.getDanhSachMinhChungCapKhoa() == null || p.getDanhSachMinhChungCapKhoa().isEmpty()) {
                        p.setDanhSachMinhChungCapKhoa(new ArrayList<>(List.of("MCC003", "MCC005", "MCC006", "MCC009")));
                        changed = true;
                    }
                    if (p.getDanhSachMinhChungTieuChi() == null || p.getDanhSachMinhChungTieuChi().isEmpty()) {
                        List<String> ids = new ArrayList<>();
                        for (int i=1; i<=52; i++) ids.add(String.format("MC%03d", i));
                        p.setDanhSachMinhChungTieuChi(ids);
                        changed = true;
                    }
                }
                if (changed) {
                    baoCaoPhanRepo.save(p);
                    partChanged++;
                }
            }
            System.out.println("[DataCompleteness] bổ sung dữ liệu thiếu: minhChung=" + mcChanged
            + ", minhChungChung=" + commonChanged + ", baoCaoTieuChi=" + reportChanged
            + ", baoCaoNhom=" + groupChanged + ", baoCaoPhan=" + partChanged);
        } catch (Exception e) {
            System.err.println("[DataCompleteness] Bỏ qua do Mongo chậm/lỗi: " + e.getMessage());
        }
    }
    private List<ChiBao> defaultIndicators(TieuChi c) {
        List<ChiBao> a = new ArrayList<>();
        for (int i=1; i<=3; i++) {
            ChiBao x = new ChiBao();
            x.setMaChiBao("CB-" + c.getId() + "-" + i);
            x.setSoChiBao(c.getId() + "." + i);
            x.setThuTu(i);
            x.setNoiDung(i == 1 ? "Có hồ sơ hoặc dữ liệu chứng minh việc thực hiện yêu cầu của tiêu chí."
            : i == 2 ? "Có bằng chứng triển khai thực tế và có thể truy xuất theo đơn vị/thời gian."
            : "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện.");
            a.add(x);
        }
        return a;
    }
    private List<Map<String,Object>> defaultEvidenceSuggestions(TieuChi c) {
        List<Map<String,Object>> out = new ArrayList<>();
        String[] names = {
            "Quy định/kế hoạch hoặc hồ sơ quản lý liên quan đến " + c.getId(),
            "Dữ liệu triển khai thực tế và biên bản rà soát " + c.getId(),
            "Báo cáo/khảo sát/phản hồi phục vụ cải tiến " + c.getId()
        }
        ;
        for (int i=0; i<names.length; i++) {
            Map<String,Object> m = new LinkedHashMap<>();
            m.put("maMinhChungGoiY", "GY-" + c.getId() + "-" + String.format("%02d", i+1));
            m.put("tenMinhChung", names[i]);
            m.put("moTa", "Tài liệu gợi ý phục vụ đối chiếu tiêu chí " + c.getId() + ".");
            out.add(m);
        }
        return out;
    }
    private String suffix(String id) {
        if (id == null) return "001";
        String n = id.replaceAll("\\D", "");
        return n.isBlank() ? "001" : String.format("%03d", Integer.parseInt(n));
    }
    private boolean blank(String s) {
        return s == null || s.isBlank();
    }
}
