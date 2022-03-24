package bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smit on 20/3/22.
 */
public class MonitorBean
{
    String name;
    String ip;
    String type;
    String id;
    String availability;


    List<MonitorBean> monitorBeanList = new ArrayList<>();

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public List<MonitorBean> getMonitorBeanList() {
        return monitorBeanList;
    }

    public void setMonitorBeanList(List<MonitorBean> monitorBeanList) {
        this.monitorBeanList = monitorBeanList;
    }
}
