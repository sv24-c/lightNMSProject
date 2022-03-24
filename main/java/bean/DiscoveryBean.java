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
    String Username;
    String Password;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public void setIP(String ip) {
        this.IP = ip;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public List<DiscoveryBean> getDiscoveryBeanList() {
        return discoveryBeanList;
    }

    public void setDiscoveryBeanList(List<DiscoveryBean> discoveryBeanList) {
        this.discoveryBeanList = discoveryBeanList;
    }
}
