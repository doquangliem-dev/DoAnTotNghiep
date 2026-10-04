package com.huit.kiemdinh.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection="danhGiaTieuChi")
public class DanhGiaTieuChi {
    @Id private String id;
    private String maKeHoach;
    private String maTieuChi;
    private Integer lanDanhGia;
    private String ketQua;
    private String nhanXet;
    private String maNguoiDanhGia;
    private LocalDateTime ngayDanhGia;
    public DanhGiaTieuChi(){}
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getMaKeHoach(){return maKeHoach;} public void setMaKeHoach(String v){maKeHoach=v;}
    public String getMaTieuChi(){return maTieuChi;} public void setMaTieuChi(String v){maTieuChi=v;}
    public Integer getLanDanhGia(){return lanDanhGia;} public void setLanDanhGia(Integer v){lanDanhGia=v;}
    public String getKetQua(){return ketQua;} public void setKetQua(String v){ketQua=v;}
    public String getNhanXet(){return nhanXet;} public void setNhanXet(String v){nhanXet=v;}
    public String getMaNguoiDanhGia(){return maNguoiDanhGia;} public void setMaNguoiDanhGia(String v){maNguoiDanhGia=v;}
    public LocalDateTime getNgayDanhGia(){return ngayDanhGia;} public void setNgayDanhGia(LocalDateTime v){ngayDanhGia=v;}
}
