package org.ebuy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Burak Köken on 19.4.2020.
 */
@RestController
@RequestMapping("/carts")
public class CheckoutController {

    /* User Basket */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserBasket(@PathVariable String userId) {
        return null;
    }

    /* Code Promotions */
    @GetMapping("/{userId}/code-promotions")
    public ResponseEntity<?> getCodePromotionsForUserBasket(@PathVariable String userId) {
        return null;
    }

    @PostMapping("/{userId}/code-promotions")
    public ResponseEntity<?> addCodePromotionToUserBasket(@PathVariable String userId) {
        return null;
    }

    @DeleteMapping("/{userId}/code-promotions/{codePromotion}")
    public ResponseEntity<?> deleteCodePromotionForUserBasket(@PathVariable String userId, @RequestParam String codePromotion) {
        return null;
    }

    @DeleteMapping("/{userId}/code-promotions")
    public ResponseEntity<?> deleteCodePromotionsForUserBasket(@PathVariable String userId) {
        return null;
    }

    /* Coupons */
    @GetMapping("/{userId}/coupons")
    public ResponseEntity<?> getCouponsForUserBasket(@PathVariable String userId) {
        return null;
    }

    @PostMapping("/{userId}/coupons")
    public ResponseEntity<?> addCouponToUserBasket(@PathVariable String userId) {
        return null;
    }

    @DeleteMapping("/{userId}/coupons/{couponId}")
    public ResponseEntity<?> deleteCouponForUserBasket(@PathVariable String userId, @RequestParam String couponId) {
        return null;
    }

    @DeleteMapping("/{userId}/coupons")
    public ResponseEntity<?> deleteCouponsForUserBasket(@PathVariable String userId) {
        return null;
    }

    /* Shipping Options */
    @GetMapping("/{userId}/shipping-options")
    public ResponseEntity<?> getShippingOptionsForUserBasket(@PathVariable String userId){
        return null;
    }

    @PostMapping("/{userId}/shipping-options")
    public ResponseEntity<?> addShippingOptionToUserBasket(@PathVariable String userId){
        return null;
    }

    /* Installment */
    @PostMapping("/{userId}/payment-options/{option}/installment-options")
    public ResponseEntity<?> getInstallment(@PathVariable String userId, @RequestParam String binCode){
        return null;
    }

    @PostMapping("/{userId}/payment-options/{option}/installment-options/{installmentId}")
    public ResponseEntity<?> addInstallmentToUserBasket(@PathVariable String userId, @RequestParam String binCode){
        return null;
    }

}
