package com.huit.kiemdinh.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection="phanCongBaoCao")
public class PhanCongBaoCao {
    @Id private String id;
    private String maBaoCaoTuDanhGia;
    private String maKeHoach;
    private Integer soPhan;
    private String tenCongViec;
    private String maNguoiDung;
    private List<String> maNhanSuHoTroList = new ArrayList<>();
    private String noiDungCongViec;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThucDuKien;
    private String trangThai;
    private Integer phanTramHoanThanh;
    private String maNguoiTao;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
    public PhanCongBaoCao(){}
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getMaBaoCaoTuDanhGia(){return maBaoCaoTuDanhGia;} public void setMaBaoCaoTuDanhGia(String v){maBaoCaoTuDanhGia=v;}
    public String getMaKeHoach(){return maKeHoach;} public void setMaKeHoach(String v){maKeHoach=v;}
    public Integer getSoPhan(){return soPhan;} public void setSoPhan(Integer v){soPhan=v;}
    public String getTenCongViec(){return tenCongViec;} public void setTenCongViec(String v){tenCongViec=v;}
    public String getMaNguoiDung(){return maNguoiDung;} public void setMaNguoiDung(String v){maNguoiDung=v;}
    public List<String> getMaNhanSuHoTroList(){return maNhanSuHoTroList;} public void setMaNhanSuHoTroList(List<String> v){maNhanSuHoTroList=v;}
    public String getNoiDungCongViec(){return noiDungCongViec;} public void setNoiDungCongViec(String v){noiDungCongViec=v;}
    public LocalDate getNgayBatDau(){return ngayBatDau;} public void setNgayBatDau(LocalDate v){ngayBatDau=v;}
    public LocalDate getNgayKetThucDuKien(){return ngayKetThucDuKien;} public void setNgayKetThucDuKien(LocalDate v){ngayKetThucDuKien=v;}
    public String getTrangThai(){return trangThai;} public void setTrangThai(String v){trangThai=v;}
    public Integer getPhanTramHoanThanh(){return phanTramHoanThanh;} public void setPhanTramHoanThanh(Integer v){phanTramHoanThanh=v;}
    public String getMaNguoiTao(){return maNguoiTao;} public void setMaNguoiTao(String v){maNguoiTao=v;}
    public LocalDateTime getNgayTao(){return ngayTao;} public void setNgayTao(LocalDateTime v){ngayTao=v;}
    public LocalDateTime getNgayCapNhat(){return ngayCapNhat;} public void setNgayCapNhat(LocalDateTime v){ngayCapNhat=v;}
}
