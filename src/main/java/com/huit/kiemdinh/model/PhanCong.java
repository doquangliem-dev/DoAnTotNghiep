package com.huit.kiemdinh.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.time.LocalDate;

@Document(collection="phanCong")
public class PhanCong {
    @Id private String id;
    private String maKeHoach;
    private String maNguoiDung;
    private String maTieuChuan;
    private String maTieuChi; // dùng cho bước Trưởng nhóm giao tiêu chí cho thành viên (nếu có)
    private String maNhomTieuChi;
    private String vaiTroTrongNhom;
    private String maNhanSuHoTro; // dữ liệu cũ: một nhân sự hỗ trợ
    private List<String> maNhanSuHoTroList; // dữ liệu mới: nhiều nhân sự hỗ trợ
    private String maDotPhanCong; // gom nhiều tiêu chí phân trong cùng một lần thành một dòng UI
    private String noiDungCongViec;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThucDuKien;
    private String trangThai;
    private String maNguoiTao;

    public PhanCong(){}
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getMaKeHoach(){return maKeHoach;} public void setMaKeHoach(String v){maKeHoach=v;}
    public String getMaNguoiDung(){return maNguoiDung;} public void setMaNguoiDung(String v){maNguoiDung=v;}
    public String getMaTieuChuan(){return maTieuChuan;} public void setMaTieuChuan(String v){maTieuChuan=v;}
    public String getMaTieuChi(){return maTieuChi;} public void setMaTieuChi(String v){maTieuChi=v;}
    public String getMaNhomTieuChi(){return maNhomTieuChi;} public void setMaNhomTieuChi(String v){maNhomTieuChi=v;}
    public String getVaiTroTrongNhom(){return vaiTroTrongNhom;} public void setVaiTroTrongNhom(String v){vaiTroTrongNhom=v;}
    public String getMaNhanSuHoTro(){return maNhanSuHoTro;} public void setMaNhanSuHoTro(String v){maNhanSuHoTro=v;}
    public List<String> getMaNhanSuHoTroList(){return maNhanSuHoTroList;} public void setMaNhanSuHoTroList(List<String> v){maNhanSuHoTroList=v;}
    public String getMaDotPhanCong(){return maDotPhanCong;} public void setMaDotPhanCong(String v){maDotPhanCong=v;}
    public String getNoiDungCongViec(){return noiDungCongViec;} public void setNoiDungCongViec(String v){noiDungCongViec=v;}
    public LocalDate getNgayBatDau(){return ngayBatDau;} public void setNgayBatDau(LocalDate v){ngayBatDau=v;}
    public LocalDate getNgayKetThucDuKien(){return ngayKetThucDuKien;} public void setNgayKetThucDuKien(LocalDate v){ngayKetThucDuKien=v;}
    public String getTrangThai(){return trangThai;} public void setTrangThai(String v){trangThai=v;}
    public String getMaNguoiTao(){return maNguoiTao;} public void setMaNguoiTao(String v){maNguoiTao=v;}
}
