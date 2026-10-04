package com.huit.kiemdinh.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PhienBanCTDT {
    private String maPhienBan;
    private String namHoc;
    private Integer soPhienBan;
    private String tenPhienBan;
    private String noiDungCapNhat;
    private LocalDate ngayBanHanh;
    private String trangThai;
    private String maNguoiCapNhat;
    private String fileDinhKem;
    private String tenFileGoc;
    private List<ChuanDauRaPLO> chuanDauRa = new ArrayList<>();

    public PhienBanCTDT() {}
    public String getMaPhienBan(){return maPhienBan;} public void setMaPhienBan(String v){maPhienBan=v;}
    public String getNamHoc(){return namHoc;} public void setNamHoc(String v){namHoc=v;}
    public Integer getSoPhienBan(){return soPhienBan;} public void setSoPhienBan(Integer v){soPhienBan=v;}
    public String getTenPhienBan(){return tenPhienBan;} public void setTenPhienBan(String v){tenPhienBan=v;}
    public String getNoiDungCapNhat(){return noiDungCapNhat;} public void setNoiDungCapNhat(String v){noiDungCapNhat=v;}
    public LocalDate getNgayBanHanh(){return ngayBanHanh;} public void setNgayBanHanh(LocalDate v){ngayBanHanh=v;}
    public String getTrangThai(){return trangThai;} public void setTrangThai(String v){trangThai=v;}
    public String getMaNguoiCapNhat(){return maNguoiCapNhat;} public void setMaNguoiCapNhat(String v){maNguoiCapNhat=v;}
    public String getFileDinhKem(){return fileDinhKem;} public void setFileDinhKem(String v){fileDinhKem=v;}
    public String getTenFileGoc(){return tenFileGoc;} public void setTenFileGoc(String v){tenFileGoc=v;}
    public List<ChuanDauRaPLO> getChuanDauRa(){return chuanDauRa;} public void setChuanDauRa(List<ChuanDauRaPLO> v){chuanDauRa=v;}
}
