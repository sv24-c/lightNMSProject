package bean;

import executor.DiscoveryExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smit on 14/3/22.
 */
public class DiscoveryBean
{
    String Name;
    String IP;
    String Type;

    List<DiscoveryBean> discoveryBeanList = new ArrayList<>();

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }



    public List<DiscoveryBean> getDiscoveryBeanList() {
        return discoveryBeanList;
    }

    public void setDiscoveryBeanList(List<DiscoveryBean> discoveryBeanList) {
        this.discoveryBeanList = discoveryBeanList;
    }


}
