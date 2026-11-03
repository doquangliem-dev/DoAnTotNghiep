package com.huit.kiemdinh.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection="lichSuThaoTac")
public class LichSuThaoTac {
    @Id private String id;
    private String maNguoiDung;
    private String hanhDong;
    private String doiTuongTacDong;
    private String maBanGhi;
    private String noiDung;
    private LocalDateTime thoiGian;

    public LichSuThaoTac(){}
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getMaNguoiDung(){return maNguoiDung;} public void setMaNguoiDung(String v){maNguoiDung=v;}
    public String getHanhDong(){return hanhDong;} public void setHanhDong(String v){hanhDong=v;}
    public String getDoiTuongTacDong(){return doiTuongTacDong;} public void setDoiTuongTacDong(String v){doiTuongTacDong=v;}
    public String getMaBanGhi(){return maBanGhi;} public void setMaBanGhi(String v){maBanGhi=v;}
    public String getNoiDung(){return noiDung;} public void setNoiDung(String v){noiDung=v;}
    public LocalDateTime getThoiGian(){return thoiGian;} public void setThoiGian(LocalDateTime v){thoiGian=v;}
}
