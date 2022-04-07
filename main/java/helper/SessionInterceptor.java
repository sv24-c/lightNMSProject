package helper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.StrutsStatics;
import org.omg.CORBA.*;
import org.omg.CORBA.Object;
import org.omg.PortableInterceptor.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by smit on 2/4/22.
 */
public class SessionInterceptor implements Interceptor
{

    private String[] allowedURLs = {"/login.jsp"};

    @Override
    public String name() {
        return null;
    }

    @Override
    public void destroy() {

    }

   /* @Override
    public void init()
    {

    }*/

    //@Override
    public String intercept(ActionInvocation actionInvocation) throws Exception
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

        } else
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

        return ActionSupport.SUCCESS;
    }



    @Override
    public boolean _is_a(String repositoryIdentifier) {
        return false;
    }

    @Override
    public boolean _is_equivalent(Object other) {
        return false;
    }

    @Override
    public boolean _non_existent() {
        return false;
    }

    @Override
    public int _hash(int maximum) {
        return 0;
    }

    @Override
    public Object _duplicate() {
        return null;
    }

    @Override
    public void _release() {

    }

    @Override
    public Object _get_interface_def() {
        return null;
    }

    @Override
    public Request _request(String operation) {
        return null;
    }

    @Override
    public Request _create_request(Context ctx, String operation, NVList arg_list, NamedValue result) {
        return null;
    }

    @Override
    public Request _create_request(Context ctx, String operation, NVList arg_list, NamedValue result, ExceptionList exclist, ContextList ctxlist) {
        return null;
    }

    @Override
    public Policy _get_policy(int policy_type) {
        return null;
    }

    @Override
    public DomainManager[] _get_domain_managers() {
        return new DomainManager[0];
    }

    @Override
    public Object _set_policy_override(Policy[] policies, SetOverrideType set_add) {
        return null;
    }
}
