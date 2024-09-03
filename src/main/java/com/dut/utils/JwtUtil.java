package com.dut.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtUtil {

    /**
     * 使用jwt生成token
     * @param claims 字段
     * @return 生成的token字符串
     */
    public static String generateJwt(Map<String, Object> claims, String jwtSecretKey) {
        //默认使用 HMAC-SHA-256 算法
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
        Date now = new Date();

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .signWith(secretKey)
                .compact();
    }

    /**
     * 解析token
     * @param token jwt字符串
     * @return Claims
     */
    public static Claims parsePayload(String token, String jwtSecretKey){
        try{
            SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }catch (Exception e){
            System.out.println("=====> JwtUtil: " + e);
            return null;
        }
    }
}
