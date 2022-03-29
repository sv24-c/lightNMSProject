package bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smit on 25/3/22.
 */
public class PollingBean
{
    String name;
    String ip;
    String type;
    String availability;

    int id;
    int sendPacket;
    int receivePacket;
    int packetLoss;
    float rtt;

    float cpu;
    float memory;
    float disk;


    List<PollingBean> matrixBeanList = new ArrayList<>();

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

    public int getSendPacket() {
        return sendPacket;
    }

    public void setSendPacket(int sendPacket) {
        this.sendPacket = sendPacket;
    }

    public int getReceivePacket() {
        return receivePacket;
    }

    public void setReceivePacket(int receivePacket) {
        this.receivePacket = receivePacket;
    }

    public int getPacketLoss() {
        return packetLoss;
    }

    public void setPacketLoss(int packetLoss) {
        this.packetLoss = packetLoss;
    }

    public float getRtt() {
        return rtt;
    }

    public void setRtt(float rtt) {
        this.rtt = rtt;
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

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public List<PollingBean> getMatrixBeanList() {
        return matrixBeanList;
    }

    public void setMatrixBeanList(List<PollingBean> matrixBeanList) {
        this.matrixBeanList = matrixBeanList;
    }
}
