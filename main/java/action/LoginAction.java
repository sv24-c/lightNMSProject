package action;

import bean.LoginBean;
import com.opensymphony.xwork2.ModelDriven;
import executor.LoginExecutor;
import helper.Logger;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public class LoginAction implements SessionAware, ModelDriven
{
    private LoginBean loginBean = new LoginBean();

    private LoginExecutor loginExecutor = new LoginExecutor();

    private static Logger _logger = new Logger();

    //not thread safe
    private String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());

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

