package com.huit.kiemdinh.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection="danhGiaMinhChung")
public class DanhGiaMinhChung {
    @Id private String id;
    private String maMinhChung;
    private String maTieuChi;
    private String capDuyet;
    private String maNguoiDuyet;
    private String trangThai;
    private String nhanXet;
    private LocalDateTime ngayDuyet;
    public DanhGiaMinhChung(){}
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getMaMinhChung(){return maMinhChung;} public void setMaMinhChung(String v){maMinhChung=v;}
    public String getMaTieuChi(){return maTieuChi;} public void setMaTieuChi(String v){maTieuChi=v;}
    public String getCapDuyet(){return capDuyet;} public void setCapDuyet(String v){capDuyet=v;}
    public String getMaNguoiDuyet(){return maNguoiDuyet;} public void setMaNguoiDuyet(String v){maNguoiDuyet=v;}
    public String getTrangThai(){return trangThai;} public void setTrangThai(String v){trangThai=v;}
    public String getNhanXet(){return nhanXet;} public void setNhanXet(String v){nhanXet=v;}
    public LocalDateTime getNgayDuyet(){return ngayDuyet;} public void setNgayDuyet(LocalDateTime v){ngayDuyet=v;}
}
