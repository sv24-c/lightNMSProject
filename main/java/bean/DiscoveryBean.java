package bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smit on 14/3/22.
 */
public class DiscoveryBean
{
    private String name;
    private String IP;
    private String type;
    private String username;
    private String password;
    private int id;

    String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private List<DiscoveryBean> discoveryBeanList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String ip) {
        this.IP = ip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public boolean setUsername(String username) {
        this.username = username;
        return true;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DiscoveryBean> getDiscoveryBeanList() {
        return discoveryBeanList;
    }

    public void setDiscoveryBeanList(List<DiscoveryBean> discoveryBeanList) {
        this.discoveryBeanList = discoveryBeanList;
    }
}
