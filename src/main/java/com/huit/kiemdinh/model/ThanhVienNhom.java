package com.huit.kiemdinh.model;

public class ThanhVienNhom {
    private String maNguoiDung;
    private String chucVu;

    public ThanhVienNhom(){}
    public ThanhVienNhom(String maNguoiDung, String chucVu){
        this.maNguoiDung=maNguoiDung; this.chucVu=chucVu;
    }
    public String getMaNguoiDung(){return maNguoiDung;}
    public void setMaNguoiDung(String v){maNguoiDung=v;}
    public String getChucVu(){return chucVu;}
    public void setChucVu(String v){chucVu=v;}
}
