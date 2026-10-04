package com.huit.kiemdinh.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection="minhChung")
public class MinhChung {
    @Id private String id;              // MC001
    private String loaiMinhChung;       // TIEU_CHI / CHUNG
    private String capMinhChung;        // Cấp trường / Cấp khoa (chỉ CHUNG)
    private String maMinhChung;         // H1.01.01.01 (chỉ TIEU_CHI)
    private String maTieuChuan;
    private String maTieuChi;
    private String maPhanCong;
    private String maKeHoach;          // Đợt/kế hoạch tự đánh giá liên quan
    private String tenMinhChung;
    private String moTa;
    private String duongDanFile;
    private String loaiFile;
    private Long kichThuoc;
    private LocalDateTime ngayUpload;
    private String trangThai;           // Chỉ áp dụng TIEU_CHI
    private String maNguoiUpload;

    public MinhChung(){}
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getLoaiMinhChung(){return loaiMinhChung;} public void setLoaiMinhChung(String v){loaiMinhChung=v;}
    public String getCapMinhChung(){return capMinhChung;} public void setCapMinhChung(String v){capMinhChung=v;}
    public String getMaMinhChung(){return maMinhChung;} public void setMaMinhChung(String v){maMinhChung=v;}
    public String getMaTieuChuan(){return maTieuChuan;} public void setMaTieuChuan(String v){maTieuChuan=v;}
    public String getMaTieuChi(){return maTieuChi;} public void setMaTieuChi(String v){maTieuChi=v;}
    public String getMaPhanCong(){return maPhanCong;} public void setMaPhanCong(String v){maPhanCong=v;}
    public String getMaKeHoach(){return maKeHoach;} public void setMaKeHoach(String v){maKeHoach=v;}
    public String getTenMinhChung(){return tenMinhChung;} public void setTenMinhChung(String v){tenMinhChung=v;}
    public String getMoTa(){return moTa;} public void setMoTa(String v){moTa=v;}
    public String getDuongDanFile(){return duongDanFile;} public void setDuongDanFile(String v){duongDanFile=v;}
    public String getLoaiFile(){return loaiFile;} public void setLoaiFile(String v){loaiFile=v;}
    public Long getKichThuoc(){return kichThuoc;} public void setKichThuoc(Long v){kichThuoc=v;}
    public LocalDateTime getNgayUpload(){return ngayUpload;} public void setNgayUpload(LocalDateTime v){ngayUpload=v;}
    public String getTrangThai(){return trangThai;} public void setTrangThai(String v){trangThai=v;}
    public String getMaNguoiUpload(){return maNguoiUpload;} public void setMaNguoiUpload(String v){maNguoiUpload=v;}
}
