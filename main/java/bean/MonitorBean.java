package bean;

import com.sun.org.apache.bcel.internal.generic.FLOAD;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by smit on 20/3/22.
 */
public class MonitorBean
{
    private String name;
    private String ip;
    private String type;
    private int id;
    private String availability;
    private String ipForChart;
    String status;

    float cpu;
    float memory;
    float disk;

    private List<MonitorBean> monitorBeanList = new ArrayList<>();

    private HashMap<String, Object> pingMatrixHashMap = new HashMap<>();

    private HashMap<String, Object> sshMatrixHashMap = new HashMap<>();

    private HashMap<String, Object> pingStatusHashMap = new HashMap<>();

    private HashMap<String, Object> sshStatusHashMap = new HashMap<>();

    private List<HashMap<String, Object>> cpuMap = new ArrayList<>();

    private List<HashMap<String, Object>> rttMap = new ArrayList<>();

    private HashMap<Float, Timestamp> pingRTTPerPollingTimeHashMap = new LinkedHashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public float getCpu() {
        return cpu;
    }

    public void setCpu(float cpu) {
        this.cpu = cpu;
    }

    public float getMemory() {
        return memory;
    }

    public void setMemory(float memory) {
        this.memory = memory;
    }

    public float getDisk() {
        return disk;
    }

    public void setDisk(float disk) {
        this.disk = disk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MonitorBean> getMonitorBeanList() {
        return monitorBeanList;
    }

    public void setMonitorBeanList(List<MonitorBean> monitorBeanList) {
        this.monitorBeanList = monitorBeanList;
    }

    public HashMap<String, Object> getPingMatrixHashMap() {
        return pingMatrixHashMap;
    }

    public void setPingMatrixHashMap(HashMap<String, Object> pingMatrixHashMap) {
        this.pingMatrixHashMap = pingMatrixHashMap;
    }

    public HashMap<String, Object> getSshMatrixHashMap() {
        return sshMatrixHashMap;
    }

    public void setSshMatrixHashMap(HashMap<String, Object> sshMatrixHashMap) {
        this.sshMatrixHashMap = sshMatrixHashMap;
    }

    public HashMap<String, Object> getSshStatusHashMap() {
        return sshStatusHashMap;
    }

    public void setSshStatusHashMap(HashMap<String, Object> sshStatusHashMap) {
        this.sshStatusHashMap = sshStatusHashMap;
    }

    public HashMap<String, Object> getPingStatusHashMap() {
        return pingStatusHashMap;
    }

    public void setPingStatusHashMap(HashMap<String, Object> pingStatusHashMap) {
        this.pingStatusHashMap = pingStatusHashMap;
    }

    public List<HashMap<String, Object>> getCpuMap() {
        return cpuMap;
    }

    public void setCpuMap(List<HashMap<String, Object>> cpuMap) {
        this.cpuMap = cpuMap;
    }

    public HashMap<Float, Timestamp> getPingRTTPerPollingTimeHashMap() {
        return pingRTTPerPollingTimeHashMap;
    }

    public void setPingRTTPerPollingTimeHashMap(HashMap<Float, Timestamp> pingRTTPerPollingTimeHashMap) {
        this.pingRTTPerPollingTimeHashMap = pingRTTPerPollingTimeHashMap;
    }

    public List<HashMap<String, Object>> getRttMap() {
        return rttMap;
    }

    public void setRttMap(List<HashMap<String, Object>> rttMap) {
        this.rttMap = rttMap;
    }

    public String getIpForChart() {
        return ipForChart;
    }
    public void setIpForChart(String ipForChart) {
        this.ipForChart = ipForChart;
    }
}
