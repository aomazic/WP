package com.example.backend.Service;

import com.example.backend.Repo.ItemRepository;
import com.example.backend.Repo.OrderRepository;
import com.example.backend.Repo.PromoCodeRepository;
import com.example.backend.model.PromoCode;
import com.example.backend.model.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PromoCodeService {
    private final PromoCodeRepository promoCodeRepository;
    @Autowired

    public PromoCodeService(PromoCodeRepository promoCodeRepository) {
        this.promoCodeRepository = promoCodeRepository;
    }

    public void deletePromoCode(Long id){
        promoCodeRepository.deleteById(id);
    }

    public PromoCode insertPromoCode(int discount){
        String promoCode = UUID.randomUUID().toString().replaceAll("-", "");
        PromoCode  promoCode1 = new PromoCode(
                promoCode,
                discount,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(43200)
        );
        promoCodeRepository.save(promoCode1);
        return promoCode1;
    }

    public String usePromoCode(String promoCode) {
        PromoCode promoCode1 = promoCodeRepository.findByPromoCode(promoCode);

        if (promoCode1 == null) {
            throw new RuntimeException(String.format("PromoCode %s not found", promoCode));
        }
        else {

            if (promoCode1.getExpiresAt().toLocalTime().isAfter(LocalDateTime.now().toLocalTime())) {
                throw new RuntimeException("PromoCode is expired");
            } else {
                deletePromoCode(promoCode1.getId());
                return "Success";
            }
        }
    }

}
