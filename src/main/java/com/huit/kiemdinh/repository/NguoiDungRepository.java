package com.huit.kiemdinh.repository;

import com.huit.kiemdinh.model.NguoiDung;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface NguoiDungRepository extends MongoRepository<NguoiDung, String> {
    Optional<NguoiDung> findByEmailIgnoreCase(String email);
}
