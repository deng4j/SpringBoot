package com.itheima.jedis;

import redis.clients.jedis.Jedis;

public class Jedis_List {
    public static void main(String[] args) {
        Jedis jedis=new Jedis("127.0.0.1",6379);
        jedis.flushDB();
        System.out.println("===========添加一个1ist===========");
        jedis.lpush("collections","Arraylist","Vector","Stack","HashMap","WeakHashMap","LinkedHashMap");
        jedis.lpush("collections","HashSet");
        jedis.lpush("collections","TreeSet");
        jedis.lpush("collections","TreeMap");
        System.out.println("collections的内容:"+jedis.lrange("collections",0,-1));//-1代表倒数第一个数
        System.out.println("collections区间0-3的元素:"+jedis.lrange("collections",0,3));

        System.out.println("===============================");
        //删除列表指定的值,第二个参数为删除的个数(有重复时),后add进去的值先被测,类似于出找
        System.out.println("删除指定元素个数:"+jedis.lrem("collections",2,"HashMap"));
        System.out.println("collections的内容:"+jedis.lrange("collections",0,-1));
        System.out.println("删除下表0-3区间之外的元素:"+jedis.ltrim("collections",0,3));
        System.out.println("collections的内容:"+jedis.lrange("collections",0,-1));
        System.out.println("collections列表出栈(左端):"+jedis.lpop("collections"));
        System.out.println("collections的内容:"+jedis.lrange("collections",0,-1));
        System.out.println("collections添加元素,从列表右端,与1push相对应:"+jedis.rpush("collections", "EnumMap"));
        System.out.println("collections的内容:"+jedis.lrange("collections",0,-1));
        System.out.println("collections列表出栈(右端):"+jedis.rpop("collections"));
        System.out.println("collections的内容:"+jedis.lrange("collections",0,-1));
        System.out.println("修改collections指定下标1的内容:"+jedis.lset("collections",1,"LinkedArrayList"));
        System.out.println("collections的内容:"+jedis.lrange("collections",0,-1));

        System.out.println("===============================");
        System.out.println("collections的长度:"+jedis.llen("collections"));
        System.out.println("获取collections下标为2的元素:"+jedis.lindex("collections",2));

        System.out.println("===============================");
        jedis.lpush("sortedList","3","6","2","0","7","4");
        System.out.println("sortedList排序前:"+jedis.lrange("sortedList",0,-1));
        System.out.println(jedis.sort("sortedList"));
        System.out.println("sortedList排序后:"+jedis.lrange("sortedList",0,-1));
    }
}