package bean;

import java.util.ArrayList;
import java.util.List;

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

    private List<MonitorBean> matrixList = new ArrayList<>();

    private List<MonitorBean> sshMatrixList = new ArrayList<>();

    private List<MonitorBean> pingStatusList = new ArrayList<>();

    private List<MonitorBean> sshStatusList = new ArrayList<>();

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

    public List<MonitorBean> getMatrixList() {
        return matrixList;
    }

    public void setMatrixList(List<MonitorBean> matrixList) {
        this.matrixList = matrixList;
    }

    public List<MonitorBean> getSshMatrixList() {
        return sshMatrixList;
    }

    public void setSshMatrixList(List<MonitorBean> sshMatrixList) {
        this.sshMatrixList = sshMatrixList;
    }

    public List<MonitorBean> getSshStatusList() {
        return sshStatusList;
    }

    public void setSshStatusList(List<MonitorBean> sshStatusList) {
        this.sshStatusList = sshStatusList;
    }

    public List<MonitorBean> getPingStatusList() {
        return pingStatusList;
    }

    public void setPingStatusList(List<MonitorBean> pingStatusList) {
        this.pingStatusList = pingStatusList;
    }
}
