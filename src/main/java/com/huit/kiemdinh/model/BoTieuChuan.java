package com.huit.kiemdinh.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection="boTieuChuan")
public class BoTieuChuan {
    @Id private String id;
    private String tenBoTieuChuan;
    private String maSoVanBan;
    private LocalDate ngayBanHanh;
    private Integer namApDung;
    private String trangThai;
    private String moTa;

    public BoTieuChuan(){}
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getTenBoTieuChuan(){return tenBoTieuChuan;} public void setTenBoTieuChuan(String v){tenBoTieuChuan=v;}
    public String getMaSoVanBan(){return maSoVanBan;} public void setMaSoVanBan(String v){maSoVanBan=v;}
    public LocalDate getNgayBanHanh(){return ngayBanHanh;} public void setNgayBanHanh(LocalDate v){ngayBanHanh=v;}
    public Integer getNamApDung(){return namApDung;} public void setNamApDung(Integer v){namApDung=v;}
    public String getTrangThai(){return trangThai;} public void setTrangThai(String v){trangThai=v;}
    public String getMoTa(){return moTa;} public void setMoTa(String v){moTa=v;}
}
