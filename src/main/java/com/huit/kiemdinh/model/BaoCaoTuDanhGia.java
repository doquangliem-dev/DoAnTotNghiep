package com.huit.kiemdinh.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Document(collection="baoCaoTuDanhGia")
public class BaoCaoTuDanhGia {
    @Id private String id;
    private String maKeHoach;
    private String maCTDT;
    private String maPhienBanCTDT;
    private String tenBaoCao;
    private Integer phienBanBaoCao;
    private List<Map<String,Object>> cauTruc = new ArrayList<>();
    private Map<String,Object> nguonPhan2;
    private String trangThai;
    private Integer mucDoHoanThanh;
    private String maNguoiTao;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
    public BaoCaoTuDanhGia(){}
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getMaKeHoach(){return maKeHoach;} public void setMaKeHoach(String v){maKeHoach=v;}
    public String getMaCTDT(){return maCTDT;} public void setMaCTDT(String v){maCTDT=v;}
    public String getMaPhienBanCTDT(){return maPhienBanCTDT;} public void setMaPhienBanCTDT(String v){maPhienBanCTDT=v;}
    public String getTenBaoCao(){return tenBaoCao;} public void setTenBaoCao(String v){tenBaoCao=v;}
    public Integer getPhienBanBaoCao(){return phienBanBaoCao;} public void setPhienBanBaoCao(Integer v){phienBanBaoCao=v;}
    public List<Map<String,Object>> getCauTruc(){return cauTruc;} public void setCauTruc(List<Map<String,Object>> v){cauTruc=v;}
    public Map<String,Object> getNguonPhan2(){return nguonPhan2;} public void setNguonPhan2(Map<String,Object> v){nguonPhan2=v;}
    public String getTrangThai(){return trangThai;} public void setTrangThai(String v){trangThai=v;}
    public Integer getMucDoHoanThanh(){return mucDoHoanThanh;} public void setMucDoHoanThanh(Integer v){mucDoHoanThanh=v;}
    public String getMaNguoiTao(){return maNguoiTao;} public void setMaNguoiTao(String v){maNguoiTao=v;}
    public LocalDateTime getNgayTao(){return ngayTao;} public void setNgayTao(LocalDateTime v){ngayTao=v;}
    public LocalDateTime getNgayCapNhat(){return ngayCapNhat;} public void setNgayCapNhat(LocalDateTime v){ngayCapNhat=v;}
}
