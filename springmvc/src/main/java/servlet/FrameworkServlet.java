package servlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrameworkServlet {
    protected final void initServletBean() throws ServletException {
        getServletContext().log("Initializing Spring FrameworkServlet '" + getServletName() + "'");
        if (this.logger.isInfoEnabled()) {
            this.logger.info("FrameworkServlet '" + getServletName() + "': initialization started");
        }
        long startTime = System.currentTimeMillis();
        try {
            this.webApplicationContext = initWebApplicationContext();
            initFrameworkServlet();
        }catch (ServletException ex) {
            this.logger.error("Context initialization failed", ex);
            throw ex;
        }catch (RuntimeException ex) {
            this.logger.error("Context initialization failed", ex);
            throw ex;
        }
        if (this.logger.isInfoEnabled()) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            this.logger.info("FrameworkServlet '" + getServletName() + "':
                    initialization completed in " +
                    elapsedTime + " ms");
        }
    }


    protected WebApplicationContext initWebApplicationContext() {
//获取rootContext
        WebApplicationContext rootContext =
                WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        WebApplicationContext wac = null;
        //如果已经通过构造方法设置了webApplicationContext
        if (this.webApplicationContext != null) {
            wac = this.webApplicationContext;
            if (wac instanceof ConfigurableWebApplicationContext) {
                ConfigurableWebApplicationContext cwac = (ConfigurableWebApplicationContext) wac;
                if (!cwac.isActive()) {
                    if (cwac.getParent() == null) {
                        cwac.setParent(rootContext);
                    }
                    configureAndRefreshWebApplicationContext(cwac);
                }
            }
        }
        if (wac == null) {
            // 当webApplicationContext已经存在ServletContext中时，通过配置在Servlet中的contextAttribute参数获取
            wac = findWebApplicationContext();
        }
        if (wac == null) {
            // 如果webApplicationContext还没有创建，则创建一个
            wac = createWebApplicationContext(rootContext);
        }

        if (!this.refreshEventReceived) {
            // 当ContextRefreshedEvent事件没有触发时调用此方法，模板方法，可以在子类重写
            onRefresh(wac);
        }

        if (this.publishContext) {
            // 将ApplicationContext保存到ServletContext中
            String attrName = getServletContextAttributeName();
            getServletContext().setAttribute(attrName, wac);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Published WebApplicationContext of servlet '" + getServletName() +
                        "' as ServletContext attribute with name [" + attrName + "]");
            }
        }
        return wac;
    }
    protected WebApplicationContext createWebApplicationContext(ApplicationContext parent) {
        //获取创建类型
        Class<?> contextClass = getContextClass();
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Servlet with name '" + getServletName() +
                    "' will try to create custom WebApplicationContext context of class '" +
                    contextClass.getName() + "'" + ", using parent context [" + parent + "]");
        }
//检查创建类型
        if (!ConfigurableWebApplicationContext.class.isAssignableFrom(contextClass)) {
            throw new ApplicationContextException(
                    "Fatal initialization error in servlet with name '" + getServletName() +
                            "': custom WebApplicationContext class [" + contextClass.getName() +
                            "] is not of type ConfigurableWebApplicationContext");
        }

//具体创建
        ConfigurableWebApplicationContext wac =
                (ConfigurableWebApplicationContext) BeanUtils.instantiateClass(contextClass);

        wac.setEnvironment(getEnvironment());
        wac.setParent(parent);
//将设置的contextConfigLocation参数传给wac，默认传入WEB-INFO/[ServletName]-Servlet.xml
        wac.setConfigLocation(getContextConfigLocation());

        configureAndRefreshWebApplicationContext(wac);

        return wac;
    }

    protected void configureAndRefreshWebApplicationContext(ConfigurableWebApplicationContext wac) {
        if (ObjectUtils.identityToString(wac).equals(wac.getId())) {
            // The application context id is still set to its original default value
            // -> assign a more useful id based on available information
            if (this.contextId != null) {
                wac.setId(this.contextId);
            }else {
                wac.setId(ConfigurableWebApplicationContext.APPLICATION_CONTEXT_ID_PREFIX +
                        ObjectUtils.getDisplayString(getServletContext().getContextPath()) + "/" + getServletName());
            }
        }

        wac.setServletContext(getServletContext());
        wac.setServletConfig(getServletConfig());
        wac.setNamespace(getNamespace());
        //添加监听ContextRefreshedEvent的监听器
        wac.addApplicationListener(new SourceFilteringListener(wac, new ContextRefreshListener()));

        // The wac environment's #initPropertySources will be called in any case when the context
        // is refreshed; do it eagerly here to ensure servlet property sources are in place for
        // use in any post-processing or initialization that occurs below prior to #refresh
        ConfigurableEnvironment env = wac.getEnvironment();
        if (env instanceof ConfigurableWebEnvironment) {
            ((ConfigurableWebEnvironment) env).initPropertySources(getServletContext(), getServletConfig());
        }

        postProcessWebApplicationContext(wac);
        applyInitializers(wac);
        wac.refresh();
    }
    //************************************************************************************
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getMethod();
        if (method.equalsIgnoreCase(RequestMethod.PATCH.name())) {
            processRequest(request, response);
        }else {
            super.service(request, response);
        }
    }
    protected final void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    //************************************************************************************
    protected final void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        Throwable failureCause = null;
        // 获取LocaleContextHolder中原来保存的LocaleContext
        LocaleContext previousLocaleContext = LocaleContextHolder.getLocaleContext();
        // 获取当前请求的LocaleContext
        LocaleContext localeContext = buildLocaleContext(request);
        // 获取RequestContextHolder中原来保存的RequestAttributes
        RequestAttributes previousAttributes = RequestContextHolder.getRequestAttributes();
        //获取当前请求的ServletRequestAttributes
        ServletRequestAttributes requestAttributes = buildRequestAttributes(request, response, previousAttributes);

        WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);
        asyncManager.registerCallableInterceptor(FrameworkServlet.class.getName(), new RequestBindingInterceptor());
        //将当前请求的LocaleContext和ServletRequestAttributes设置到LocaleContextHolder和RequestContextHolder
        initContextHolders(request, localeContext, requestAttributes);

        try {
            //实际处理请求入口
            doService(request, response);
        }catch (ServletException ex) {
            failureCause = ex;
            throw ex;
        }catch (IOException ex) {
            failureCause = ex;
            throw ex;
        }catch (Throwable ex) {
            failureCause = ex;
            throw new NestedServletException("Request processing failed", ex);
        }finally {
            //恢复原来的LocaleContext和ServletRequestAttributes到LocaleContextHolder和RequestContextHolder中
            resetContextHolders(request, previousLocaleContext, previousAttributes);
            if (requestAttributes != null) {
                requestAttributes.requestCompleted();
            }
            //省略了log代码
            //发布ServletRequestHandledEvent消息
            publishRequestHandledEvent(request, response, startTime, failureCause);
        }
    }



}
