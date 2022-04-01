package bean;

import com.sun.org.apache.bcel.internal.generic.FLOAD;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    float cpu;
    float memory;
    float disk;

    private List<MonitorBean> monitorBeanList = new ArrayList<>();

    private List<Object> matrixList = new ArrayList<>();

    private List<Object> sshMatrixList = new ArrayList<>();

    private List<Integer> pingStatusList = new ArrayList<>();

    private List<Integer> sshStatusList = new ArrayList<>();

    private Map<Timestamp, Float> cpuMap = new LinkedHashMap<>();

    private Map<Timestamp, Float> rttMap = new LinkedHashMap<>();

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

    public List<MonitorBean> getMonitorBeanList() {
        return monitorBeanList;
    }

    public void setMonitorBeanList(List<MonitorBean> monitorBeanList) {
        this.monitorBeanList = monitorBeanList;
    }

    public List<Object> getMatrixList() {
        return matrixList;
    }

    public void setMatrixList(List<Object> matrixList) {
        this.matrixList = matrixList;
    }

    public List<Object> getSshMatrixList() {
        return sshMatrixList;
    }

    public void setSshMatrixList(List<Object> sshMatrixList) {
        this.sshMatrixList = sshMatrixList;
    }

    public List<Integer> getSshStatusList() {
        return sshStatusList;
    }

    public void setSshStatusList(List<Integer> sshStatusList) {
        this.sshStatusList = sshStatusList;
    }

    public List<Integer> getPingStatusList() {
        return pingStatusList;
    }

    public void setPingStatusList(List<Integer> pingStatusList) {
        this.pingStatusList = pingStatusList;
    }

    public Map<Timestamp, Float> getCpuMap() {
        return cpuMap;
    }

    public void setCpuMap(Map<Timestamp, Float> cpuMap) {
        this.cpuMap = cpuMap;
    }

    public Map<Timestamp, Float> getRttMap() {
        return rttMap;
    }

    public void setRttMap(Map<Timestamp, Float> rttMap) {
        this.rttMap = rttMap;
    }
}
