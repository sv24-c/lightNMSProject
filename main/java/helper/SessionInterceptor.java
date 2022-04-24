package helper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.struts2.StrutsStatics;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by smit on 2/4/22.
 */

public class SessionInterceptor implements Interceptor
{
    private static final Logger _logger = new Logger();

    private String[] allowedURLs = {"/login.jsp"};

    public String name() {
        return null;
    }

    @Override
    public void destroy()
    {

    }

    @Override
    public void init()
    {

    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception
    {
        try
        {

            final ActionContext context = actionInvocation.getInvocationContext();

            HttpServletRequest request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);

            HttpServletResponse response = (HttpServletResponse) context.get(StrutsStatics.HTTP_RESPONSE);

            if (response != null)
            {
                response.setHeader("Cache-control", "no-cache, no-store");

                response.setHeader("Pragma", "no-cache");

                response.setHeader("Expires", "-1");
            }

            Map<String, java.lang.Object> session = ActionContext.getContext().getSession();

            boolean userActionQualified = false;

            if (session.get("user") != null)
            {
                actionInvocation.invoke();

                userActionQualified = true;
            }
            else
            {
                String requestURL = request.getRequestURI();

                for (String url : allowedURLs)
                {
                    if (requestURL.contains(url))
                    {
                        actionInvocation.invoke();

                        userActionQualified = true;
                    }
                }
            }

            if (!userActionQualified)
            {
                return "loginUser";
            }
        }
        catch (Exception exception)
        {
            _logger.error("SessionInterceptor class intercept method having error", exception);
        }

            return ActionSupport.SUCCESS;
    }
}
