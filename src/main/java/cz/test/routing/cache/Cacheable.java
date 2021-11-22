package cz.test.routing.cache;

public interface Cacheable {

    boolean requireCacheUpdate();

    void updateCache();

}
