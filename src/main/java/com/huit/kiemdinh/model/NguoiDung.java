package com.huit.kiemdinh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Document(collection="nguoiDung")
public class NguoiDung {
    @Id private String id;
    private String hoTen;
    private String email;

    // Chỉ nhận mật khẩu từ request, không trả hash mật khẩu về trình duyệt/API.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String matKhau;

    private String soDienThoai;
    private String khoa;
    private String trinhDo;
    private String trangThai;
    private List<PhanQuyen> phanQuyen = new ArrayList<>();

    public NguoiDung(){}
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getHoTen(){return hoTen;} public void setHoTen(String v){hoTen=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getMatKhau(){return matKhau;} public void setMatKhau(String v){matKhau=v;}
    public String getSoDienThoai(){return soDienThoai;} public void setSoDienThoai(String v){soDienThoai=v;}
    public String getKhoa(){return khoa;} public void setKhoa(String v){khoa=v;}
    public String getTrinhDo(){return trinhDo;} public void setTrinhDo(String v){trinhDo=v;}
    public String getTrangThai(){return trangThai;} public void setTrangThai(String v){trangThai=v;}
    public List<PhanQuyen> getPhanQuyen(){return phanQuyen;} public void setPhanQuyen(List<PhanQuyen> v){phanQuyen=v;}
}
