package org.hbrs.ooka.uebung1.buchungssystem;

import org.hbrs.ooka.uebung1.cache.Caching;

import java.util.List;

public class Cache implements Caching<Object> {
    private final org.hbrs.ooka.uebung1.cache.Cache cache = new org.hbrs.ooka.uebung1.cache.Cache();

    @Override
    public void cacheResult(String key, List<Object> value) {
        this.cache.cacheResult(key, value);
    }

    @Override
    public List<Object> get(String key) {
        return this.cache.get(key);
    }

    @Override
    public List<Object> remove(String key) {
        return this.cache.remove(key);
    }

    @Override
    public void clear() {
        this.cache.clear();
    }
}
