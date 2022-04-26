package com.course.todos.Service;

import com.course.todos.DAO.TodoItemMapper;
import com.course.todos.entity.Result.ResponseResult;
import com.course.todos.entity.User;
import com.course.todos.entity.UserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
        if (checkInterval(phone))
            return ResponseResult.fail("验证码发送过于频繁，请1分钟后再试");
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
    public ResponseResult<String> signup(UserParam user){
        String tryCountKey=user.getPhone()+"TryCount";
        String verifyCodeKey=user.getPhone()+"VerifyCode";
        if (redisTemplate.opsForValue().get(tryCountKey)==null)
             return ResponseResult.fail("验证码已失效");
        //验证码被删除提示
        if (!checkVerifyCode(verifyCodeKey, user.getVerifyCode())){
            //更新验证次数
            updateTryCount(user.getPhone());
            return ResponseResult.fail("验证码不正确");
        }
        try {
            todoItemMapper.insertUser(new User(null,user.getUsername(), user.getPassword(), user.getPhone()));
            deleteVerifyCode(user.getPhone());
        }catch (Exception e){
            return ResponseResult.fail("用户名已经存在");
        }
        return ResponseResult.success("注册成功");
    }
    private Boolean send(int code,String phone) throws Exception{
        System.out.println("给手机号: "+phone+" 发送验证码: "+code);
        return true;
    }
    private int generateCode(){
        Random random=new Random();
        int code=random.nextInt(10000);
        return code<1000?code+1000:code;
    }
    private Boolean checkPhone(String phone){
       return phone.length()==11&&phone.charAt(0)=='1';
    }
    private Boolean checkInterval(String phone){
        String IntervalKey=phone+"Interval";
        return redisTemplate.getExpire(IntervalKey)>0;
    }

    public void cacheCode(String phone,int code) throws Exception{
        String IntervalKey=phone+"Interval";
        String tryCountKey=phone+"TryCount";
        String verifyCodeKey=phone+"VerifyCode";
        redisTemplate.opsForValue().set(IntervalKey,1,1, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(tryCountKey,0,5,TimeUnit.MINUTES);
        redisTemplate.opsForList().leftPush(verifyCodeKey,code);
        redisTemplate.expire(verifyCodeKey,5,TimeUnit.MINUTES);
    }
    private Boolean checkVerifyCode(String verifyCodeKey,Integer verifyCode){
        List<Integer> codes=redisTemplate.opsForList().range(verifyCodeKey,0,5);
        if (codes==null)
            return false;
        for (Integer i:codes){
            if (Objects.equals(i, verifyCode))
                return true;
        }
        return false;
    }
    private void updateTryCount(String phone){
        String tryCountKey=phone+"TryCount";
        System.out.println(redisTemplate.opsForValue().get(tryCountKey));
        if ((Integer)redisTemplate.opsForValue().get(tryCountKey)>=2)
            deleteVerifyCode(phone);
        else
            redisTemplate.opsForValue().increment(tryCountKey);
    }
    private void deleteVerifyCode(String phone){
        String tryCountKey=phone+"TryCount";
        String verifyCodeKey=phone+"VerifyCode";
        redisTemplate.delete(tryCountKey);
        redisTemplate.delete(verifyCodeKey);
    }
}
