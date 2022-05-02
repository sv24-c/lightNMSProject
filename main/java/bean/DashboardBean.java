package bean;

import java.util.*;

/**
 * Created by smit on 31/3/22.
 */
public class DashboardBean
{
    private HashMap<String, Long>  hashMap = new HashMap<>();

    private HashMap<String, Float> topFiveCpuHashMap = new HashMap<>();

    private HashMap<String, Float> topFiveMemoryHashMap = new HashMap<>();

    public HashMap<String, Long> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, Long> hashMap) {
        this.hashMap = hashMap;
    }

    public HashMap<String, Float> getTopFiveCpuHashMap() {
        return topFiveCpuHashMap;
    }

    public void setTopFiveCpuHashMap(HashMap<String, Float> topFiveCpuHashMap) {
        this.topFiveCpuHashMap = topFiveCpuHashMap;
    }

    public HashMap<String, Float> getTopFiveMemoryHashMap() {
        return topFiveMemoryHashMap;
    }

    public void setTopFiveMemoryHashMap(HashMap<String, Float> topFiveMemoryHashMap) {
        this.topFiveMemoryHashMap = topFiveMemoryHashMap;
    }
}
