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

    private HashMap<String, Float> topFiveDiskHashMap = new HashMap<>();

    private HashMap<String, Float> topFiveRTTHashMap = new HashMap<>();

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

    public HashMap<String, Float> getTopFiveDiskHashMap() {
        return topFiveDiskHashMap;
    }

    public void setTopFiveDiskHashMap(HashMap<String, Float> topFiveDiskHashMap) {
        this.topFiveDiskHashMap = topFiveDiskHashMap;
    }

    public HashMap<String, Float> getTopFiveRTTHashMap() {
        return topFiveRTTHashMap;
    }

    public void setTopFiveRTTHashMap(HashMap<String, Float> topFiveRTTHashMap) {
        this.topFiveRTTHashMap = topFiveRTTHashMap;
    }
}
