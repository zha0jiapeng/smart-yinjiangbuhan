package com.ruoyi.web.controller.basic.view;

import com.ruoyi.common.constant.Constants;
import io.jsonwebtoken.*; // 导入所需的类

import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SAParser {
    public static void main(String[] args) throws Exception {



    /*    Claims claims = parseToken(token);
        // 解析对应的权限以及用户信息
        String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
        String userKey = getTokenKey(uuid);
        LoginUser user = redisCache.getCacheObject(userKey);*/


        String soapToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpblR5cGUiOiJsb2dpbiIsImxvZ2luSWQiOiJzeXNfdXNlcjoxIiwicm5TdHIiOiJHTGxIelBNTVBaWGsyQnEyNk9rNXc3bHBmdHZZZWU5TSIsInVzZXJJZCI6MX0.qWt7JfPlNCi7nQqJC4nMukL64pO9Y97o964v6g1OlwQ"; // 将"your_soap_token"替换为要解析的SOAP Token字符串

        byte[] decodedBytes = DatatypeConverter.parseBase64Binary(soapToken);
        String parsedSoapToken = new String(decodedBytes);

        System.out.println(parsedSoapToken);


        String saToken = "eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6ImFhNWE4MzQ5LTcyMGYtNDYwYi1hMWNkLTFjZTc5NTFiYjk4ZSJ9.2KUxVm54XWvgwd59Udm8cmTS5MolqLzEj7aRKXy2rAw4dlHwt9GVfyN_Rx49Y2I0bEqaLr5EeV9GeRb8Nb2-XQ";
        Claims claims;
        try {

            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey("abcdefghijklmnopqrstuvwxyz") // 设置密钥，这里应该与生成SA-Token时使用的密钥保持一致
                    .parseClaimsJws(saToken);

            claims = claimsJws.getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("SA-Token已过期");
            return;
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            System.out.println("无效的SA-Token");
            return;
        }

        // 在claims对象上获取解析得到的信息
        String userId = claims.getSubject();
        List<String> roles = claims.get("roles", ArrayList.class);

        System.out.println("User ID: " + userId);
        System.out.println("Roles: " + roles);


        Map<String, Object> claimsss = new HashMap<>();
        claimsss.put(Constants.LOGIN_USER_KEY, "{\"userId\":1,\"userName\":\"ceshi\",\"loginTime\":10,\"expireTime\":100}");

        String token = Jwts.builder()
                .setClaims(claimsss)
                .signWith(SignatureAlgorithm.HS512, "abcdefghijklmnopqrstuvwxyz").compact();

        System.out.println("SA-Token: " + token);

        Jws<Claims> claimsJws222 = Jwts.parser()
                .setSigningKey("abcdefghijklmnopqrstuvwxyz") // 设置密钥，这里应该与生成SA-Token时使用的密钥保持一致
                .parseClaimsJws(token);

        System.out.println(claimsJws222 + "assdad");
        System.out.println(claimsJws222.getBody());

        Claims claims333 = Jwts.parser()
                .setSigningKey("abcdefghijklmnopqrstuvwxyz")
                .parseClaimsJws(token)
                .getBody();

        System.out.println(claims333.get(Constants.LOGIN_USER_KEY));



    /*    Claims claims1 = Jwts.parser().setSigningKey("abcdefghijklmnopqrstuvwxyz").parseClaimsJws(saToken).getBody();
        String userId = claims1.getSubject();
        List<String> roles = claims1.get("roles", ArrayList.class);

        System.out.println("User ID: " + userId);
        System.out.println("Roles: " + roles);
*/

    }
}