package cz.test.routing.cache.model;

import java.util.List;

public enum RegionEnum {

    /**
     *  This enum needed to gather countries into a network that contact each other through the land.
     *  Those countries that do not have neighbors are filtered in another class.
     *
     */

    EURASIA(List.of("Europe", "Africa", "Asia")),
    AMERICA(List.of("Americas"));

    public List<String> getRegions() {
        return regions;
    }

    private List<String> regions;

    RegionEnum(List<String> regions) {
        this.regions = regions;
    }
}
