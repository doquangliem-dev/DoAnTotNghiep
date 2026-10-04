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
        if(!hasRole(session,"Quản trị")) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Chỉ Quản trị viên được chỉnh sửa minh chứng chung ở màn hình này");
        return s.saveMinhChungChung(x);
    }
    @PostMapping(value="/minh-chung-chung/{id}/upload",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public MinhChungChung uploadMcc(@PathVariable String id,@RequestParam("file") MultipartFile file,HttpSession session){
        if(!hasRole(session,"Quản trị")) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Không có quyền chỉnh sửa minh chứng chung");
        return s.uploadMinhChungChung(id,file);
    }
    @DeleteMapping("/minh-chung-chung/{id}/file")
    public MinhChungChung deleteMccFile(@PathVariable String id,HttpSession session){
        if(!hasRole(session,"Quản trị")) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Không có quyền chỉnh sửa minh chứng chung");
        return s.deleteMinhChungChungFile(id);
    }

    @GetMapping("/bao-cao") public List<BaoCaoTieuChi> bc(){return s.baoCao();}
    @GetMapping("/ke-hoach") public List<KeHoachTuDanhGia> keHoachChung(){return s.keHoach();}
    @GetMapping("/bao-cao/{id}") public BaoCaoTieuChi getBc(@PathVariable String id){return s.getBaoCao(id);}
    @PostMapping("/bao-cao") public BaoCaoTieuChi saveBc(@RequestBody BaoCaoTieuChi x){return s.saveBaoCao(x);}

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
    @GetMapping("/thanh-vien/thu-ky-nhom/bao-cao-nhom")
    public List<BaoCaoNhom> tvBaoCaoNhom(HttpSession session){return s.baoCaoNhomCuaThuKy(currentUser(session));}
    @PostMapping("/thanh-vien/thu-ky-nhom/bao-cao-nhom")
    public BaoCaoNhom tvSaveBaoCaoNhom(@RequestBody BaoCaoNhom x,HttpSession session){return s.saveBaoCaoNhomThuKy(x,currentUser(session));}
    @PostMapping("/thanh-vien/thu-ky-nhom/bao-cao-nhom/{id}/gui-duyet")
    public BaoCaoNhom tvGuiBaoCaoNhom(@PathVariable String id,HttpSession session){return s.guiBaoCaoNhom(id,currentUser(session));}

    // ===== THƯ KÝ CHƯƠNG TRÌNH =====
    @GetMapping("/thu-ky/dashboard")
    public Map<String,Object> tkDashboard(){Map<String,Object> m=new LinkedHashMap<>();m.put("keHoach",s.keHoach().size());m.putAll(s.tongHopThuKy());return m;}
    @GetMapping("/thu-ky/ke-hoach") public List<KeHoachTuDanhGia> tkKeHoach(){return s.keHoach();}
    @PostMapping("/thu-ky/ke-hoach") public KeHoachTuDanhGia tkSaveKh(@RequestBody KeHoachTuDanhGia x){return s.saveKeHoach(x);}
    @PostMapping("/thu-ky/ke-hoach/{id}/gui-duyet") public KeHoachTuDanhGia tkGuiKh(@PathVariable String id,HttpSession session){return s.guiKeHoachKhaoThi(id,currentUser(session));}
    @GetMapping("/thu-ky/minh-chung") public List<MinhChung> tkMinhChung(){return s.minhChungChoThuKy();}
    @PostMapping("/thu-ky/minh-chung/{id}/duyet")
    public DanhGiaMinhChung tkDuyetMc(@PathVariable String id,@RequestBody Map<String,String> b,HttpSession session){return s.thuKyDuyetMinhChung(id,b.get("ketQua"),b.get("nhanXet"),currentUser(session));}
    @GetMapping("/thu-ky/tong-hop") public Map<String,Object> tkTongHop(@RequestParam(required=false) String maKeHoach){return s.tongHopHoiDong(maKeHoach);}

    // ===== BỘ PHẬN KHẢO THÍ =====
    @GetMapping("/khao-thi/dashboard")
    public Map<String,Object> ktDashboard(){Map<String,Object> m=new LinkedHashMap<>();List<KeHoachTuDanhGia> a=s.keHoach();m.put("keHoach",a.size());m.put("choDuyet",a.stream().filter(x->"Chờ khảo thí duyệt".equalsIgnoreCase(x.getTrangThai())).count());m.putAll(s.tongHopHoiDong());return m;}
    @GetMapping("/khao-thi/ke-hoach") public List<KeHoachTuDanhGia> ktKeHoach(){return s.keHoach();}
    @PostMapping("/khao-thi/ke-hoach/{id}/duyet")
    public KeHoachTuDanhGia ktDuyetKh(@PathVariable String id,@RequestBody Map<String,String> b,HttpSession session){return s.khaoThiDuyetKeHoach(id,b.get("ketQua"),b.get("nhanXet"),currentUser(session));}
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
