package com.huit.kiemdinh.repository;
import com.huit.kiemdinh.model.ThongBao;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface ThongBaoRepository extends MongoRepository<ThongBao, String> {}
