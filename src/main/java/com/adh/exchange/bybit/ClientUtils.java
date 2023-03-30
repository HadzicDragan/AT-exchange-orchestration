package com.adh.exchange.bybit;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

// Validate if this class name is descriptive enough
// Maybe should be changed in the future
public class ClientUtils {

    public static final long RECV_WINDOW = 5000L;

    private ClientUtils() {
    }

    public static StringBuilder genQueryStr(Map<String, Object> map) {
        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        StringBuilder sb = new StringBuilder();
        while (iter.hasNext()) {
            String key = iter.next();
            sb.append(key)
                    .append("=")
                    .append(map.get(key))
                    .append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb;
    }
}
