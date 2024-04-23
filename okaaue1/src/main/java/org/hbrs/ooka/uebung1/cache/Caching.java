package org.hbrs.ooka.uebung1.cache;

import java.util.List;

public interface Caching<Object> {
    void cacheResult(String key, List<Object> value);

    List<Object> get(String key);

    List<Object> remove(String key);

    void clear();
}
