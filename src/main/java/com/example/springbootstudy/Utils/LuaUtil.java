package com.example.springbootstudy.Utils;

import redis.clients.jedis.Jedis;

public class LuaUtil {
    private static String UidScript =
            "    local newUid = redis.call(\"INCR\", KEYS[1])\n" +
            "    return newUid\n";
    public static String getNextUid(Jedis jedis){
        return jedis.eval(UidScript,1,"uid_counter").toString();
    }
}
