package servlet;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.PropertyValues;
import org.springframework.core.io.ResourceLoader;

import javax.servlet.ServletException;

public class HttpServletBean {
    public final void init() throws ServletException {
        if (logger.isDebugEnabled()) {
            logger.debug("Initializing servlet '" + getServletName() + "'");
        }

        try {
//将Servlet中配置的参数封装到pvs变量中，requiredProperties为必需参数，如果没配置将报异常
            PropertyValues pvs = new ServletConfigPropertyValues(getServletConfig(), this.requiredProperties);
            BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(this);
            ResourceLoader resourceLoader = new ServletContextResourceLoader(getServletContext());
            bw.registerCustomEditor(Resource.class, new esourceEditor(resourceLoader, getEnvironment()));
            //模板方法，可以在子类调用，做一些初始化工作。bw代表DispatcherServlet
            initBeanWrapper(bw);
            //将配置的初始化值（如contextConfigLocation）设置到DispatcherServlet
            bw.setPropertyValues(pvs, true);
        } catch (BeansException ex) {
            logger.error("Failed to set bean properties on servlet '" + getServletName() + "'", ex);
            throw ex;
        }

        // 模板方法，子类初始化的入口方法
        initServletBean();

        if (logger.isDebugEnabled()) {
            logger.debug("Servlet '" + getServletName() + "' configured successfully");
        }

    }
}
