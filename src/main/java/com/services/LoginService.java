package com.services;

import com.security.CustomUserDetails;
import com.security.jwt.JsonTokenProvider;
import com.security.payload.LoginRequest;
import com.security.payload.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JsonTokenProvider jsonTokenProvider;


    /* return token for user if username and password matches */
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword()
            ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jsonTokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return new ResponseEntity<>(new LoginResponse(token, userDetails.getUsername(), userDetails.getAuthorities()), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("incorrect username or password ", HttpStatus.FORBIDDEN);
        }
    }
}
