package com.huit.kiemdinh.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection="baoCaoNhom")
public class BaoCaoNhom {
    @Id private String id;
    private String maKeHoach;
    private String maNhomTieuChi;
    private String maTieuChuan;
    private String tenBaoCao;
    private List<String> danhSachBaoCaoTieuChi = new ArrayList<>();
    private String moTaTongHop;
    private String diemManhTongHop;
    private String tonTaiTongHop;
    private String keHoachCaiTien;
    private String maThuKyNhom;
    private String maTruongNhom;
    private String trangThai;
    private String nhanXetTruongNhom;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;

    public BaoCaoNhom(){}
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getMaKeHoach(){return maKeHoach;} public void setMaKeHoach(String v){maKeHoach=v;}
    public String getMaNhomTieuChi(){return maNhomTieuChi;} public void setMaNhomTieuChi(String v){maNhomTieuChi=v;}
    public String getMaTieuChuan(){return maTieuChuan;} public void setMaTieuChuan(String v){maTieuChuan=v;}
    public String getTenBaoCao(){return tenBaoCao;} public void setTenBaoCao(String v){tenBaoCao=v;}
    public List<String> getDanhSachBaoCaoTieuChi(){return danhSachBaoCaoTieuChi;} public void setDanhSachBaoCaoTieuChi(List<String> v){danhSachBaoCaoTieuChi=v;}
    public String getMoTaTongHop(){return moTaTongHop;} public void setMoTaTongHop(String v){moTaTongHop=v;}
    public String getDiemManhTongHop(){return diemManhTongHop;} public void setDiemManhTongHop(String v){diemManhTongHop=v;}
    public String getTonTaiTongHop(){return tonTaiTongHop;} public void setTonTaiTongHop(String v){tonTaiTongHop=v;}
    public String getKeHoachCaiTien(){return keHoachCaiTien;} public void setKeHoachCaiTien(String v){keHoachCaiTien=v;}
    public String getMaThuKyNhom(){return maThuKyNhom;} public void setMaThuKyNhom(String v){maThuKyNhom=v;}
    public String getMaTruongNhom(){return maTruongNhom;} public void setMaTruongNhom(String v){maTruongNhom=v;}
    public String getTrangThai(){return trangThai;} public void setTrangThai(String v){trangThai=v;}
    public String getNhanXetTruongNhom(){return nhanXetTruongNhom;} public void setNhanXetTruongNhom(String v){nhanXetTruongNhom=v;}
    public LocalDateTime getNgayTao(){return ngayTao;} public void setNgayTao(LocalDateTime v){ngayTao=v;}
    public LocalDateTime getNgayCapNhat(){return ngayCapNhat;} public void setNgayCapNhat(LocalDateTime v){ngayCapNhat=v;}
}
