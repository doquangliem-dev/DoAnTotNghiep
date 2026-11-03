package com.huit.kiemdinh.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="tieuChuan")
public class TieuChuan {
    @Id private String id;              // TC1, TC2, ...
    private String maBoTieuChuan;
    private Integer soTieuChuan;
    private String tenTieuChuan;
    private String moTa;
    private Integer thuTu;
    private String trangThai;

    public TieuChuan(){}
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getMaBoTieuChuan(){return maBoTieuChuan;} public void setMaBoTieuChuan(String v){maBoTieuChuan=v;}
    public Integer getSoTieuChuan(){return soTieuChuan;} public void setSoTieuChuan(Integer v){soTieuChuan=v;}
    public String getTenTieuChuan(){return tenTieuChuan;} public void setTenTieuChuan(String v){tenTieuChuan=v;}
    public String getMoTa(){return moTa;} public void setMoTa(String v){moTa=v;}
    public Integer getThuTu(){return thuTu;} public void setThuTu(Integer v){thuTu=v;}
    public String getTrangThai(){return trangThai;} public void setTrangThai(String v){trangThai=v;}
}
