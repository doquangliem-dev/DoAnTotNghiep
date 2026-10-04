package com.huit.kiemdinh.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection="minhChungChung")
public class MinhChungChung {
    @Id private String id;                 // MCC001
    private String maMinhChung;            // MCC001
    private String tenMinhChung;
    private String capQuanLy;              // Cấp Trường / Cấp Khoa
    private String soHieuVanBan;
    private LocalDateTime ngayBanHanh;
    private String donViBanHanh;
    private String moTa;
    private String duongDanFile;
    private String tenFile;
    private String loaiFile;
    private String maNguoiTao;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;

    public MinhChungChung(){}
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getMaMinhChung(){return maMinhChung;} public void setMaMinhChung(String v){maMinhChung=v;}
    public String getTenMinhChung(){return tenMinhChung;} public void setTenMinhChung(String v){tenMinhChung=v;}
    public String getCapQuanLy(){return capQuanLy;} public void setCapQuanLy(String v){capQuanLy=v;}
    public String getSoHieuVanBan(){return soHieuVanBan;} public void setSoHieuVanBan(String v){soHieuVanBan=v;}
    public LocalDateTime getNgayBanHanh(){return ngayBanHanh;} public void setNgayBanHanh(LocalDateTime v){ngayBanHanh=v;}
    public String getDonViBanHanh(){return donViBanHanh;} public void setDonViBanHanh(String v){donViBanHanh=v;}
    public String getMoTa(){return moTa;} public void setMoTa(String v){moTa=v;}
    public String getDuongDanFile(){return duongDanFile;} public void setDuongDanFile(String v){duongDanFile=v;}
    public String getTenFile(){return tenFile;} public void setTenFile(String v){tenFile=v;}
    public String getLoaiFile(){return loaiFile;} public void setLoaiFile(String v){loaiFile=v;}
    public String getMaNguoiTao(){return maNguoiTao;} public void setMaNguoiTao(String v){maNguoiTao=v;}
    public LocalDateTime getNgayTao(){return ngayTao;} public void setNgayTao(LocalDateTime v){ngayTao=v;}
    public LocalDateTime getNgayCapNhat(){return ngayCapNhat;} public void setNgayCapNhat(LocalDateTime v){ngayCapNhat=v;}
}
