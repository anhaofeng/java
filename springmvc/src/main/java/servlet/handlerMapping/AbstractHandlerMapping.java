package servlet.handlerMapping;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.MappedInterceptor;

import javax.servlet.http.HttpServletRequest;

public class AbstractHandlerMapping extends HandlerMapping {
    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest httpServletRequest) throws Exception {
        return null;
    }
    protected void initApplicationContext() throws BeansException {
        extendInterceptors(this.interceptors);
        detectMappedInterceptors(this.mappedInterceptors);
        initInterceptors();
    }
    protected void detectMappedInterceptors(List<MappedInterceptor> mappedInterceptors) {
        mappedInterceptors.addAll(
                BeanFactoryUtils.beansOfTypeIncludingAncestors(
                        getApplicationContext(), MappedInterceptor.class, true, false).values());
    }
    protected void initInterceptors() {
        if (!this.interceptors.isEmpty()) {
            for (int i = 0; i < this.interceptors.size(); i++) {
                Object interceptor = this.interceptors.get(i);
                if (interceptor == null) {
                    throw new IllegalArgumentException("Entry number " + i + " in interceptors array is null");
                }
                if (interceptor instanceof MappedInterceptor) {
                    this.mappedInterceptors.add((MappedInterceptor) interceptor);
                }else {
                    this.adaptedInterceptors.add(adaptInterceptor(interceptor));
                }
            }
        }
    }
    public final HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        Object handler = getHandlerInternal(request);
        if (handler == null) {
            handler = getDefaultHandler();
        }
        if (handler == null) {
            return null;
        }
        // Bean name or resolved handler?
        if (handler instanceof String) {
            String handlerName = (String) handler;
            handler = getApplicationContext().getBean(handlerName);
        }
        return getHandlerExecutionChain(handler, request);
    }


    protected HandlerExecutionChain getHandlerExecutionChain(Object handler, HttpServletRequest request) {
        HandlerExecutionChain chain = (handler instanceof HandlerExecutionChain ?
                (HandlerExecutionChain) handler : new HandlerExecutionChain(handler));
        chain.addInterceptors(getAdaptedInterceptors());

        String lookupPath = this.urlPathHelper.getLookupPathForRequest(request);
        for (MappedInterceptor mappedInterceptor : this.mappedInterceptors) {
            if (mappedInterceptor.matches(lookupPath, this.pathMatcher)) {
                chain.addInterceptor(mappedInterceptor.getInterceptor());
            }
        }
        return chain;
    }

}
