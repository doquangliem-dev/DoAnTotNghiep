package com.huit.kiemdinh.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="tienDo")
public class TienDo {
    @Id private String id;
    private String maKeHoach;
    private String maPhanCong;
    private Integer phanTramHoanThanh;
    private String trangThai;
    public TienDo(){}
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getMaKeHoach(){return maKeHoach;} public void setMaKeHoach(String v){maKeHoach=v;}
    public String getMaPhanCong(){return maPhanCong;} public void setMaPhanCong(String v){maPhanCong=v;}
    public Integer getPhanTramHoanThanh(){return phanTramHoanThanh;} public void setPhanTramHoanThanh(Integer v){phanTramHoanThanh=v;}
    public String getTrangThai(){return trangThai;} public void setTrangThai(String v){trangThai=v;}
}
