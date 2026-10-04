package com.huit.kiemdinh.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Document(collection="baoCaoTieuChi")
public class BaoCaoTieuChi {
    @Id private String id;              // BC001
    private String maPhanCong;
    private String maKeHoach;
    private String maTieuChuan;
    private String maTieuChi;
    private String nhanDeTieuChi;

    // Cấu trúc chuẩn báo cáo tự đánh giá
    // 1. Mô tả hiện trạng
    private String moTaHienTrang;
    // Field cũ chỉ giữ để đọc/migrate dữ liệu đã lưu trước đây.
    private String noiDungBaoCao;
    // 2. Điểm mạnh
    private String diemManh;
    // 3. Điểm tồn tại
    private String diemTonTai;
    // Field cũ chỉ giữ để đọc/migrate dữ liệu đã lưu trước đây.
    private String diemYeu;
    // 4. Kế hoạch hành động
    private String keHoachHanhDong;

    private String trangThai;
    private String maNguoiViet;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
    private List<Map<String,Object>> duyetBaoCao = new ArrayList<>();

    public BaoCaoTieuChi(){}
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getMaPhanCong(){return maPhanCong;} public void setMaPhanCong(String v){maPhanCong=v;}
    public String getMaKeHoach(){return maKeHoach;} public void setMaKeHoach(String v){maKeHoach=v;}
    public String getMaTieuChuan(){return maTieuChuan;} public void setMaTieuChuan(String v){maTieuChuan=v;}
    public String getMaTieuChi(){return maTieuChi;} public void setMaTieuChi(String v){maTieuChi=v;}
    public String getNhanDeTieuChi(){return nhanDeTieuChi;} public void setNhanDeTieuChi(String v){nhanDeTieuChi=v;}
    public String getMoTaHienTrang(){return moTaHienTrang;} public void setMoTaHienTrang(String v){moTaHienTrang=v;}
    @JsonIgnore public String getNoiDungBaoCao(){return noiDungBaoCao;} public void setNoiDungBaoCao(String v){noiDungBaoCao=v;}
    public String getDiemManh(){return diemManh;} public void setDiemManh(String v){diemManh=v;}
    public String getDiemTonTai(){return diemTonTai;} public void setDiemTonTai(String v){diemTonTai=v;}
    @JsonIgnore public String getDiemYeu(){return diemYeu;} public void setDiemYeu(String v){diemYeu=v;}
    public String getKeHoachHanhDong(){return keHoachHanhDong;} public void setKeHoachHanhDong(String v){keHoachHanhDong=v;}
    public String getTrangThai(){return trangThai;} public void setTrangThai(String v){trangThai=v;}
    public String getMaNguoiViet(){return maNguoiViet;} public void setMaNguoiViet(String v){maNguoiViet=v;}
    public LocalDateTime getNgayTao(){return ngayTao;} public void setNgayTao(LocalDateTime v){ngayTao=v;}
    public LocalDateTime getNgayCapNhat(){return ngayCapNhat;} public void setNgayCapNhat(LocalDateTime v){ngayCapNhat=v;}
    public List<Map<String,Object>> getDuyetBaoCao(){return duyetBaoCao;} public void setDuyetBaoCao(List<Map<String,Object>> v){duyetBaoCao=v;}
}
