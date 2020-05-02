package org.ebuy.userregisterservice.controller;

import com.netflix.discovery.converters.Auto;
import org.ebuy.userregisterservice.dto.UserDto;
import org.ebuy.userregisterservice.feign.AuthServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Ozgur Ustun on May, 2020
 */

@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private AuthServiceFeign authServiceFeign;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto){
        return (ResponseEntity<?>) authServiceFeign.registerUser(userDto);
    }

    @PostMapping("/registrationConfirm")
    public ResponseEntity<?> confirmUserAccount(@RequestParam("t") String confirmationToken) {
        return (ResponseEntity<?>) authServiceFeign.confirmUserAccount(confirmationToken);
    }


}
