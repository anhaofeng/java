package servlet.handlerMapping;

import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

public class AbstractUrlHandlerMapping extends HandlerMapping{
    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest httpServletRequest) throws Exception {
        return null;
    }
    private Object rootHandler;
    private final Map<String, Object> handlerMap = new LinkedHashMap<String, Object>();
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
        String lookupPath = getUrlPathHelper().getLookupPathForRequest(request);
        Object handler = lookupHandler(lookupPath, request);
        if (handler == null) {
            // 定义一个临时变量，保存找到的原始Handler
            Object rawHandler = null;
            if ("/".equals(lookupPath)) {
                rawHandler = getRootHandler();
            }
            if (rawHandler == null) {
                rawHandler = getDefaultHandler();
            }
            if (rawHandler != null) {
                // 如果是String类型则到容器中查找具体的Bean
                if (rawHandler instanceof String) {
                    String handlerName = (String) rawHandler;
                    rawHandler = getApplicationContext().getBean(handlerName);
                }
                // 可以用来校验找到的Handler和request是否匹配，是模板方法，而且子类也没有使用
                validateHandler(rawHandler, request);
                handler = buildPathExposingHandler(rawHandler, lookupPath, lookupPath, null);
            }
        }
        if (handler != null && logger.isDebugEnabled()) {
            logger.debug("Mapping [" + lookupPath + "] to " + handler);
        }else if (handler == null && logger.isTraceEnabled()) {
            logger.trace("No handler mapping found for [" + lookupPath + "]");
        }
        return handler;
    }
    protected Object lookupHandler(String urlPath, HttpServletRequest request) throws Exception {
        // 直接从Map中获取
        Object handler = this.handlerMap.get(urlPath);
        if (handler != null) {
            // 如果是String类型则从容器中获取
            if (handler instanceof String) {
                String handlerName = (String) handler;
                handler = getApplicationContext().getBean(handlerName);
            }
            validateHandler(handler, request);
            return buildPathExposingHandler(handler, urlPath, urlPath, null);
        }
        // Pattern匹配，比如使用带*号的模式与url进行匹配
        List<String> matchingPatterns = new ArrayList<String>();
        for (String registeredPattern : this.handlerMap.keySet()) {
            if (getPathMatcher().match(registeredPattern, urlPath)) {
                matchingPatterns.add(registeredPattern);
            }
        }
        String bestPatternMatch = null;
        Comparator<String> patternComparator = getPathMatcher().getPatternComparator(urlPath);
        if (!matchingPatterns.isEmpty()) {
            Collections.sort(matchingPatterns, patternComparator);
            if (logger.isDebugEnabled()) {
                logger.debug("Matching patterns for request [" + urlPath + "] are " + matchingPatterns);
            }
            bestPatternMatch = matchingPatterns.get(0);
        }
        if (bestPatternMatch != null) {
            handler = this.handlerMap.get(bestPatternMatch);
            //如果是String类型则从容器中获取
            if (handler instanceof String) {
                String handlerName = (String) handler;
                handler = getApplicationContext().getBean(handlerName);
            }
            validateHandler(handler, request);
            String pathWithinMapping = getPathMatcher().extractPathWithinPattern(bestPatternMatch, urlPath);
            // 之前是通过sort方法进行排序，然后拿第一个作为bestPatternMatch的，不过有可能有多个Pattern的顺序相同，也就是sort方法返回0，这里就是处理这种情况
            Map<String, String> uriTemplateVariables = new LinkedHashMap<String, String>();
            for (String matchingPattern : matchingPatterns) {
                if (patternComparator.compare(bestPatternMatch, matchingPattern) == 0) {
                    Map<String, String> vars = getPathMatcher().extractUriTemplateVariables(matchingPattern, urlPath);
                    Map<String, String> decodedVars = getUrlPathHelper().decodePathVariables (request, vars);
                    uriTemplateVariables.putAll(decodedVars);
                }
            }
            if (logger.isDebugEnabled()) {
                logger.debug("URI Template variables for request [" + urlPath + "] are " + uriTemplateVariables);
            }
            return buildPathExposingHandler(handler, bestPatternMatch, pathWithinMapping, uriTemplateVariables);
        }
        // 没找到Handler则返回null
        return null;
    }
    protected Object buildPathExposingHandler(Object rawHandler, String bestMatchingPattern,
                                              String pathWithinMapping, Map<String, String> uriTemplateVariables) {

        HandlerExecutionChain chain = new HandlerExecutionChain(rawHandler);
        chain.addInterceptor(new PathExposingHandlerInterceptor(bestMatchingPattern, pathWithinMapping));
        if (!CollectionUtils.isEmpty(uriTemplateVariables)) {
            chain.addInterceptor(new UriTemplateVariablesHandlerInterceptor(uriTemplateVariables));
        }
        return chain;
    }

    public void initApplicationContext() {
    }

    private class PathExposingHandlerInterceptor extends HandlerInterceptorAdapter {
        private final String bestMatchingPattern;
        private final String pathWithinMapping;
        public PathExposingHandlerInterceptor(String bestMatchingPattern, String pathWithinMapping) {
            this.bestMatchingPattern = bestMatchingPattern;
            this.pathWithinMapping = pathWithinMapping;
        }
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            exposePathWithinMapping(this.bestMatchingPattern, this.pathWithinMapping, request);
            request.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING, supportsTypeLevelMappings());
            return true;
        }

    }
    protected void exposePathWithinMapping(String bestMatchingPattern, String pathWithinMapping, HttpServletRequest request) {
        request.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE, bestMatchingPattern);
        request.setAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, pathWithinMapping);
    }

    private class UriTemplateVariablesHandlerInterceptor extends HandlerInterceptorAdapter {
        private final Map<String, String> uriTemplateVariables;
        public UriTemplateVariablesHandlerInterceptor(Map<String, String> uriTemplateVariables) {
            this.uriTemplateVariables = uriTemplateVariables;
        }
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            exposeUriTemplateVariables(this.uriTemplateVariables, request);
            return true;
        }
    }
    protected void exposeUriTemplateVariables(Map<String, String> uriTemplateVariables, HttpServletRequest request) {
        request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, uriTemplateVariables);
    }
    protected void registerHandler(String[] urlPaths, String beanName) throws BeansException, IllegalStateException {
        Assert.notNull(urlPaths, "URL path array must not be null");
        for (String urlPath : urlPaths) {
            registerHandler(urlPath, beanName);
        }
    }
    protected void registerHandler(String urlPath, Object handler) throws BeansException, IllegalStateException {
        Assert.notNull(urlPath, "URL path must not be null");
        Assert.notNull(handler, "Handler object must not be null");
        Object resolvedHandler = handler;

        // 如果Handler是String类型而且没有设置lazyInitHandlers则从SpringMVC容器中获取Handler
        if (!this.lazyInitHandlers && handler instanceof String) {
            String handlerName = (String) handler;
            if (getApplicationContext().isSingleton(handlerName)) {
                resolvedHandler = getApplicationContext().getBean(handlerName);
            }
        }

        Object mappedHandler = this.handlerMap.get(urlPath);
        if (mappedHandler != null) {
            if (mappedHandler != resolvedHandler) {
                throw new IllegalStateException(
                        "Cannot map " + getHandlerDescription(handler) + " to URL path [" + urlPath +
                                "]: There is already " + getHandlerDescription(mappedHandler) + " mapped.");
            }
        }else {
            if (urlPath.equals("/")) {
                if (logger.isInfoEnabled()) {
                    logger.info("Root mapping to " + getHandlerDescription(handler));
                }
                setRootHandler(resolvedHandler);
            }else if (urlPath.equals("/*")) {
                if (logger.isInfoEnabled()) {
                    logger.info("Default mapping to " + getHandlerDescription(handler));
                }
                setDefaultHandler(resolvedHandler);
            }else {
                this.handlerMap.put(urlPath, resolvedHandler);
                if (logger.isInfoEnabled()) {
                    logger.info("Mapped URL path [" + urlPath + "] onto " + getHandlerDescription(handler));
                }
            }
        }
    }


}
