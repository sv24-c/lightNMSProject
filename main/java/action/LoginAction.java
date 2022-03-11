package action;


import dao.LoginDao;
import executor.LoginExecutor;

import static sun.management.Agent.getText;

public class LoginAction
{
    String userName;
    String password;

    LoginExecutor loginExecutor = new LoginExecutor();

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String login()
    {
        if(loginExecutor.login(userName, password))
        {
            return "success";
        }
        else{
            return "failure";
        }
    }
}

