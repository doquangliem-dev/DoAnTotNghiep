package com.huit.kiemdinh.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Document(collection="tieuChi")
public class TieuChi {
    @Id private String id;              // VD H1.01.01
    private String maTieuChuan;         // VD H1
    private Integer soTieuChi;          // VD 1 cho tiêu chí 1.1
    private String tenTieuChi;
    private String moTa;
    private String tieuChiDieuKien = "Không"; // Có / Không
    private Integer thuTu;
    private List<ChiBao> chiBao = new ArrayList<>();
    private List<Map<String,Object>> minhChungGoiY = new ArrayList<>();
    private String maNguoiTao;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;

    public TieuChi(){}
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getMaTieuChuan(){return maTieuChuan;} public void setMaTieuChuan(String v){maTieuChuan=v;}
    public Integer getSoTieuChi(){return soTieuChi;} public void setSoTieuChi(Integer v){soTieuChi=v;}
    public String getTenTieuChi(){return tenTieuChi;} public void setTenTieuChi(String v){tenTieuChi=v;}
    public String getMoTa(){return moTa;} public void setMoTa(String v){moTa=v;}
    public String getTieuChiDieuKien(){return tieuChiDieuKien;} public void setTieuChiDieuKien(String v){tieuChiDieuKien=v;}
    public Integer getThuTu(){return thuTu;} public void setThuTu(Integer v){thuTu=v;}
    public List<ChiBao> getChiBao(){return chiBao;} public void setChiBao(List<ChiBao> v){chiBao=v;}
    public List<Map<String,Object>> getMinhChungGoiY(){return minhChungGoiY;} public void setMinhChungGoiY(List<Map<String,Object>> v){minhChungGoiY=v;}
    public String getMaNguoiTao(){return maNguoiTao;} public void setMaNguoiTao(String v){maNguoiTao=v;}
    public LocalDateTime getNgayTao(){return ngayTao;} public void setNgayTao(LocalDateTime v){ngayTao=v;}
    public LocalDateTime getNgayCapNhat(){return ngayCapNhat;} public void setNgayCapNhat(LocalDateTime v){ngayCapNhat=v;}
}
