package org.ebuy.authservice.controller;

import org.ebuy.authservice.model.AuthenticationRequest;
import org.ebuy.authservice.model.AuthenticationResponse;
import org.ebuy.authservice.security.AppUserDetails;
import org.ebuy.authservice.security.AppUserDetailsService;
import org.ebuy.authservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AppUserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserEmail(), request.getPassword())
            );

        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        AppUserDetails userDetails = (AppUserDetails) userDetailsService.loadUserByUsername(request.getUserEmail());
        String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
