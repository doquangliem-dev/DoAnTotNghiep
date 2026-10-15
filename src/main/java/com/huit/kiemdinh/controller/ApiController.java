package com.huit.kiemdinh.controller;

import com.huit.kiemdinh.model.*;
import com.huit.kiemdinh.service.HeThongService;
import com.huit.kiemdinh.security.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final HeThongService s;
    public ApiController(HeThongService s){this.s=s;}

    @GetMapping("/dashboard") public Map<String,Object> dashboard(){return s.dashboard();}

    @GetMapping("/ctdt") public List<ChuongTrinhDaoTao> ctdt(){return s.ctdt();}
    @GetMapping("/ctdt/{id}") public ChuongTrinhDaoTao getCtdt(@PathVariable String id){return s.getCtdt(id);}
    @PostMapping("/ctdt") public ChuongTrinhDaoTao saveCtdt(@RequestBody ChuongTrinhDaoTao x){return s.saveCtdt(x);}
    @PostMapping("/ctdt/{id}/phien-ban") public ChuongTrinhDaoTao addVersion(@PathVariable String id,@RequestBody PhienBanCTDT x){return s.addPhienBan(id,x);}
    @PostMapping("/ctdt/{id}/phien-ban/{maPhienBan}/trang-thai")
    public ChuongTrinhDaoTao updateVersionStatus(@PathVariable String id,@PathVariable String maPhienBan,@RequestBody Map<String,String> body){
        return s.updatePhienBanTrangThai(id,maPhienBan,body.get("trangThai"));
    }
    @PostMapping(value="/ctdt/{id}/phien-ban/{maPhienBan}/upload",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ChuongTrinhDaoTao uploadCtdt(@PathVariable String id,@PathVariable String maPhienBan,@RequestParam("file") MultipartFile file){return s.uploadCtdt(id,maPhienBan,file);}
    @DeleteMapping("/ctdt/{id}/phien-ban/{maPhienBan}/file")
    public ChuongTrinhDaoTao deleteCtdtFile(@PathVariable String id,@PathVariable String maPhienBan){return s.deleteCtdtFile(id,maPhienBan);}

    @GetMapping("/bo-tieu-chuan") public List<BoTieuChuan> bo(){return s.boTieuChuan();}
    @PostMapping("/bo-tieu-chuan") public BoTieuChuan saveBo(@RequestBody BoTieuChuan x){return s.saveBo(x);}
    @DeleteMapping("/bo-tieu-chuan/{id}") public void delBo(@PathVariable String id){s.deleteBo(id);}

    @GetMapping("/tieu-chuan") public List<TieuChuan> tc(){return s.tieuChuan();}
    @PostMapping("/tieu-chuan") public TieuChuan saveTc(@RequestBody TieuChuan x){return s.saveTieuChuan(x);}
    @DeleteMapping("/tieu-chuan/{id}") public void delTc(@PathVariable String id){s.deleteTieuChuan(id);}

    @GetMapping("/tieu-chi") public List<TieuChi> tchi(){return s.tieuChi();}
    @PostMapping("/tieu-chi") public TieuChi saveTchi(@RequestBody TieuChi x){return s.saveTieuChi(x);}
    @DeleteMapping("/tieu-chi/{id}") public void delTchi(@PathVariable String id){s.deleteTieuChi(id);}

    @GetMapping("/nguoi-dung") public List<NguoiDung> user(){return s.nguoiDung();}
    @PostMapping("/nguoi-dung") public NguoiDung saveUser(@RequestBody NguoiDung x){return s.saveNguoiDung(x);}
    @PostMapping("/nguoi-dung/{id}/toggle") public NguoiDung toggle(@PathVariable String id){return s.toggleUser(id);}
    @GetMapping("/vai-tro") public List<VaiTro> role(){return s.vaiTro();}

    @GetMapping("/nhom-tieu-chi") public List<NhomTieuChi> group(){return s.nhomTieuChi();}
    @PostMapping("/nhom-tieu-chi") public NhomTieuChi saveGroup(@RequestBody NhomTieuChi x){return s.saveNhom(x);}
    @DeleteMapping("/nhom-tieu-chi/{id}") public void deleteGroup(@PathVariable String id){s.deleteNhom(id);}
    @GetMapping("/phan-cong") public List<PhanCong> pc(){return s.phanCong();}
    @PostMapping("/phan-cong") public PhanCong savePc(@RequestBody PhanCong x){return s.savePhanCong(x);}
    @PostMapping("/phan-cong/{id}/huy") public PhanCong cancelPc(@PathVariable String id){return s.cancelPhanCong(id);}

    @GetMapping("/minh-chung") public List<MinhChung> mc(@RequestParam(required=false) String loai){return s.minhChung(loai);}
    @PostMapping("/minh-chung")
    public MinhChung saveMc(@RequestBody MinhChung x,HttpSession session){
        // Admin chỉ được chỉnh sửa minh chứng đã tồn tại, không được tạo mới.
        boolean isAdmin = hasRole(session,"Quản trị");
        if(isAdmin && (x.getId()==null || x.getId().isBlank()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Quản trị viên không được thêm minh chứng");
        return s.saveMinhChung(x);
    }
    @PostMapping(value="/minh-chung/{id}/upload",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public MinhChung uploadMinhChung(@PathVariable String id,@RequestParam("file") MultipartFile file){return s.uploadMinhChung(id,file);}
    @DeleteMapping("/minh-chung/{id}/file") public MinhChung deleteMinhChungFile(@PathVariable String id){return s.deleteMinhChungFile(id);}
    @DeleteMapping("/minh-chung/{id}")
    public void delMc(@PathVariable String id,HttpSession session){
        if(hasRole(session,"Quản trị")) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Quản trị viên không được xóa minh chứng");
        s.deleteMinhChung(id);
    }

    @GetMapping("/minh-chung-chung") public List<MinhChungChung> mcc(){return s.minhChungChung();}
    @PostMapping("/minh-chung-chung")
    public MinhChungChung saveMcc(@RequestBody MinhChungChung x,HttpSession session){
        if(!hasRole(session,"Quản trị")&&!hasRole(session,"Thư ký"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Chỉ Quản trị viên hoặc Thư ký chương trình được thêm/sửa minh chứng cấp Trường/Khoa");
        if(x.getMaNguoiTao()==null||x.getMaNguoiTao().isBlank()) x.setMaNguoiTao(currentUser(session));
        return s.saveMinhChungChung(x);
    }
    @PostMapping(value="/minh-chung-chung/{id}/upload",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public MinhChungChung uploadMcc(@PathVariable String id,@RequestParam("file") MultipartFile file,HttpSession session){
        if(!hasRole(session,"Quản trị")&&!hasRole(session,"Thư ký")) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Không có quyền chỉnh sửa minh chứng cấp Trường/Khoa");
        return s.uploadMinhChungChung(id,file);
    }
    @DeleteMapping("/minh-chung-chung/{id}/file")
    public MinhChungChung deleteMccFile(@PathVariable String id,HttpSession session){
        if(!hasRole(session,"Quản trị")&&!hasRole(session,"Thư ký")) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Không có quyền chỉnh sửa minh chứng cấp Trường/Khoa");
        return s.deleteMinhChungChungFile(id);
    }
    @DeleteMapping("/minh-chung-chung/{id}")
    public void deleteMcc(@PathVariable String id,HttpSession session){
        if(!hasRole(session,"Quản trị")&&!hasRole(session,"Thư ký")) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Không có quyền xóa minh chứng cấp Trường/Khoa");
        s.deleteMinhChungChung(id);
    }

    @GetMapping("/bao-cao") public List<BaoCaoTieuChi> bc(){return s.baoCao();}
    @GetMapping("/ke-hoach") public List<KeHoachTuDanhGia> keHoachChung(){return s.keHoach();}
    @GetMapping("/bao-cao/{id}") public BaoCaoTieuChi getBc(@PathVariable String id){return s.getBaoCao(id);}
    @PostMapping("/bao-cao") public BaoCaoTieuChi saveBc(@RequestBody BaoCaoTieuChi x){return s.saveBaoCao(x);}
    @PostMapping("/admin/bao-cao/{id}/xu-ly")
    public BaoCaoTieuChi adminXuLyBaoCao(@PathVariable String id,@RequestBody Map<String,String> body,HttpSession session){
        return s.adminXuLyBaoCao(id,body.get("ketQua"),body.get("nhanXet"),currentUser(session));
    }
    @GetMapping("/admin/thong-ke") public Map<String,Object> adminThongKe(@RequestParam(required=false) String maKeHoach){return s.thongKeHeThong(maKeHoach);}
    @GetMapping("/admin/bao-cao-tong") public Map<String,Object> adminBaoCaoTong(@RequestParam(required=false) String maKeHoach){return s.cauTrucBaoCao(maKeHoach);}
    @GetMapping("/admin/phan-cong-bao-cao") public List<PhanCongBaoCao> adminPhanCongBaoCao(){return s.phanCongBaoCao();}
    @PostMapping("/admin/phan-cong-bao-cao") public PhanCongBaoCao adminSavePhanCongBaoCao(@RequestBody PhanCongBaoCao x,HttpSession session){return s.savePhanCongBaoCao(x,currentUser(session));}
    @PostMapping("/admin/bao-cao-phan/{id}/xu-ly") public BaoCaoPhan adminXuLyBaoCaoPhan(@PathVariable String id,@RequestBody Map<String,String> b,HttpSession session){return s.adminXuLyBaoCaoPhan(id,b.get("ketQua"),b.get("nhanXet"),currentUser(session));}

    // ===== API DÀNH CHO HỘI ĐỒNG TỰ ĐÁNH GIÁ =====
    @GetMapping("/hoi-dong/dashboard") public Map<String,Object> hoiDongDashboard(){return s.hoiDongDashboard();}
    @GetMapping("/hoi-dong/minh-chung") public List<MinhChung> hoiDongMinhChung(){return s.minhChungHoiDong();}
    @GetMapping("/hoi-dong/danh-gia-minh-chung") public List<DanhGiaMinhChung> lichSuDuyetMinhChung(){return s.danhGiaMinhChung();}
    @GetMapping("/hoi-dong/bao-cao") public List<BaoCaoTieuChi> hoiDongBaoCao(){return s.baoCaoHoiDong();}
    @GetMapping("/hoi-dong/ke-hoach") public List<KeHoachTuDanhGia> hoiDongKeHoach(){return s.keHoach();}
    @GetMapping("/hoi-dong/danh-gia-tieu-chi") public List<DanhGiaTieuChi> danhGiaTieuChi(){return s.danhGiaTieuChi();}
    @PostMapping("/hoi-dong/danh-gia-tieu-chi")
    public DanhGiaTieuChi saveDanhGiaTieuChi(@RequestBody DanhGiaTieuChi x,HttpSession session){return s.saveDanhGiaTieuChi(x,currentUser(session));}
    @PostMapping("/hoi-dong/bao-cao/{id}/duyet")
    public BaoCaoTieuChi duyetBaoCao(@PathVariable String id,@RequestBody Map<String,String> body,HttpSession session){
        return s.duyetBaoCao(id,body.get("trangThai"),body.get("nhanXet"),body.get("khuyenNghi"),currentUser(session));
    }
    @GetMapping("/hoi-dong/tong-hop")
    public Map<String,Object> tongHopHoiDong(@RequestParam(required=false) String maKeHoach){return s.tongHopHoiDong(maKeHoach);}
    @GetMapping("/hoi-dong/bao-cao-nhom") public List<BaoCaoNhom> hoiDongBaoCaoNhom(){return s.baoCaoNhomHoiDong();}
    @GetMapping("/hoi-dong/bao-cao-phan") public List<BaoCaoPhan> hoiDongBaoCaoPhan(){return s.baoCaoPhanChoHoiDong();}
    @PostMapping("/hoi-dong/bao-cao-phan/{id}/duyet") public BaoCaoPhan hoiDongDuyetBaoCaoPhan(@PathVariable String id,@RequestBody Map<String,String> b,HttpSession session){return s.hoiDongXuLyBaoCaoPhan(id,b.get("ketQua"),b.get("nhanXet"),currentUser(session));}


    // ===== TRƯỞNG NHÓM TIÊU CHÍ =====
    @GetMapping("/truong-nhom/dashboard")
    public Map<String,Object> tnDashboard(HttpSession session){return s.roleDashboard(currentUser(session));}
    @GetMapping("/truong-nhom/nhom")
    public List<NhomTieuChi> tnNhom(HttpSession session){return s.nhomCuaNguoiDung(currentUser(session));}
    @GetMapping("/truong-nhom/cong-viec")
    public List<PhanCong> tnCongViec(HttpSession session){return s.congViecCuaNguoiDung(currentUser(session));}
    @GetMapping("/truong-nhom/phan-cong")
    public List<PhanCong> tnPhanCongList(HttpSession session){return s.phanCongTrongPhamViTruongNhom(currentUser(session));}
    @GetMapping("/truong-nhom/minh-chung")
    public List<MinhChung> tnMinhChung(HttpSession session){return s.minhChungTruongNhom(currentUser(session));}
    @GetMapping("/truong-nhom/bao-cao")
    public List<BaoCaoTieuChi> tnBaoCao(HttpSession session){return s.baoCaoTruongNhom(currentUser(session));}
    @PostMapping("/truong-nhom/bao-cao/{id}/duyet")
    public BaoCaoTieuChi tnDuyetBaoCao(@PathVariable String id,@RequestBody Map<String,String> body,HttpSession session){
        return s.truongNhomDuyetBaoCaoTieuChi(id,body.get("ketQua"),body.get("nhanXet"),currentUser(session));
    }
    @PostMapping("/truong-nhom/phan-cong-tieu-chi")
    public PhanCong tnPhanCong(@RequestBody PhanCong x,HttpSession session){return s.truongNhomPhanCongTieuChi(x,currentUser(session));}
    @PostMapping("/truong-nhom/phan-cong-nhieu-tieu-chi")
    public List<PhanCong> tnPhanCongNhieu(@RequestBody Map<String,Object> body,HttpSession session){
        return s.truongNhomPhanCongNhieuTieuChi(body,currentUser(session));
    }
    @PostMapping("/truong-nhom/minh-chung")
    public MinhChung tnSaveMc(@RequestBody MinhChung x,HttpSession session){return s.luuMinhChungNguoiDung(x,currentUser(session),true);}
    @PostMapping(value="/truong-nhom/minh-chung/{id}/upload",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public MinhChung tnUploadMc(@PathVariable String id,@RequestParam("file") MultipartFile file,HttpSession session){return s.uploadMinhChungNguoiDung(id,file,currentUser(session),true);}
    @PostMapping("/truong-nhom/minh-chung/{id}/duyet")
    public DanhGiaMinhChung tnDuyetMc(@PathVariable String id,@RequestBody Map<String,String> b,HttpSession session){return s.truongNhomDuyetMinhChung(id,b.get("ketQua"),b.get("nhanXet"),currentUser(session));}
    @GetMapping("/truong-nhom/bao-cao-nhom")
    public List<BaoCaoNhom> tnBaoCaoNhom(HttpSession session){return s.baoCaoNhomCuaTruongNhom(currentUser(session));}
    @PostMapping("/truong-nhom/bao-cao-nhom/{id}/duyet")
    public BaoCaoNhom tnDuyetBaoCaoNhom(@PathVariable String id,@RequestBody Map<String,String> b,HttpSession session){
        return s.truongNhomDuyetBaoCaoNhom(id,b.get("ketQua"),b.get("nhanXet"),currentUser(session));
    }

    // ===== THÀNH VIÊN NHÓM =====
    @GetMapping("/thanh-vien/dashboard")
    public Map<String,Object> tvDashboard(HttpSession session){return s.roleDashboard(currentUser(session));}
    @GetMapping("/thanh-vien/cong-viec")
    public List<PhanCong> tvCongViec(HttpSession session){return s.congViecCuaNguoiDung(currentUser(session));}
    @GetMapping("/thanh-vien/bao-cao")
    public List<BaoCaoTieuChi> tvBaoCao(HttpSession session){return s.baoCaoCuaNguoiDung(currentUser(session));}
    @GetMapping("/thanh-vien/minh-chung")
    public List<MinhChung> tvMinhChung(HttpSession session){return s.minhChungThanhVien(currentUser(session));}
    @PostMapping("/thanh-vien/minh-chung")
    public MinhChung tvSaveMc(@RequestBody MinhChung x,HttpSession session){return s.luuMinhChungNguoiDung(x,currentUser(session),false);}
    @PostMapping(value="/thanh-vien/minh-chung/{id}/upload",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public MinhChung tvUploadMc(@PathVariable String id,@RequestParam("file") MultipartFile file,HttpSession session){return s.uploadMinhChungNguoiDung(id,file,currentUser(session),false);}
    @PostMapping("/thanh-vien/bao-cao")
    public BaoCaoTieuChi tvSaveBc(@RequestBody BaoCaoTieuChi x,HttpSession session){return s.luuBaoCaoThanhVien(x,currentUser(session));}
    @PostMapping("/thanh-vien/bao-cao/{id}/gui-duyet")
    public BaoCaoTieuChi tvGuiDuyetBc(@PathVariable String id,HttpSession session){return s.guiBaoCaoThanhVien(id,currentUser(session));}
    @PostMapping(value="/thanh-vien/bao-cao/{id}/upload",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaoCaoTieuChi tvUploadBaoCao(@PathVariable String id,@RequestParam("file") MultipartFile file,HttpSession session){return s.uploadBaoCaoThanhVien(id,file,currentUser(session));}
    @GetMapping("/thanh-vien/thu-ky-nhom/bao-cao-nhom")
    public List<BaoCaoNhom> tvBaoCaoNhom(HttpSession session){return s.baoCaoNhomCuaThuKy(currentUser(session));}
    @GetMapping("/thanh-vien/thu-ky-nhom/phan-cong")
    public List<PhanCong> tvPhanCongNhom(HttpSession session){return s.phanCongNhomCuaThuKy(currentUser(session));}
    @GetMapping("/thanh-vien/thu-ky-nhom/thong-tin")
    public Map<String,Object> tvThongTinThuKyNhom(HttpSession session){return s.thongTinThuKyNhom(currentUser(session));}
    @GetMapping("/thanh-vien/thu-ky-nhom/nguon-bao-cao")
    public Map<String,Object> tvNguonBaoCaoNhom(@RequestParam String maNhom,@RequestParam String maKeHoach,@RequestParam String maTieuChuan,HttpSession session){
        return s.nguonBaoCaoNhom(currentUser(session),maNhom,maKeHoach,maTieuChuan);
    }
    @GetMapping("/thanh-vien/danh-gia-minh-chung")
    public List<DanhGiaMinhChung> tvDanhGiaMinhChung(HttpSession session){return s.danhGiaMinhChungCuaNguoiDung(currentUser(session));}
    @PostMapping("/thanh-vien/thu-ky-nhom/bao-cao-nhom")
    public BaoCaoNhom tvSaveBaoCaoNhom(@RequestBody BaoCaoNhom x,HttpSession session){return s.saveBaoCaoNhomThuKy(x,currentUser(session));}
    @PostMapping("/thanh-vien/thu-ky-nhom/bao-cao-nhom/{id}/gui-duyet")
    public BaoCaoNhom tvGuiBaoCaoNhom(@PathVariable String id,HttpSession session){return s.guiBaoCaoNhom(id,currentUser(session));}

    // ===== THƯ KÝ CHƯƠNG TRÌNH =====
    @GetMapping("/thu-ky/dashboard")
    public Map<String,Object> tkDashboard(){return s.dashboardThuKy();}
    @GetMapping("/thu-ky/bao-cao")
    public Map<String,Object> tkBaoCao(@RequestParam(required=false) String maKeHoach){return s.theoDoiBaoCaoThuKy(maKeHoach);}
    @GetMapping("/thu-ky/ke-hoach") public List<KeHoachTuDanhGia> tkKeHoach(){return s.keHoach();}
    @PostMapping("/thu-ky/ke-hoach") public KeHoachTuDanhGia tkSaveKh(@RequestBody KeHoachTuDanhGia x){return s.saveKeHoach(x);}
    @PostMapping("/thu-ky/ke-hoach/{id}/gui-duyet") public KeHoachTuDanhGia tkGuiKh(@PathVariable String id,HttpSession session){return s.guiKeHoachKhaoThi(id,currentUser(session));}
    @GetMapping("/thu-ky/minh-chung") public List<MinhChung> tkMinhChung(){return s.minhChungChoThuKy();}
    @PostMapping("/thu-ky/minh-chung/{id}/duyet")
    public DanhGiaMinhChung tkDuyetMc(@PathVariable String id,@RequestBody Map<String,String> b,HttpSession session){return s.thuKyDuyetMinhChung(id,b.get("ketQua"),b.get("nhanXet"),currentUser(session));}
    @GetMapping("/thu-ky/tong-hop") public Map<String,Object> tkTongHop(@RequestParam(required=false) String maKeHoach){return s.tongHopHoiDong(maKeHoach);}

    // ===== NHÂN SỰ PHỤ TRÁCH BÁO CÁO (PHẦN 1, 3, 4) =====
    @GetMapping("/nhan-su-bao-cao/dashboard") public Map<String,Object> nsbcDashboard(HttpSession session){Map<String,Object> m=new LinkedHashMap<>();List<PhanCongBaoCao> jobs=s.congViecBaoCao(currentUser(session));m.put("congViec",jobs.size());m.put("dangThucHien",jobs.stream().filter(x->!"Hoàn thành".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse(""))).count());m.put("baoCaoPhan",s.baoCaoPhanCuaNguoiDung(currentUser(session)).size());return m;}
    @GetMapping("/nhan-su-bao-cao/cong-viec") public List<PhanCongBaoCao> nsbcCongViec(HttpSession session){return s.congViecBaoCao(currentUser(session));}
    @GetMapping("/nhan-su-bao-cao/bao-cao-phan") public List<BaoCaoPhan> nsbcBaoCao(HttpSession session){return s.baoCaoPhanCuaNguoiDung(currentUser(session));}
    @PostMapping("/nhan-su-bao-cao/bao-cao-phan") public BaoCaoPhan nsbcSave(@RequestBody BaoCaoPhan x,HttpSession session){return s.saveBaoCaoPhan(x,currentUser(session));}
    @PostMapping("/nhan-su-bao-cao/bao-cao-phan/{id}/gui-duyet") public BaoCaoPhan nsbcGui(@PathVariable String id,HttpSession session){return s.guiBaoCaoPhan(id,currentUser(session));}

    // ===== BỘ PHẬN KHẢO THÍ =====
    @GetMapping("/khao-thi/dashboard")
    public Map<String,Object> ktDashboard(){Map<String,Object> m=new LinkedHashMap<>();List<KeHoachTuDanhGia> a=s.keHoach();m.put("keHoach",a.size());m.put("choDuyet",a.stream().filter(x->"Chờ khảo thí duyệt".equalsIgnoreCase(x.getTrangThai())).count());m.putAll(s.tongHopHoiDong());return m;}
    @GetMapping("/khao-thi/ke-hoach") public List<KeHoachTuDanhGia> ktKeHoach(){return s.keHoach();}
    @PostMapping("/khao-thi/ke-hoach/{id}/duyet")
    public KeHoachTuDanhGia ktDuyetKh(@PathVariable String id,@RequestBody Map<String,String> b,HttpSession session){return s.khaoThiDuyetKeHoach(id,b.get("ketQua"),b.get("nhanXet"),currentUser(session));}
    @GetMapping("/khao-thi/bao-cao") public List<BaoCaoTieuChi> ktBaoCao(){return s.baoCao().stream().filter(x->"Chờ TT QLCL duyệt".equalsIgnoreCase(x.getTrangThai())).toList();}
    @GetMapping("/khao-thi/bao-cao-phan") public List<BaoCaoPhan> ktBaoCaoPhan(){return s.baoCaoPhanChoQlcl();}
    @PostMapping("/khao-thi/bao-cao-phan/{id}/duyet") public BaoCaoPhan ktDuyetBaoCaoPhan(@PathVariable String id,@RequestBody Map<String,String> b,HttpSession session){return s.qlclXuLyBaoCaoPhan(id,b.get("ketQua"),b.get("nhanXet"),currentUser(session));}
    @PostMapping("/khao-thi/bao-cao/{id}/duyet")
    public BaoCaoTieuChi ktDuyetBaoCao(@PathVariable String id,@RequestBody Map<String,String> b,HttpSession session){return s.qlclDuyetBaoCao(id,b.get("ketQua"),b.get("nhanXet"),currentUser(session));}
    @GetMapping("/khao-thi/tong-hop") public Map<String,Object> ktTongHop(@RequestParam(required=false) String maKeHoach){return s.tongHopHoiDong(maKeHoach);}

    private String currentUser(HttpSession session){ return (String)session.getAttribute(AuthService.SESSION_USER_ID); }

    private boolean hasRole(HttpSession session,String keyword){
        Object o=session.getAttribute(AuthService.SESSION_ROLE_NAMES);
        if(!(o instanceof List<?> list)) return false;
        String k=normalizeRole(keyword);
        for(Object v:list) if(v!=null && normalizeRole(String.valueOf(v)).contains(k)) return true;
        return false;
    }
    private String normalizeRole(String s){
        if(s==null)return "";
        return java.text.Normalizer.normalize(s,java.text.Normalizer.Form.NFD).replaceAll("\\p{M}","").replace('đ','d').replace('Đ','D').toLowerCase(java.util.Locale.ROOT);
    }

}
