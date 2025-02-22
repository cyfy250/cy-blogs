package com.cyfy.cyblogsbackend.framework.jwt;


import com.alibaba.fastjson2.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {
    // 令牌秘钥
    private static final String JWT_KEY = "cyfy";
    // 令牌有效期
    private static final long JWT_EXPIRE = 30 * 60 * 1000;

    /**
     * 根据传入数据生成令牌
     *
     * @param data 令牌数据
     * @return
     */
    public String createToken(Object data) {
        // 获取当前时间
        long currentTime = System.currentTimeMillis();
        // 过期时间
        long expireTime = currentTime + JWT_EXPIRE;
        // 构造令牌
        JwtBuilder builder = Jwts.builder()
                .setId(UUID.randomUUID() + "")
                .setSubject(JSON.toJSONString(data))
                .setIssuer("blogs")
                .setIssuedAt(new Date(currentTime))
                .signWith(SignatureAlgorithm.HS256, encodeSecret(JWT_KEY))
                .setExpiration(new Date(expireTime));
        return builder.compact();
    }

    /**
     * 加密密钥
     *
     * @param key 令牌秘钥字符串
     * @return
     */
    private SecretKey encodeSecret(String key) {
        // 将输入字符串转换为字节数组并进行Base64编码。
        byte[] encode = Base64.getEncoder().encode(key.getBytes());
        // 创建一个SecretKeySpec对象，使用AES算法对输入的字节数组进行加密。
        SecretKeySpec aes = new SecretKeySpec(encode, 0, encode.length, "AES");
        return aes;
    }

    /**
     * 解密，获取token数据
     *
     * @param token 令牌
     * @return
     */
    public Claims parseToken(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(encodeSecret(JWT_KEY))
                .parseClaimsJws(token)
                .getBody();
        return body;
    }

    /**
     * 解密，并转换为对应实体类
     *
     * @param token 令牌
     * @param clazz 实体类
     * @param <T>
     * @return
     */
    public <T> T parseToken(String token, Class<T> clazz) {
        Claims body = parseToken(token);
        return JSON.parseObject(body.getSubject(), clazz);
    }
}
