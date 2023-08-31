package com.itheima.vote.service.impl;

import com.itheima.vote.domain.Star;
import com.itheima.vote.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StarServiceImpl implements StarService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public  Set<String>  getCharts(String name) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        Set<String> myZSet = zSetOperations.range("Stars", 0, -1);
        if (name!=null){
            if (myZSet.contains(name)){
                zSetOperations.incrementScore("Stars",name,1);
            }else {
                zSetOperations.add("Stars",name,1);
            }
        }
        myZSet=getAll();
        return myZSet;
    }

    @Override
    public Set<String> getAll() {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        Set<String> myZSet = zSetOperations.range("Stars", 0, -1);
        myZSet = myZSet.stream().map(item -> {
            Double stars = zSetOperations.score("Stars", item);
            String newItem = item + stars;
            return newItem;
        }).collect(Collectors.toSet());
        return myZSet;
    }
}
