package servlet;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;


public class DispatcherServlet {
    private static final Properties defaultStrategies;

    static {
        try {
            ClassPathResource resource = new ClassPathResource(DEFAULT_STRATEGIES_PATH, DispatcherServlet.class);
            defaultStrategies = PropertiesLoaderUtils.loadProperties(resource);
        }catch (IOException ex) {
            throw new IllegalStateException("Could not load 'DispatcherServlet.properties': " + ex.getMessage());
        }
    }

    protected void onRefresh(ApplicationContext context) {
        initStrategies(context);
    }

    protected void initStrategies(ApplicationContext context) {
        initMultipartResolver(context);
        initLocaleResolver(context);
        initThemeResolver(context);
        initHandlerMappings(context);
        initHandlerAdapters(context);
        initHandlerExceptionResolvers(context);
        initRequestToViewNameTranslator(context);
        initViewResolvers(context);
        initFlashMapManager(context);
    }
    private void initLocaleResolver(ApplicationContext context) {
        try {
            // 在context中获取
            this.localeResolver = context.getBean(LOCALE_RESOLVER_BEAN_NAME, LocaleResolver.class);
            if (logger.isDebugEnabled()) {
                logger.debug("Using LocaleResolver [" + this.localeResolver + "]");
            }
        }catch (NoSuchBeanDefinitionException ex) {
            // 使用默认策略
            this.localeResolver = getDefaultStrategy(context, LocaleResolver.class);
            if (logger.isDebugEnabled()) {
                logger.debug("Unable to locate LocaleResolver with name '" + LOCALE_RESOLVER_BEAN_NAME +
                        "': using default [" + this.localeResolver + "]");
            }
        }
    }
    protected<T> T getDefaultStrategy(ApplicationContext context, Class<T> strategyInterface) {
        List<T> strategies = getDefaultStrategies(context, strategyInterface);
        if (strategies.size() != 1) {
            throw new BeanInitializationException(
                    "DispatcherServlet needs exactly 1 strategy for interface [" + strategyInterface.getName() + "]");
        }
        return strategies.get(0);
    }
    protected <T> List<T> getDefaultStrategies(ApplicationContext context, Class<T> strategyInterface) {
        String key = strategyInterface.getName();
        //从defaultStrategies获取所需策略的类型
        String value = defaultStrategies.getProperty(key);
        if (value != null) {
            //如果有多个默认值，以逗号分割为数组
            String[] classNames = StringUtils.commaDelimitedListToStringArray(value);
            List<T> strategies = new ArrayList<T>(classNames.length);
            //按获取到的类型初始化策略
            for (String className : classNames) {
                try {
                    Class<?> clazz = ClassUtils.forName(className, DispatcherServlet.class.getClassLoader());
                    Object strategy = createDefaultStrategy(context, clazz);
                    strategies.add((T) strategy);
                }catch (ClassNotFoundException ex) {
                    throw new BeanInitializationException(
                            "Could not find DispatcherServlet's default strategy class [" + className +
                                    "] for interface [" + key + "]", ex);
                }catch (LinkageError err) {
                    throw new BeanInitializationException(
                            "Error loading DispatcherServlet's default strategy class [" + className +
                                    "] for interface [" + key + "]: problem with class file or dependent class", err);
                }
            }
            return strategies;
        }else {
            return new LinkedList<T>();
        }
    }



}
