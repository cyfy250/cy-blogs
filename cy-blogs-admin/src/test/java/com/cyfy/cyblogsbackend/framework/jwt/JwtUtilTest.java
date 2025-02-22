package com.cyfy.cyblogsbackend.framework.jwt;

import com.cyfy.cyblogsbackend.business.domain.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void createToken() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1L);
        userInfo.setUserAccount("admin");
        userInfo.setUserEmail("154545484");
        userInfo.setUserAvatar("头像");
        userInfo.setCreateTime(new Date());
        userInfo.setUpdateTime(new Date());
        // 加密，生成令牌
        String token = jwtUtil.createToken(userInfo);
        System.out.println(token);
    }

    @Test
    void parseToken() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxZGU3MzJlMy01NTYwLTRmY2UtYjdiMS1mN2YwMjQ4YmRlNDIiLCJzdWIiOiJ7XCJjcmVhdGVUaW1lXCI6XCIyMDI1LTAyLTIzVDAxOjMyOjA1LjE1NCswODowMFwiLFwidXBkYXRlVGltZVwiOlwiMjAyNS0wMi0yM1QwMTozMjowNS4xNTQrMDg6MDBcIixcInVzZXJBY2NvdW50XCI6XCJhZG1pblwiLFwidXNlckF2YXRhclwiOlwi5aS05YOPXCIsXCJ1c2VyRW1haWxcIjpcIjE1NDU0NTQ4NFwiLFwidXNlcklkXCI6MX0iLCJpc3MiOiJibG9ncyIsImlhdCI6MTc0MDI0NTUyNSwiZXhwIjoxNzQwMjQ3MzI1fQ.vzBCA1eAvpHH8GK9vv-ZLPv8KavTgGk4dbymwDB38ps";
        UserInfo userInfo = jwtUtil.parseToken(token, UserInfo.class);
        System.out.println(userInfo.getUserId());
        System.out.println(userInfo.getUserAccount());
        System.out.println(userInfo.getUserAvatar());
        System.out.println(token.length());
    }

    @Test
    void testParseToken() {
    }
}