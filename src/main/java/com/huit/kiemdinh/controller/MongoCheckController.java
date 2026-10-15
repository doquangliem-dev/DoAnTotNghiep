package com.huit.kiemdinh.controller;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class MongoCheckController {
    private final MongoTemplate mongoTemplate;
    public MongoCheckController(MongoTemplate mongoTemplate){ this.mongoTemplate=mongoTemplate; }

    @GetMapping("/api/admin/mongo-check")
    public Map<String,Object> check(){
        Map<String,Object> r=new LinkedHashMap<>();
        r.put("database", mongoTemplate.getDb().getName());
        r.put("collections", mongoTemplate.getCollectionNames());
        for (String c : new String[]{"chuongTrinhDaoTao","boTieuChuan","tieuChuan","tieuChi","nguoiDung","phanCong","minhChung","baoCaoTieuChi"}) {
            r.put(c, mongoTemplate.getCollection(c).countDocuments());
        }
        return r;
    }
}
