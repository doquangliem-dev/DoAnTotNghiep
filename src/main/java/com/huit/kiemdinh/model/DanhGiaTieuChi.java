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
    private String tenNguoiDanhGia;
    private String vaiTroDanhGia;
    private String donViDanhGia;
    private Integer diemDanhGia;
    // thang 1-5 để tính mức đánh giá tổng quát
    private LocalDateTime ngayDanhGia;
    public DanhGiaTieuChi() {
    }
    public String getId() {
        return id;
    }
    public void setId(String v) {
        id=v;
    }
    public String getMaKeHoach() {
        return maKeHoach;
    }
    public void setMaKeHoach(String v) {
        maKeHoach=v;
    }
    public String getMaTieuChi() {
        return maTieuChi;
    }
    public void setMaTieuChi(String v) {
        maTieuChi=v;
    }
    public Integer getLanDanhGia() {
        return lanDanhGia;
    }
    public void setLanDanhGia(Integer v) {
        lanDanhGia=v;
    }
    public String getKetQua() {
        return ketQua;
    }
    public void setKetQua(String v) {
        ketQua=v;
    }
    public String getNhanXet() {
        return nhanXet;
    }
    public void setNhanXet(String v) {
        nhanXet=v;
    }
    public String getMaNguoiDanhGia() {
        return maNguoiDanhGia;
    }
    public void setMaNguoiDanhGia(String v) {
        maNguoiDanhGia=v;
    }
    public String getTenNguoiDanhGia() {
        return tenNguoiDanhGia;
    }
    public void setTenNguoiDanhGia(String v) {
        tenNguoiDanhGia=v;
    }
    public String getVaiTroDanhGia() {
        return vaiTroDanhGia;
    }
    public void setVaiTroDanhGia(String v) {
        vaiTroDanhGia=v;
    }
    public String getDonViDanhGia() {
        return donViDanhGia;
    }
    public void setDonViDanhGia(String v) {
        donViDanhGia=v;
    }
    public Integer getDiemDanhGia() {
        return diemDanhGia;
    }
    public void setDiemDanhGia(Integer v) {
        diemDanhGia=v;
    }
    public LocalDateTime getNgayDanhGia() {
        return ngayDanhGia;
    }
    public void setNgayDanhGia(LocalDateTime v) {
        ngayDanhGia=v;
    }
}
