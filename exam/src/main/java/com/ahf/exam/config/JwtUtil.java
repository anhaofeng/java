package com.ahf.exam.config;

import com.ahf.exam.model.JwtProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Component
public class JwtUtil {

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    private static JwtUtil jwtUtil;

    @PostConstruct
    public void init() {
        jwtUtil = this;
        jwtUtil.jwtProperties = this.jwtProperties;
    }

    /**
     * 校验token是否正确
     * @param token
     * @return
     */
    public static boolean verify(String token) throws UnsupportedEncodingException {
        try {
            String secret = getClaim(token, SecurityConsts.ACCOUNT) + jwtUtil.jwtProperties.secretKey;
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     * @param token
     * @param account
     * @return
     */
    public static String getClaim(String token, String account) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(account).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,5min后过期
     * @param account
     * @return
     */
    public static String sign(String account) throws UnsupportedEncodingException {
        // 帐号加JWT私钥加密
        String secret = account + jwtUtil.jwtProperties.getSecretKey();
        // 此处过期时间，单位：毫秒
        Date date = new Date(System.currentTimeMillis() + jwtUtil.jwtProperties.getTokenExpireTime()*60*1000l);
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withClaim(SecurityConsts.ACCOUNT, account)
//                .withClaim(SecurityConsts.CURRENT_TIME_MILLIS, currentTimeMillis)
                .withExpiresAt(date)
                .sign(algorithm);
    }
}