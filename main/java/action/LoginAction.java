package action;

import executor.LoginExecutor;


public class LoginAction
{
    private String userName;

    private String password;

    private LoginExecutor loginExecutor = new LoginExecutor();

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
        try
        {

            if (loginExecutor.login(userName, password))
            {
                return "success";
            }

            else
            {
                return "failure";
            }
//        return "success";
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return "failure";
    }

}

