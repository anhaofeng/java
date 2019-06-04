package servlet.handlerAdapter;

import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.WebContentGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractHandlerMethodAdapter extends WebContentGenerator implements HandlerAdapter, Ordered {
    private int order = Ordered.LOWEST_PRECEDENCE;
    public AbstractHandlerMethodAdapter() {
        // no restriction of HTTP methods by default
        super(false);
    }
    public void setOrder(int order) {
        this.order = order;
    }
    @Override
    public int getOrder() {
        return this.order;
    }
    @Override
    public final boolean supports(Object handler) {
        return (handler instanceof HandlerMethod && supportsInternal((HandlerMethod) handler));
    }
    protected abstract boolean supportsInternal(HandlerMethod handlerMethod);
    @Override
    public final ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return handleInternal(request, response, (HandlerMethod) handler);
    }
    protected abstract ModelAndView handleInternal(HttpServletRequest request,
                                                   HttpServletResponse response, HandlerMethod handlerMethod) throws Exception;
    @Override
    public final long getLastModified(HttpServletRequest request, Object handler) {
        return getLastModifiedInternal(request, (HandlerMethod) handler);
    }
    protected abstract long getLastModifiedInternal(HttpServletRequest request, HandlerMethod handlerMethod);
}

