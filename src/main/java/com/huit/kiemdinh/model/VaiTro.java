package com.huit.kiemdinh.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="vaiTro")
public class VaiTro {
    @Id private String id;
    private String tenVaiTro;
    private String moTa;
    public VaiTro(){}
    public String getId(){return id;} public void setId(String v){id=v;}
    public String getTenVaiTro(){return tenVaiTro;} public void setTenVaiTro(String v){tenVaiTro=v;}
    public String getMoTa(){return moTa;} public void setMoTa(String v){moTa=v;}
}
