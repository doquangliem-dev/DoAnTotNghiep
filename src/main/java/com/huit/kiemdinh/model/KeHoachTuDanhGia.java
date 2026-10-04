package com.huit.kiemdinh.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Document(collection="keHoachTuDanhGia")
public class KeHoachTuDanhGia {
    @Id private String id;
    private String tenKeHoach;
    private String maCTDT;
    private String maBoTieuChuan;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private String trangThai;
    private Map<String,Object> thongKeDanhGiaLan1;
    private List<Map<String,Object>> quyTrinhDuyet = new ArrayList<>();
    public KeHoachTuDanhGia(){}
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getTenKeHoach(){return tenKeHoach;} public void setTenKeHoach(String v){tenKeHoach=v;}
    public String getMaCTDT(){return maCTDT;} public void setMaCTDT(String v){maCTDT=v;}
    public String getMaBoTieuChuan(){return maBoTieuChuan;} public void setMaBoTieuChuan(String v){maBoTieuChuan=v;}
    public LocalDate getNgayBatDau(){return ngayBatDau;} public void setNgayBatDau(LocalDate v){ngayBatDau=v;}
    public LocalDate getNgayKetThuc(){return ngayKetThuc;} public void setNgayKetThuc(LocalDate v){ngayKetThuc=v;}
    public String getTrangThai(){return trangThai;} public void setTrangThai(String v){trangThai=v;}
    public Map<String,Object> getThongKeDanhGiaLan1(){return thongKeDanhGiaLan1;} public void setThongKeDanhGiaLan1(Map<String,Object> v){thongKeDanhGiaLan1=v;}
    public List<Map<String,Object>> getQuyTrinhDuyet(){return quyTrinhDuyet;} public void setQuyTrinhDuyet(List<Map<String,Object>> v){quyTrinhDuyet=v;}
}
