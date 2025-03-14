package com.wbsrisktaskerx.wbsrisktaskerx.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Tạo token có chứa thêm thông tin (claims).
     *
     * @param claims   Map chứa các thông tin claims cần thêm vào JWT.
     * @param username Tên đăng nhập (sử dụng làm subject).
     * @return Chuỗi JWT được tạo ra.
     */
    public String generateToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims) // Thêm thông tin vào JWT
                .setSubject(username) // Đặt username làm subject
                .setIssuedAt(new Date()) // Thời gian phát hành token
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // Thời gian hết hạn
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Ký token bằng thuật toán HS256
                .compact();
    }

    /**
     * Tạo token đơn giản chỉ từ username.
     *
     * @param username Tên đăng nhập.
     * @return Chuỗi JWT được tạo ra.
     */
    public String generateToken(String username) {
        return generateToken(Map.of(), username);
    }

    /**
     * Trích xuất tất cả các claims từ token.
     *
     * @param token Chuỗi JWT.
     * @return Claims chứa các thông tin được lưu trong token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Lấy username từ JWT token.
     *
     * @param token Chuỗi JWT.
     * @return Username được lưu trong token.
     */
    public String getUserNameFromJwtToken(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Xác minh JWT token có hợp lệ hay không.
     *
     * @param token Chuỗi JWT.
     * @return true nếu token hợp lệ, ngược lại false.
     */
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            logger.error("JWT validation error: {}", e.getMessage());
        }
        return false;
    }

    /**
     * Tạo key bí mật dựa trên jwtSecret được mã hóa Base64.
     *
     * @return Key để ký và xác minh JWT.
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }


}
