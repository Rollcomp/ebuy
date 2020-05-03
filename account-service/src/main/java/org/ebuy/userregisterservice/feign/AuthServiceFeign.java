package org.ebuy.userregisterservice.feign;

import org.ebuy.userregisterservice.dto.ChangePasswordDto;
import org.ebuy.userregisterservice.dto.ResetPasswordDto;
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
@FeignClient(name = "oauth-server")
@RequestMapping("/api")
public interface AuthServiceFeign {

    @PostMapping("/registerUser")
    public Message<String> registerUser(@RequestBody UserDto userDto);

    @PostMapping(value = "/confirmUser")
    public Message<String> confirmUserAccount(@RequestParam("t") String confirmationToken);

    @PostMapping("/resetPassword")
    public Message<String> sendPasswordResetMail(@RequestBody String userEmail);

    @PostMapping("/resetPassword")
    public Message<String> resetUserPassword(@RequestParam("token") String token, @RequestBody ResetPasswordDto passwordDto);

    @PostMapping("/changePassword")
    public Message<String> changeUserPassword(@RequestBody ChangePasswordDto changePasswordDto);

}
