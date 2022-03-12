package action;

import executor.DiscoveryExecutor;

/**
 * Created by smit on 12/3/22.
 */
public class DiscoveryAction
{
    String Name;
    String IP;
    String Type;

    DiscoveryExecutor discoveryExecutor = new DiscoveryExecutor();

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

    public String discovery()
    {

        try
        {

            if (discoveryExecutor.discovery())
            {
                return "success";
            }
            else
            {
                return "failure";
            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return "failure";
    }
}
