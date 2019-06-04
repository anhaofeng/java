package servlet.handlerMapping;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.method.HandlerMethod;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractHandlerMethodMapping<T> extends AbstractHandlerMapping implements InitializingBean {
    private final Map<T, HandlerMethod> handlerMethods = new LinkedHashMap<T, HandlerMethod>();
    private final MultiValueMap<String, T> urlMap = new LinkedMultiValueMap<String, T>();
    private final MultiValueMap<String, HandlerMethod> nameMap = new LinkedMultiValueMap<String, HandlerMethod>();
}
