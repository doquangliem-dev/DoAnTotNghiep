package com.huit.kiemdinh.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Document(collection="baoCaoPhan")
public class BaoCaoPhan {
    @Id private String id;
    private String maBaoCaoTuDanhGia;
    private String maKeHoach;
    private Integer soPhan;
    private String tenPhan;
    private String maPhanCong;
    private String maNguoiPhuTrach;
    private Map<String,Object> noiDung;
    private List<String> danhSachMinhChungCapTruong = new ArrayList<>();
    private List<String> danhSachMinhChungCapKhoa = new ArrayList<>();
    private List<String> danhSachMinhChungTieuChi = new ArrayList<>();
    private String trangThai;
    private Integer mucDoDuyet;
    private String capDuyetHienTai;
    private List<Map<String,Object>> feedback = new ArrayList<>();
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
    public BaoCaoPhan(){}
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getMaBaoCaoTuDanhGia(){return maBaoCaoTuDanhGia;} public void setMaBaoCaoTuDanhGia(String v){maBaoCaoTuDanhGia=v;}
    public String getMaKeHoach(){return maKeHoach;} public void setMaKeHoach(String v){maKeHoach=v;}
    public Integer getSoPhan(){return soPhan;} public void setSoPhan(Integer v){soPhan=v;}
    public String getTenPhan(){return tenPhan;} public void setTenPhan(String v){tenPhan=v;}
    public String getMaPhanCong(){return maPhanCong;} public void setMaPhanCong(String v){maPhanCong=v;}
    public String getMaNguoiPhuTrach(){return maNguoiPhuTrach;} public void setMaNguoiPhuTrach(String v){maNguoiPhuTrach=v;}
    public Map<String,Object> getNoiDung(){return noiDung;} public void setNoiDung(Map<String,Object> v){noiDung=v;}
    public List<String> getDanhSachMinhChungCapTruong(){return danhSachMinhChungCapTruong;} public void setDanhSachMinhChungCapTruong(List<String> v){danhSachMinhChungCapTruong=v;}
    public List<String> getDanhSachMinhChungCapKhoa(){return danhSachMinhChungCapKhoa;} public void setDanhSachMinhChungCapKhoa(List<String> v){danhSachMinhChungCapKhoa=v;}
    public List<String> getDanhSachMinhChungTieuChi(){return danhSachMinhChungTieuChi;} public void setDanhSachMinhChungTieuChi(List<String> v){danhSachMinhChungTieuChi=v;}
    public String getTrangThai(){return trangThai;} public void setTrangThai(String v){trangThai=v;}
    public Integer getMucDoDuyet(){return mucDoDuyet;} public void setMucDoDuyet(Integer v){mucDoDuyet=v;}
    public String getCapDuyetHienTai(){return capDuyetHienTai;} public void setCapDuyetHienTai(String v){capDuyetHienTai=v;}
    public List<Map<String,Object>> getFeedback(){return feedback;} public void setFeedback(List<Map<String,Object>> v){feedback=v;}
    public LocalDateTime getNgayTao(){return ngayTao;} public void setNgayTao(LocalDateTime v){ngayTao=v;}
    public LocalDateTime getNgayCapNhat(){return ngayCapNhat;} public void setNgayCapNhat(LocalDateTime v){ngayCapNhat=v;}
}
