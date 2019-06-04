package servlet.handlerMapping;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContextException;

public abstract class AbstractDetectingUrlHandlerMapping extends AbstractUrlHandlerMapping {
    private boolean detectHandlersInAncestorContexts = false;
    public void setDetectHandlersInAncestorContexts(boolean detectHandlersInAncestorContexts) {
        this.detectHandlersInAncestorContexts = detectHandlersInAncestorContexts;
    }
    @Override
    public void initApplicationContext() throws ApplicationContextException {
        super.initApplicationContext();
        detectHandlers();
    }
    protected void detectHandlers() throws BeansException {
        if (logger.isDebugEnabled()) {
            logger.debug("Looking for URL mappings in application context: " + getApplicationContext());
        }
//获取容器的所有bean的名字
        String[] beanNames = (this.detectHandlersInAncestorContexts ?
                BeanFactoryUtils.beanNamesForTypeIncludingAncestors(getApplicationContext(), Object.class) :
                getApplicationContext().getBeanNamesForType(Object.class));
        //对每个beanName解析url，如果能解析到就注册到父类的Map中
        for (String beanName : beanNames) {
// 使用beanName解析url，是模板方法，子类具体实现
            String[] urls = determineUrlsForHandler(beanName);
// 如果能解析到url则注册到父类
            if (!ObjectUtils.isEmpty(urls)) {
// 父类的registerHandler方法
                registerHandler(urls, beanName);
            }else {
                if (logger.isDebugEnabled()) {
                    logger.debug("Rejected bean name '" + beanName + "': no URL paths identified");
                }
            }
        }
    }
    protected abstract String[] determineUrlsForHandler(String beanName);
}
