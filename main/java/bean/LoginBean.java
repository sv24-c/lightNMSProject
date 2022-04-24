package bean;

/**
 * Created by smit on 15/4/22.
 */
public class LoginBean
{
    private String userName = null;

    private String password = null;

    private String status = null;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
