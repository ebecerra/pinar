package com.vincomobile.interceptors;

import com.befasoft.common.actions.NeedAuthentificate;
import com.befasoft.common.actions.UsuariosAction;
import com.befasoft.common.beans.LOGIN_INFO;
import com.befasoft.common.tools.HibernateUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.vincomobile.actions.IndexAction;
import com.vincomobile.actions.LoginAction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class WebInterceptor implements Interceptor {

    private static Log log = LogFactory.getLog(WebInterceptor.class);

    public void destroy() {
        log.debug("destroy()");
    }

    public void init() {
        log.debug("init()");
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        try {
            Map session = actionInvocation.getInvocationContext().getSession();
            LOGIN_INFO user = (LOGIN_INFO) session.get("user");
            MDC.put("admin", user != null ? user.getLogin() : "N/A");
            HttpServletRequest request = ServletActionContext.getRequest();
            MDC.put("ip", request.getRemoteHost());
            if (user == null) {
                log.debug("intercept(<no user>)");
                Object action = actionInvocation.getAction();

                // Si no esta autentificado el usuario y es una accion que lo necesite entonces se retorna error.
                if (action instanceof NeedAuthentificate && ((NeedAuthentificate) action).needAuthetificated()) {
                    if (action instanceof LoginAction || action instanceof IndexAction || action instanceof UsuariosAction) {
                        return actionInvocation.invoke();
                    }
                    return "index";
                }
            }
            return actionInvocation.invoke();
        }
        catch (Throwable e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        finally {
            HibernateUtil.closeSession();
        }
    }

}