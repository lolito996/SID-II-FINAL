package com.example.demo.services.interfaces;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    public String generateToken(String username);
    
    String generateToken(UserDetails userDetails);
    
    public String extractUserName(String token);

    public boolean validateToken(String token, UserDetails userDetails);


}
