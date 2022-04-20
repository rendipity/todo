package com.course.todos.Service;

import com.course.todos.DAO.TodoItemMapper;
import com.course.todos.entity.Result.ResponseResult;
import com.course.todos.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    TodoItemMapper todoItemMapper;

    @Autowired
    private  RedisTemplate redisTemplate;

    public Boolean authentication(User user){
        User user1=todoItemMapper.queryPasswordByUsername(user.getUsername());
        if (user1!=null&&user1.getPassword().equals(user.getPassword())){
            user.setId(user1.getId());
            return true;
        }
        else
            return false;
    }
    public ResponseResult<String> sendVerifyCode (String phone){
        if (!checkPhone(phone))
            return ResponseResult.fail("手机号不合法");
        int verifyCode=generateCode();
        try {
            send(verifyCode,phone);
            cacheCode(phone,verifyCode);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail("发送失败，请重试");
        }
        return ResponseResult.success("验证码发送成功,请注意查收");
    }
    public Boolean signup(User user){
        String tryCountKey=user.getPhone()+"TryCount";
        String verifyCodeKey=user.getPhone()+"VerifyCode";
        System.out.println(redisTemplate.opsForValue().get(tryCountKey)+" 过期时间: "+redisTemplate.getExpire(tryCountKey));
        System.out.println(redisTemplate.opsForList().range(verifyCodeKey, 0,5)+" expire "+redisTemplate.getExpire(verifyCodeKey));
        return true;
    }
    private Boolean send(int code,String phone) throws Exception{
        System.out.println(phone);
        return true;
    }
    private int generateCode(){
        Random random=new Random();
        int code=random.nextInt(10000);
        return code<1000?code+1000:code;
    }
    private Boolean checkPhone(String phone){
        return true;
    }

    public void cacheCode(String phone,int code) throws Exception{
        String sendIntervalKey=phone+"Interval";
        String tryCountKey=phone+"TryCount";
        String verifyCodeKey=phone+"VerifyCode";
        redisTemplate.opsForValue().set(sendIntervalKey,1,1, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(tryCountKey,0,5,TimeUnit.MINUTES);
        redisTemplate.opsForList().leftPush(verifyCodeKey,code);
        redisTemplate.expire(verifyCodeKey,5,TimeUnit.MINUTES);
    }
}
