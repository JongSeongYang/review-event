package com.triple.review.utils;

import java.math.BigInteger;
import java.util.UUID;

public class UUIDFormatter {

    public static UUID StringToUUID(String s) {
        return new UUID(
                new BigInteger(s.substring(0, 16), 16).longValue(),
                new BigInteger(s.substring(16), 16).longValue());
    }

    public static UUID check(String s){
        if(s.contains("-"))
            return UUID.fromString(s);
        else
            return StringToUUID(s);
    }
}
