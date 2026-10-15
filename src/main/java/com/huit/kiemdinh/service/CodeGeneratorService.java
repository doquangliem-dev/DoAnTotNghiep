package com.huit.kiemdinh.service;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CodeGeneratorService {
    private final MongoTemplate mongoTemplate;
    public CodeGeneratorService(MongoTemplate mongoTemplate){ this.mongoTemplate = mongoTemplate; }

    private int maxSuffix(String collection, String field, Pattern pattern){
        int max=0;
        for(Document d: mongoTemplate.getCollection(collection).find()){
            Object raw=d.get(field); if(raw==null) continue;
            Matcher m=pattern.matcher(String.valueOf(raw));
            if(m.matches()) try{ max=Math.max(max,Integer.parseInt(m.group(1))); }catch(Exception ignored){}
        }
        return max;
    }
    public String nextCTDT(){return String.format("CTDT%03d",maxSuffix("chuongTrinhDaoTao","_id",Pattern.compile("CTDT(\\d+)"))+1);}
    public String nextNguoiDung(){return String.format("ND%03d",maxSuffix("nguoiDung","_id",Pattern.compile("ND(\\d+)"))+1);}
    public String nextPhanCong(){return String.format("PC%03d",maxSuffix("phanCong","_id",Pattern.compile("PC(\\d+)"))+1);}
    public String nextNhomTieuChi(){return String.format("NT%03d",maxSuffix("nhomTieuChi","_id",Pattern.compile("NT(\\d+)"))+1);}
    public String nextMinhChungId(){return String.format("MC%03d",maxSuffix("minhChung","_id",Pattern.compile("MC(\\d+)"))+1);}
    public String nextMinhChungChung(){return String.format("MCC%03d",maxSuffix("minhChungChung","_id",Pattern.compile("MCC(\\d+)"))+1);}
    public String nextBaoCao(){return String.format("BC%03d",maxSuffix("baoCaoTieuChi","_id",Pattern.compile("BC(\\d+)"))+1);}
    public String nextDanhGiaMinhChung(){return String.format("DGMC%03d",maxSuffix("danhGiaMinhChung","_id",Pattern.compile("DGMC(\\d+)"))+1);}
    public String nextDanhGiaTieuChi(){return String.format("DGTC%03d",maxSuffix("danhGiaTieuChi","_id",Pattern.compile("DGTC(\\d+)"))+1);}
    public String nextKeHoach(){return String.format("KH%03d",maxSuffix("keHoachTuDanhGia","_id",Pattern.compile("KH(\\d+)"))+1);}

    public String nextBaoCaoNhom(){return String.format("BCN%03d",maxSuffix("baoCaoNhom","_id",Pattern.compile("BCN(\\d+)"))+1);}
    public String nextBaoCaoTuDanhGia(){return String.format("BCTDG%03d",maxSuffix("baoCaoTuDanhGia","_id",Pattern.compile("BCTDG(\\d+)"))+1);}
    public String nextPhanCongBaoCao(){return String.format("PCB%03d",maxSuffix("phanCongBaoCao","_id",Pattern.compile("PCB(\\d+)"))+1);}
    public String nextBaoCaoPhan(int soPhan){
        String preferred=String.format("BCP%03d",soPhan);
        if(mongoTemplate.getCollection("baoCaoPhan").find(new Document("_id",preferred)).first()==null) return preferred;
        return String.format("BCP%03d",maxSuffix("baoCaoPhan","_id",Pattern.compile("BCP(\\d+)"))+1);
    }

    /**
     * Bộ tiêu chuẩn không còn dùng BTC001. Mã chính là mã tên/bộ kiểm định,
     * ví dụ MOET, AUN-QA, ABET, FIBAA, ASIIN.
     */
    public String codeBoTieuChuan(String requestedId, String tenBoTieuChuan){
        if(requestedId!=null && !requestedId.isBlank()) return requestedId.trim().toUpperCase(Locale.ROOT);
        String raw=tenBoTieuChuan==null?"":tenBoTieuChuan.trim();
        String n=Normalizer.normalize(raw,Normalizer.Form.NFD).replaceAll("\\p{M}","").toUpperCase(Locale.ROOT);
        if(n.contains("AUN") && n.contains("QA")) return "AUN-QA";
        if(n.contains("ABET")) return "ABET";
        if(n.contains("FIBAA")) return "FIBAA";
        if(n.contains("ASIIN")) return "ASIIN";
        if(n.contains("MOET") || n.contains("BO TIEU CHUAN MOET")) return "MOET";
        String code=n.replaceAll("[^A-Z0-9]+","-").replaceAll("(^-|-$)","");
        if(code.isBlank()) throw new IllegalArgumentException("Vui lòng chọn/nhập mã bộ tiêu chuẩn");
        return code.length()>24?code.substring(0,24):code;
    }

    // Góp ý tuần 7: mã H chỉ dùng cho minh chứng. Tiêu chuẩn/tiêu chí dùng TC.
    // Tiêu chuẩn: TC1..TC8
    public String nextTieuChuan(String ignored){
        int max=maxSuffix("tieuChuan","_id",Pattern.compile("TC(\\d+)"));
        if(max>=8) throw new IllegalStateException("Bộ tiêu chuẩn hiện hành chỉ có 8 tiêu chuẩn TC1-TC8");
        return "TC"+(max+1);
    }

    // TC2 -> TC2.1, TC2.2...
    public String nextTieuChi(String maTieuChuan){
        Matcher std=Pattern.compile("TC(\\d+)").matcher(maTieuChuan==null?"":maTieuChuan.trim());
        if(!std.matches()) throw new IllegalArgumentException("Mã tiêu chuẩn phải có dạng TC1..TC8");
        int n=Integer.parseInt(std.group(1));
        String prefix="TC"+n+".";
        int max=0;
        Pattern p=Pattern.compile(Pattern.quote(prefix)+"(\\d+)");
        for(Document d:mongoTemplate.getCollection("tieuChi").find()){
            Object raw=d.get("_id"); if(raw==null) continue;
            Matcher m=p.matcher(String.valueOf(raw)); if(m.matches()) max=Math.max(max,Integer.parseInt(m.group(1)));
        }
        return prefix+(max+1);
    }

    // Mã H chỉ dành cho minh chứng: TC2.3 -> H2.3.01, H2.3.02...
    public String nextMaMinhChung(String maTieuChi){
        Matcher c=Pattern.compile("TC(\\d+)\\.(\\d+)").matcher(maTieuChi==null?"":maTieuChi.trim());
        if(!c.matches()) throw new IllegalArgumentException("Mã tiêu chí phải có dạng TC1.1, TC1.2...");
        String prefix="H"+Integer.parseInt(c.group(1))+"."+Integer.parseInt(c.group(2))+".";
        int max=0; Pattern p=Pattern.compile(Pattern.quote(prefix)+"(\\d{2,})");
        for(Document d:mongoTemplate.getCollection("minhChung").find()){
            Object raw=d.get("maMinhChung"); if(raw==null) continue;
            Matcher m=p.matcher(String.valueOf(raw)); if(m.matches()) max=Math.max(max,Integer.parseInt(m.group(1)));
        }
        return String.format("%s%02d",prefix,max+1);
    }
}
