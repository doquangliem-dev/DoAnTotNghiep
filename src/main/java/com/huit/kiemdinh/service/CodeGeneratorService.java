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
    public String nextNhomTieuChi(){return String.format("NTC%03d",maxSuffix("nhomTieuChi","_id",Pattern.compile("NTC(\\d+)"))+1);}
    public String nextMinhChungId(){return String.format("MC%03d",maxSuffix("minhChung","_id",Pattern.compile("MC(\\d+)"))+1);}
    public String nextBaoCao(){return String.format("BC%03d",maxSuffix("baoCaoTieuChi","_id",Pattern.compile("BC(\\d+)"))+1);}
    public String nextDanhGiaMinhChung(){return String.format("DGMC%03d",maxSuffix("danhGiaMinhChung","_id",Pattern.compile("DGMC(\\d+)"))+1);}
    public String nextDanhGiaTieuChi(){return String.format("DGTC%03d",maxSuffix("danhGiaTieuChi","_id",Pattern.compile("DGTC(\\d+)"))+1);}
    public String nextKeHoach(){return String.format("KH%03d",maxSuffix("keHoachTuDanhGia","_id",Pattern.compile("KH(\\d+)"))+1);}

    /**
     * Bộ tiêu chuẩn không còn dùng BTC001. Mã chính là mã tên/bộ kiểm định,
     * ví dụ AUN-QA, ABET, FIBAA, ASIIN, TT04-2025.
     */
    public String codeBoTieuChuan(String requestedId, String tenBoTieuChuan){
        if(requestedId!=null && !requestedId.isBlank()) return requestedId.trim().toUpperCase(Locale.ROOT);
        String raw=tenBoTieuChuan==null?"":tenBoTieuChuan.trim();
        String n=Normalizer.normalize(raw,Normalizer.Form.NFD).replaceAll("\\p{M}","").toUpperCase(Locale.ROOT);
        if(n.contains("AUN") && n.contains("QA")) return "AUN-QA";
        if(n.contains("ABET")) return "ABET";
        if(n.contains("FIBAA")) return "FIBAA";
        if(n.contains("ASIIN")) return "ASIIN";
        if(n.contains("04/2025") || n.contains("THONG TU 04")) return "TT04-2025";
        String code=n.replaceAll("[^A-Z0-9]+","-").replaceAll("(^-|-$)","");
        if(code.isBlank()) throw new IllegalArgumentException("Vui lòng chọn/nhập mã bộ tiêu chuẩn");
        return code.length()>24?code.substring(0,24):code;
    }

    // Thông tư 04/2025: mã tiêu chuẩn H1..H8
    public String nextTieuChuan(String ignored){
        int max=maxSuffix("tieuChuan","_id",Pattern.compile("H(\\d+)"));
        if(max>=8) throw new IllegalStateException("Bộ tiêu chuẩn hiện hành chỉ có 8 tiêu chuẩn H1-H8");
        return "H"+(max+1);
    }

    // H2 -> H2.02.01, H2.02.02...
    public String nextTieuChi(String maTieuChuan){
        Matcher std=Pattern.compile("H(\\d+)").matcher(maTieuChuan==null?"":maTieuChuan.trim());
        if(!std.matches()) throw new IllegalArgumentException("Mã tiêu chuẩn phải có dạng H1..H8");
        int n=Integer.parseInt(std.group(1));
        String prefix=String.format("H%d.%02d.",n,n);
        int max=0;
        Pattern p=Pattern.compile(Pattern.quote(prefix)+"(\\d{2,})");
        for(Document d:mongoTemplate.getCollection("tieuChi").find()){
            Object raw=d.get("_id"); if(raw==null) continue;
            Matcher m=p.matcher(String.valueOf(raw)); if(m.matches()) max=Math.max(max,Integer.parseInt(m.group(1)));
        }
        return String.format("%s%02d",prefix,max+1);
    }

    public String nextMaMinhChung(String maTieuChi){
        int max=0; String prefix=maTieuChi+"."; Pattern p=Pattern.compile(Pattern.quote(prefix)+"(\\d{2,})");
        for(Document d:mongoTemplate.getCollection("minhChung").find()){
            Object raw=d.get("maMinhChung"); if(raw==null) continue;
            Matcher m=p.matcher(String.valueOf(raw)); if(m.matches()) max=Math.max(max,Integer.parseInt(m.group(1)));
        }
        return String.format("%s%02d",prefix,max+1);
    }
}
