package com.example.backend.Repo;

import com.example.backend.model.PromoCode;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {
    @Override
    void deleteById(@NonNull Long id);
    PromoCode findByPromoCode(String promoCode);




}
