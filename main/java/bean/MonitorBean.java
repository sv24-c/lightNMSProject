package bean;

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

    private List<HashMap<String, Object>> matrixList = new ArrayList<>();

    private List<HashMap<String, Object>> sshMatrixList = new ArrayList<>();

    private List<HashMap<String, Object>> pingStatusList = new ArrayList<>();

    private List<HashMap<String, Object>> sshStatusList = new ArrayList<>();

    private List<HashMap<String, Object>> cpuMap = new ArrayList<>();

    private List<HashMap<String, Object>> rttMap = new ArrayList<>();

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

    public List<HashMap<String, Object>> getMatrixList() {
        return matrixList;
    }

    public void setMatrixList(List<HashMap<String, Object>> matrixList) {
        this.matrixList = matrixList;
    }

    public List<HashMap<String, Object>> getSshMatrixList() {
        return sshMatrixList;
    }

    public void setSshMatrixList(List<HashMap<String, Object>> sshMatrixList) {
        this.sshMatrixList = sshMatrixList;
    }

    public List<HashMap<String, Object>> getSshStatusList() {
        return sshStatusList;
    }

    public void setSshStatusList(List<HashMap<String, Object>> sshStatusList) {
        this.sshStatusList = sshStatusList;
    }

    public List<HashMap<String, Object>> getPingStatusList() {
        return pingStatusList;
    }

    public void setPingStatusList(List<HashMap<String, Object>> pingStatusList) {
        this.pingStatusList = pingStatusList;
    }

    public List<HashMap<String, Object>> getCpuMap() {
        return cpuMap;
    }

    public void setCpuMap(List<HashMap<String, Object>> cpuMap) {
        this.cpuMap = cpuMap;
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
