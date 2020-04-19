package org.ebuy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Burak Köken on 19.4.2020.
 */
@RestController
@RequestMapping("/basket")
public class BasketController {

    @PostMapping("/addToBasket")
    public ResponseEntity<?> addToBasket() {
        return null;
    }

    @GetMapping("/updateBasketItemAsync")
    public ResponseEntity<?> getBasketAsync() {
        return null;
    }

    @DeleteMapping("/removeBasketItem")
    public ResponseEntity<?> removeBasketItem(@RequestParam String itemId) {
        return null;
    }

    @PutMapping("/updateBasketItemAsync")
    public ResponseEntity<?> updateBasketItemAsync() {
        /* item id, quantity */
        return null;
    }

    @GetMapping("/getBasketItemCountAsync")
    public ResponseEntity<?> getBasketItemCountAsync() {
        return null;
    }

    @GetMapping("/getBasketPreview")
    public ResponseEntity<?> getBasketPreview() {
        return null;
    }

}
