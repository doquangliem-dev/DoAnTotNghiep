package com.huit.kiemdinh.repository;
import com.huit.kiemdinh.model.TienDo;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface TienDoRepository extends MongoRepository<TienDo, String> {}
