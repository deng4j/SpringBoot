package com.itheima;

import com.itheima.jedis.Jedis_1;
import com.itheima.redis.redis1.TestRedis;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest
class RedisApplicationTests {

    @Test
    void jedis() {
        Jedis_1 jedis_1 = new Jedis_1();
        jedis_1.testJedis();
    }

    @Test
    void testMulti(){
        Jedis_1 jedis_1 = new Jedis_1();
        jedis_1.testMulti(18);
    }

    @Test
    void testWATCH(){
        Jedis_1 jedis_1 = new Jedis_1();
        jedis_1.testWATCH(18);
    }

    @Test
    public void testFolder(){
        Jedis_1 jedis_1 = new Jedis_1();

        jedis_1.testFolder();
    }

}
