package servlet.handlerAdapter;

import javassist.util.proxy.MethodFilter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
/*
argumentResolvers、initBinderArgumentResolvers、returnValueHandlers
以及@ControllerAdvice注释的类相关的modelAttributeAdviceCache、initBinderAdviceCache和responseBodyAdvice
 */

//RequestMappingHandlerAdapter的创建在afterPropertiesSet方法中实现
public class RequestMappingHandlerAdapter extends  AbstractHandlerMethodAdapter {
    @Override
    protected boolean supportsInternal(HandlerMethod handlerMethod) {
        return false;
    }

    @Override
    protected ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        return null;
    }

    @Override
    protected long getLastModifiedInternal(HttpServletRequest request, HandlerMethod handlerMethod) {
        return 0;
    }
    public void afterPropertiesSet() {
        // 初始化注释了@ControllerAdvice的类的相关属性
        initControllerAdviceCache();

        if (this.argumentResolvers == null) {
            List<HandlerMethodArgumentResolver> resolvers = getDefaultArgumentResolvers();
            this.argumentResolvers = new HandlerMethodArgumentResolverComposite().addResolvers(resolvers);
        }
        if (this.initBinderArgumentResolvers == null) {
            List<HandlerMethodArgumentResolver> resolvers = getDefaultInitBinderArgumentResolvers();
            this.initBinderArgumentResolvers = new HandlerMethodArgumentResolverComposite().addResolvers(resolvers);
        }
        if (this.returnValueHandlers == null) {
            List<HandlerMethodReturnValueHandler> handlers = getDefaultReturnValueHandlers();
            this.returnValueHandlers = new HandlerMethodReturnValueHandlerComposite().addHandlers(handlers);
        }
    }
    private void initControllerAdviceCache() {
        if (getApplicationContext() == null) {
            return;
        }
        if (logger.isInfoEnabled()) {
            logger.info("Looking for @ControllerAdvice: " + getApplicationContext());
        }
        // 获取到所有注释了@ControllerAdvice的bean
        List<ControllerAdviceBean> beans = ControllerAdviceBean.findAnnotatedBeans(getApplicationContext());
        // 根据Order排序
        Collections.sort(beans, new OrderComparator());

        List<Object> responseBodyAdviceBeans = new ArrayList<Object>();

        for (ControllerAdviceBean bean : beans) {
            // 查找注释了@ModelAttribute而且没注释@ RequestMapping的方法
            Set<Method> attrMethods = HandlerMethodSelector.selectMethods(bean.getBeanType(), MODEL_ATTRIBUTE_METHODS);
            if (!attrMethods.isEmpty()) {
                this.modelAttributeAdviceCache.put(bean, attrMethods);
                logger.info("Detected @ModelAttribute methods in " + bean);
            }
            // 查找注释了@InitBinder的方法
            Set<Method> binderMethods = HandlerMethodSelector.selectMethods(bean.getBeanType(), INIT_BINDER_METHODS);
            if (!binderMethods.isEmpty()) {
                this.initBinderAdviceCache.put(bean, binderMethods);
                logger.info("Detected @InitBinder methods in " + bean);
            }
            // 查找实现了ResponseBodyAdvice接口的类
            if (ResponseBodyAdvice.class.isAssignableFrom(bean.getBeanType())) {
                responseBodyAdviceBeans.add(bean);
                logger.info("Detected ResponseBodyAdvice bean in " + bean);
            }
        }
        // 将查找到的实现了ResponseBodyAdvice接口的类从前面添加到responseBodyAdvice属性
        if (!responseBodyAdviceBeans.isEmpty()) {
            this.responseBodyAdvice.addAll(0, responseBodyAdviceBeans);
        }
    }


    public static final MethodFilter INIT_BINDER_METHODS = new MethodFilter() {
        @Override
        public boolean matches(Method method) {
            return AnnotationUtils.findAnnotation(method, InitBinder.class) != null;
        }
    };

    public static final MethodFilter MODEL_ATTRIBUTE_METHODS = new MethodFilter() {
        @Override
        public boolean matches(Method method) {
            return ((AnnotationUtils.findAnnotation(method,RequestMapping.class)== null) &&
                    (AnnotationUtils.findAnnotation(method,ModelAttribute.class)!= null));
        }
    };


}
