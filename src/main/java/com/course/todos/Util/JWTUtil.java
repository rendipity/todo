package com.course.todos.Util;

import com.course.todos.entity.User;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {
    private JWTUtil() {}
    public static String getToken(User user){
        JwtBuilder builder = Jwts.builder();
        Map<String,Object> map=new HashMap<>();
        map.put("id",user.getId());
        map.put("username",user.getUsername());
        return builder.setClaims(map) //传值
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) //设置过期时间
                .signWith(SignatureAlgorithm.HS256,"user") //设置加密方式和密码
                .compact(); //生成Token
    }
    public static User verify(String token){
        if (token==null) {
            System.out.println("token 不能为null");
            return null;
        }
        //jwt解析器
        JwtParser jwtParser=Jwts.parser();
        jwtParser.setSigningKey("user1");
        try{
            Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            Integer id=body.get("id", Integer.class);
            String username=body.get("username",String.class);
            return new User(id,username,null,null);
        }catch (Exception e){
            System.out.println("出异常了");
            //e.printStackTrace();
            return null;
        }
    }


}
