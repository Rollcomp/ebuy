package org.ebuy.userregisterservice.feign;

import org.ebuy.userregisterservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Ozgur Ustun on May, 2020
 */
@FeignClient(name = "authentication-service")
@RequestMapping("/register")
public interface AuthServiceFeign {

    @PostMapping("/user")
    public Message<String> registerUser(@RequestBody UserDto userDto);

    @PostMapping(value = "/confirm")
    public Message<String> confirmUserAccount(@RequestParam("t") String confirmationToken);
}
