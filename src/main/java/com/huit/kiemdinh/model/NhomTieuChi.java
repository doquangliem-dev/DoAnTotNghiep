package com.huit.kiemdinh.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Document(collection="nhomTieuChi")
public class NhomTieuChi {
    @Id private String id;
    private String tenNhom;
    // Các trường cũ được giữ để tương thích dữ liệu đã có. Nhóm không gắn trực tiếp tiêu chuẩn/tiêu chí.
    private String maTieuChuan;
    private String maTruongNhom;
    private String maThuKyNhom;
    private List<String> thanhVien = new ArrayList<>();
    private List<ThanhVienNhom> thanhVienChiTiet = new ArrayList<>();
    private List<String> tieuChi = new ArrayList<>();
    private String trangThai;
    public NhomTieuChi(){}
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getTenNhom(){return tenNhom;} public void setTenNhom(String v){tenNhom=v;}
    public String getMaTieuChuan(){return maTieuChuan;} public void setMaTieuChuan(String v){maTieuChuan=v;}
    public String getMaTruongNhom(){return maTruongNhom;} public void setMaTruongNhom(String v){maTruongNhom=v;}
    public String getMaThuKyNhom(){return maThuKyNhom;} public void setMaThuKyNhom(String v){maThuKyNhom=v;}
    public List<String> getThanhVien(){return thanhVien;} public void setThanhVien(List<String> v){thanhVien=v;}
    public List<ThanhVienNhom> getThanhVienChiTiet(){return thanhVienChiTiet;} public void setThanhVienChiTiet(List<ThanhVienNhom> v){thanhVienChiTiet=v;}
    public List<String> getTieuChi(){return tieuChi;} public void setTieuChi(List<String> v){tieuChi=v;}
    public String getTrangThai(){return trangThai;} public void setTrangThai(String v){trangThai=v;}
}
