package com.huit.kiemdinh.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Document(collection="thongBao")
public class ThongBao {
    @Id private String id;
    private String tieuDe;
    private String noiDung;
    private String loaiThongBao;
    private LocalDateTime ngayTao;
    private List<Map<String,Object>> nguoiNhan = new ArrayList<>();
    public ThongBao(){}
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getTieuDe(){return tieuDe;} public void setTieuDe(String v){tieuDe=v;}
    public String getNoiDung(){return noiDung;} public void setNoiDung(String v){noiDung=v;}
    public String getLoaiThongBao(){return loaiThongBao;} public void setLoaiThongBao(String v){loaiThongBao=v;}
    public LocalDateTime getNgayTao(){return ngayTao;} public void setNgayTao(LocalDateTime v){ngayTao=v;}
    public List<Map<String,Object>> getNguoiNhan(){return nguoiNhan;} public void setNguoiNhan(List<Map<String,Object>> v){nguoiNhan=v;}
}
