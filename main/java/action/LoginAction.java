package action;

import bean.LoginBean;
import com.opensymphony.xwork2.ModelDriven;
import executor.LoginExecutor;
import helper.Logger;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.Map;

public class LoginAction implements SessionAware, ModelDriven
{
    private LoginBean loginBean = new LoginBean();

    private LoginExecutor loginExecutor = new LoginExecutor();

    private static Logger _logger = new Logger();

    private DateTimeFormatter timeStamp = DateTimeFormat.forPattern("dd-MMMM-yyyy_HH:mm:ss");

    private SessionMap<String, Object> sessionMain;

    public String login()
    {
        try
        {
            if (loginExecutor.logIn(loginBean.getUserName(), loginBean.getPassword()))
            {
                loginBean.setStatus("Success");

                sessionMain.put("user", loginBean.getUserName());

                _logger.info("successfully login...at "+timeStamp);
            }
        }
        catch (Exception exception)
        {
            _logger.error("LoginAction logIn method having error. at "+timeStamp, exception);
        }

        return "success";
    }

    public String logout()
    {
        sessionMain.invalidate();

        return "success";
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.sessionMain = (SessionMap<String, Object>) map;
    }

    @Override
    public Object getModel()
    {
        return loginBean;
    }
}

