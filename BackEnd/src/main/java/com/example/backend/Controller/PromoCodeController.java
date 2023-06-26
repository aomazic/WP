package com.example.backend.Controller;

import com.example.backend.Service.PromoCodeService;
import com.example.backend.model.Item;
import com.example.backend.model.PromoCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/promocode")
public class PromoCodeController {
    private final PromoCodeService promoCodeService;
    @Autowired
    public PromoCodeController(PromoCodeService promoCodeService) {
        this.promoCodeService = promoCodeService;
    }
    @PostMapping("/addPromoCode")
    public ResponseEntity<PromoCode> addPromoCode(@RequestParam int discount){
        PromoCode promoCode = promoCodeService.insertPromoCode(discount);
        return new ResponseEntity<>(promoCode, HttpStatus.CREATED);
    }
    @DeleteMapping("/use")
    public ResponseEntity<String> usePromoCode(@RequestParam String promoCode){
        String response = promoCodeService.usePromoCode(promoCode);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
