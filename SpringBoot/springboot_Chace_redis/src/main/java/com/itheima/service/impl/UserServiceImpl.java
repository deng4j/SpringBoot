package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.Users;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Caching注解可以让我们在一个方法或者类上同时指定多个Spring Cache相关的注解。其拥有三个属性：cacheable、put和evict，分别用于指定@Cacheable、@CachePut和@CacheEvict。
 *
 *    @Caching(cacheable = @Cacheable(“users”), evict = { @CacheEvict(“cache2”),
 *
 *          @CacheEvict(value = “cache3”, allEntries = true) })
 *
 *    public User find(Integer id) {
 *       returnnull;
 *
 *    }
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,Users> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 默认使用的是 ConcurrentMapCacheManager实现。
     * 这里使用的是redis缓存，不使用默认map缓存。
     */
    @Autowired
    private CacheManager cacheManager;

    /**
     * Cacheable：在方法执行前spring先查看缓存中是否有数据，如果有数据，则直接返回缓存数据；若没有数据，调用方法并将方法返回值放到缓存中
     *
     * value：缓存的名称，每个缓存名称下面可以有多个key
     * key：缓存的key
     * key的写法如下：
     * 	#user.id : #user指的是方法形参的名称, id指的是user的id属性 , 也就是使用user的id属性作为key ;
     * 	#user.name: #user指的是方法形参的名称, name指的是user的name属性 ,也就是使用user的name属性作为key ;
     * 	#result.id : #result代表方法返回值，该表达式 代表以返回对象的id属性作为key ；
     * 	#result.name : #result代表方法返回值，该表达式 代表以返回对象的name属性作为key
     *
     * 	#root.methodName：当前方法名
     * 	#root.method.name：当前方法
     * 	#root.target：当前被调用的对象
     * 	#root.targetClass：当前被调用的对象的class
     * 	#root.args[0]：当前方法参数组成的数组
     * 	#root.caches[0].name：当前被调用的方法使用的Cache
     *
     *  缓存非null值:
     * 在@Cacheable注解中，提供了两个属性分别为： condition， unless 。
     * > condition : 表示满足什么条件, 再进行缓存 ;
     * > unless : 表示满足条件则不缓存 ; 与上述的condition是反向的 ;
     *
     * 此处，我们使用的时候只能够使用 unless， 因为在condition中，我们是无法获取到结果 #result的。
     */
    @Cacheable(value = "usersCache",key = "#id", unless = "#result == null")
    @Override
    public Users selectById(String id) {
        Users users = userMapper.selectById(id);
        return users;
    }

    @Cacheable(value = "usersCache",key = "#root.methodName")
    @Override
    public List<Users> findAll(Users users){
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(users.getId() != null,Users::getId,users.getId());
        queryWrapper.eq(users.getName() != null,Users::getName,users.getName());

        return userMapper.selectList(queryWrapper);
    }

    /**
     * @CachePut 说明：
     * 作用: 将方法返回值，放入缓存
     * value: 缓存的名称, 每个缓存名称下面可以有很多key
     * key: 缓存的key-------> 支持Spring的表达式语言SPEL语法,可以有多个
     */

    @Override
    @CachePut(value = "usersCache",key = "#users.id")
    public Users saveUsers(Users users){
        userMapper.insert(users);
        return users;
    }


    /**
     * CacheEvict：清理指定缓存
     * value：缓存的名称，每个缓存名称下面可以有多个key
     * key：缓存的key。
     *
     *@CacheEvict(value = "userCache",key = "#p0.id")   //第一个参数的id属性
     *@CacheEvict(value = "userCache",key = "#root.args[0]") //#root.args[0] 代表第一个参数。
     *@CacheEvict(value = "userCache",key = "#id") //#id 代表变量名为id的参数。
     * #p0 代表第一个参数
     * allEntries为true时，意思是说这个清除缓存是清除当前value值空间下的所有缓存数据。
     */
    @Override
    @CacheEvict(value = "usersCache",key = "#p0",allEntries = false)
    public void  deleteByid(String id){
        userMapper.deleteById(id);
    }

    @CacheEvict(value = "usersCache",key = "#result.id")
    @Override
    public void update(Users users) {
        userMapper.updateById(users);
    }


}
