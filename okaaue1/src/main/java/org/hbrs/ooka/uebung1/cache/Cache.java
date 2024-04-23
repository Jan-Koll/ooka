package org.hbrs.ooka.uebung1.cache;

import java.util.HashMap;
import java.util.List;

public class Cache implements Caching<Object> {
    private final HashMap<String, List<Object>> storage = new HashMap<>();

    @Override
    public void cacheResult(String key, List<Object> value) {
        this.storage.put(key, value);
    }

    @Override
    public List<Object> get(String key) {
        if (!this.storage.containsKey(key)) {
            return null;
        }
        return this.storage.get(key);
    }

    @Override
    public List<Object> remove(String key) {
        return this.storage.remove(key);
    }

    @Override
    public void clear() {
        this.storage.clear();
    }
}
