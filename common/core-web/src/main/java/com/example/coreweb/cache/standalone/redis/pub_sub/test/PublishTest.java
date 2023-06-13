package com.example.coreweb.cache.standalone.redis.pub_sub.test;

import com.example.coreweb.cache.standalone.redis.JacksonRedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * redis{@link org.springframework.data.redis.connection.MessageListener}发布消息测试
 *
 * @author 朱伟伟
 * @date 2023-06-12 13:51
 **/
@RestController
@RequestMapping("/redis/publish")
public class PublishTest {

    private RedisConnectionFactory redisConnectionFactory;
    private StringRedisTemplate stringRedisTemplate;
    private JacksonRedisTemplate jacksonRedisTemplate;

    @Autowired
    public void setRedisConnectionFactory(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Autowired
    public void setJacksonRedisTemplate(JacksonRedisTemplate jacksonRedisTemplate) {
        this.jacksonRedisTemplate = jacksonRedisTemplate;
    }

    @GetMapping("/test")
    public void test() {
//        Map<String, String> message = new HashMap<>();
//        message.put("name", "朱伟伟");
//        message.put("age", "29");

//        redisConnectionFactory.getConnection().publish("test".getBytes(StandardCharsets.UTF_8), "TEST测试".getBytes());
//        redisConnectionFactory.getConnection().publish("hello".getBytes(StandardCharsets.UTF_8), "HELLO测试".getBytes());

//        stringRedisTemplate.convertAndSend("port", "打印端口号，来接收消息和集群部署时测试");
//        stringRedisTemplate.convertAndSend("port", JSON.toJSONString(message));
//        stringRedisTemplate.convertAndSend("stringHandler", "打印端口号，来接收消息和集群部署时测试");
//        stringRedisTemplate.convertAndSend("stringHandler", JSON.toJSONString(message));

//        jacksonRedisTemplate.convertAndSend("mapHandler", message);
//        JSONObject object = new JSONObject();
//        object.put("name", "朱伟伟");
//        object.put("age", "29");
//        jacksonRedisTemplate.convertAndSend("JSONObjectHandler", object);

//        Person person = new Person();
//        person.setName("朱伟伟");
//        person.setAge(29);
//        jacksonRedisTemplate.convertAndSend("POJOHandler", person);


    }

}
