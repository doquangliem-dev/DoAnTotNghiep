package com.huit.kiemdinh.config;

import com.huit.kiemdinh.model.*;

import com.huit.kiemdinh.repository.*;

import org.springframework.boot.context.event.ApplicationReadyEvent;

import org.springframework.context.event.EventListener;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FileDataRepairRunner {
    private final MinhChungRepository minhChungRepo;
    private final MinhChungChungRepository minhChungChungRepo;
    private final BaoCaoTieuChiRepository baoCaoTieuChiRepo;
    private final BaoCaoNhomRepository baoCaoNhomRepo;
    private final BaoCaoPhanRepository baoCaoPhanRepo;
    public FileDataRepairRunner(MinhChungRepository minhChungRepo,
    MinhChungChungRepository minhChungChungRepo,
    BaoCaoTieuChiRepository baoCaoTieuChiRepo,
    BaoCaoNhomRepository baoCaoNhomRepo,
    BaoCaoPhanRepository baoCaoPhanRepo) {
        this.minhChungRepo = minhChungRepo;
        this.minhChungChungRepo = minhChungChungRepo;
        this.baoCaoTieuChiRepo = baoCaoTieuChiRepo;
        this.baoCaoNhomRepo = baoCaoNhomRepo;
        this.baoCaoPhanRepo = baoCaoPhanRepo;
    }
    @EventListener(ApplicationReadyEvent.class)
    public void scheduleRepair() {
        Thread worker = new Thread(this::repairSafely, "mongo-file-repair");
        worker.setDaemon(true);
        worker.start();
    }
    private void repairSafely() {
        try {
            int mc = repairMinhChung();
            int mcc = repairMinhChungChung();
            int bc = repairBaoCaoTieuChi();
            int bcn = repairBaoCaoNhom();
            int bcp = repairBaoCaoPhan();
            System.out.println("[FileRepair] Mongo file paths repaired: minhChung=" + mc
            + ", minhChungChung=" + mcc + ", baoCaoTieuChi=" + bc
            + ", baoCaoNhom=" + bcn + ", baoCaoPhan=" + bcp);
        } catch (Exception e) {
            System.err.println("[FileRepair] Bỏ qua sửa file nền do Mongo chậm/lỗi: " + e.getMessage());
        }
    }
    private int repairMinhChung() {
        int changed = 0;
        for (MinhChung x : minhChungRepo.findAll()) {
            Integer n = numberOf(x.getId(), "MC", 1, 52);
            if (n == null) continue;
            String tc = x.getMaTieuChi();
            if (tc == null || tc.isBlank()) continue;
            String expected = "/uploads/minhchung/MC" + pad3(n) + "_" + tc + ".pdf";
            if (shouldRepairSeedPath(x.getDuongDanFile(), "/uploads/minhchung/", "MC")) {
                x.setDuongDanFile(expected);
                x.setLoaiFile("pdf");
                minhChungRepo.save(x);
                changed++;
            }
        }
        return changed;
    }
    private int repairMinhChungChung() {
        int changed = 0;
        for (MinhChungChung x : minhChungChungRepo.findAll()) {
            Integer n = numberOf(x.getId(), "MCC", 1, 10);
            if (n == null) continue;
            String expectedName = "MCC" + pad3(n) + ".pdf";
            String expected = "/uploads/minh-chung-chung/" + expectedName;
            if (shouldRepairSeedPath(x.getDuongDanFile(), "/uploads/minh-chung-chung/", "MCC")) {
                x.setDuongDanFile(expected);
                x.setTenFile(expectedName);
                x.setLoaiFile("pdf");
                minhChungChungRepo.save(x);
                changed++;
            }
        }
        return changed;
    }
    private int repairBaoCaoTieuChi() {
        int changed = 0;
        for (BaoCaoTieuChi x : baoCaoTieuChiRepo.findAll()) {
            Integer n = numberOf(x.getId(), "BC", 1, 52);
            if (n == null) continue;
            String tc = x.getMaTieuChi();
            if (tc == null || tc.isBlank()) continue;
            String name = "BC" + pad3(n) + "_" + tc + ".pdf";
            String expected = "/uploads/bao-cao/" + name;
            if (shouldRepairReportPath(x.getDuongDanFile(), "BC")) {
                x.setDuongDanFile(expected);
                x.setTenFileBaoCao(name);
                baoCaoTieuChiRepo.save(x);
                changed++;
            }
        }
        return changed;
    }
    private int repairBaoCaoNhom() {
        int changed = 0;
        for (BaoCaoNhom x : baoCaoNhomRepo.findAll()) {
            Integer std = standardNumber(x.getMaTieuChuan());
            if (std == null || std < 1 || std > 8) continue;
            String name = "BCN" + pad3(std) + "_TC" + std + ".pdf";
            String expected = "/uploads/bao-cao/" + name;
            if (shouldRepairReportPath(x.getDuongDanFile(), "BCN")) {
                x.setDuongDanFile(expected);
                x.setTenFileBaoCao(name);
                baoCaoNhomRepo.save(x);
                changed++;
            }
        }
        return changed;
    }
    private int repairBaoCaoPhan() {
        int changed = 0;
        Map<Integer,String> names = Map.of(
        1, "BCP001_Phan1_KhaiQuat.pdf",
        3, "BCP003_Phan3_KetLuan.pdf",
        4, "BCP004_Phan4_PhuLuc.pdf"
        );
        for (BaoCaoPhan x : baoCaoPhanRepo.findAll()) {
            String name = names.get(x.getSoPhan());
            if (name == null) continue;
            String expected = "/uploads/bao-cao/" + name;
            if (shouldRepairReportPath(x.getDuongDanFile(), "BCP")) {
                x.setDuongDanFile(expected);
                x.setTenFileBaoCao(name);
                baoCaoPhanRepo.save(x);
                changed++;
            }
        }
        return changed;
    }
    private boolean shouldRepairSeedPath(String current, String prefix, String seedPrefix) {
        if (current == null || current.isBlank()) return true;
        if (!current.startsWith(prefix)) return false;
        String file = current.substring(current.lastIndexOf('/') + 1);
        if (file.matches(".*_\\d{13}(?:_[^.]*)?\\.[A-Za-z0-9]+$")) return false;
        return file.startsWith(seedPrefix);
    }
    private boolean shouldRepairReportPath(String current, String seedPrefix) {
        if (current == null || current.isBlank()) return true;
        if (!current.startsWith("/uploads/bao-cao/")) return false;
        String file = current.substring(current.lastIndexOf('/') + 1);
        if (file.matches(".*_\\d{13}(?:_[^.]*)?\\.[A-Za-z0-9]+$")) return false;
        return file.startsWith(seedPrefix) || file.equals("PHAN2_BAO_CAO_TDG_DAY_DU.pdf");
    }
    private Integer numberOf(String id, String prefix, int min, int max) {
        if (id == null || !id.startsWith(prefix)) return null;
        try {
            int n = Integer.parseInt(id.substring(prefix.length()));
            return n >= min && n <= max ? n : null;
        } catch (Exception e) {
            return null;
        }
    }
    private Integer standardNumber(String code) {
        if (code == null) return null;
        try {
            String s = code.toUpperCase(Locale.ROOT).replace("TC", "").trim();
            return Integer.parseInt(s);
        } catch (Exception e) {
            return null;
        }
    }
    private String pad3(int n) {
        return String.format("%03d", n);
    }
}
