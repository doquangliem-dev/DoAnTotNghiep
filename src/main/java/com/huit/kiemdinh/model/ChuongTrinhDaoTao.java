package com.huit.kiemdinh.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection="chuongTrinhDaoTao")
public class ChuongTrinhDaoTao {
    @Id private String id;
    private String tenCTDT;
    private String maNganh;
    private String trinhDoDaoTao;
    private String loaiHinhDaoTao;
    private String thoiGianDaoTao;
    private String moTa;
    private String trangThai;
    private Integer namBatDau;
    private String maNguoiTao;
    private List<PhienBanCTDT> phienBan = new ArrayList<>();
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;

    public ChuongTrinhDaoTao(){}
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getTenCTDT(){return tenCTDT;} public void setTenCTDT(String v){tenCTDT=v;}
    public String getMaNganh(){return maNganh;} public void setMaNganh(String v){maNganh=v;}
    public String getTrinhDoDaoTao(){return trinhDoDaoTao;} public void setTrinhDoDaoTao(String v){trinhDoDaoTao=v;}
    public String getLoaiHinhDaoTao(){return loaiHinhDaoTao;} public void setLoaiHinhDaoTao(String v){loaiHinhDaoTao=v;}
    public String getThoiGianDaoTao(){return thoiGianDaoTao;} public void setThoiGianDaoTao(String v){thoiGianDaoTao=v;}
    public String getMoTa(){return moTa;} public void setMoTa(String v){moTa=v;}
    public String getTrangThai(){return trangThai;} public void setTrangThai(String v){trangThai=v;}
    public Integer getNamBatDau(){return namBatDau;} public void setNamBatDau(Integer v){namBatDau=v;}
    public String getMaNguoiTao(){return maNguoiTao;} public void setMaNguoiTao(String v){maNguoiTao=v;}
    public List<PhienBanCTDT> getPhienBan(){return phienBan;} public void setPhienBan(List<PhienBanCTDT> v){phienBan=v;}
    public LocalDateTime getNgayTao(){return ngayTao;} public void setNgayTao(LocalDateTime v){ngayTao=v;}
    public LocalDateTime getNgayCapNhat(){return ngayCapNhat;} public void setNgayCapNhat(LocalDateTime v){ngayCapNhat=v;}
}
