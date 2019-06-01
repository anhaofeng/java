package xml;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.xml.BeanDefinitionDecorator;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.scheduling.config.AnnotationDrivenBeanDefinitionParser;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

public class MvcNamespaceHandler {
    public abstract class NamespaceHandlerSupport implements NamespaceHandler {
        private final Map<String, BeanDefinitionParser> parsers =
                new HashMap<String, BeanDefinitionParser>();
        private final Map<String, BeanDefinitionDecorator> decorators =
                new HashMap<String, BeanDefinitionDecorator>();
        private final Map<String, BeanDefinitionDecorator> attributeDecorators =
                new HashMap<String, BeanDefinitionDecorator>();

        @Override
        public BeanDefinition parse(Element element, ParserContext parserContext) {
            return findParserForElement(element, parserContext).parse(element, parserContext);
        }

        private BeanDefinitionParser findParserForElement(Element element, ParserContext parserContext) {
            String localName = parserContext.getDelegate().getLocalName(element);
            BeanDefinitionParser parser = this.parsers.get(localName);
            if (parser == null) {
                parserContext.getReaderContext().fatal(
                        "Cannot locate BeanDefinitionParser for element [" + localName + "]", element);
            }
            return parser;
        }
        @Override
        public BeanDefinitionHolder decorate(
                Node node, BeanDefinitionHolder definition, ParserContext parserContext) {

            return findDecoratorForNode(node, parserContext).decorate(node, definition, parserContext);
        }

        private BeanDefinitionDecorator findDecoratorForNode(Node node, ParserContext parserContext) {
            BeanDefinitionDecorator decorator = null;
            String localName = parserContext.getDelegate().getLocalName(node);
            //先判断是标签还是属性，然后再调用相应方法进行处理
            if (node instanceof Element) {
                decorator = this.decorators.get(localName);
            }else if (node instanceof Attr) {
                decorator = this.attributeDecorators.get(localName);
            }else {
                parserContext.getReaderContext().fatal(
                        "Cannot decorate based on Nodes of type [" + node.getClass().getName() + "]", node);
            }
            if (decorator == null) {
                parserContext.getReaderContext().fatal("Cannot locate BeanDefinitionDecorator for " +
                        (node instanceof Element ? "element" : "attribute") + " [" + localName + "]", node);
            }
            return decorator;
        }

        protected final void registerBeanDefinitionParser(String elementName, BeanDefinitionParser parser) {
            this.parsers.put(elementName, parser);
        }

        protected final void registerBeanDefinitionDecorator(String elementName, BeanDefinitionDecorator dec) {
            this.decorators.put(elementName, dec);
        }

        protected final void registerBeanDefinitionDecoratorForAttribute(String attrName, BeanDefinitionDecorator dec) {
            this.attributeDecorators.put(attrName, dec);
        }
    }

package org.springframework.web.servlet.config;
    // 省略了imports
    public class MvcNamespaceHandler extends NamespaceHandlerSupport {
        @Override
        public void init() {
            registerBeanDefinitionParser("annotation-driven", new AnnotationDrivenBeanDefinitionParser());
            registerBeanDefinitionParser("default-servlet-handler", new DefaultServletHandlerBeanDefinitionParser());
            registerBeanDefinitionParser("interceptors", new InterceptorsBeanDefinitionParser());
            registerBeanDefinitionParser("resources", new ResourcesBeanDefinitionParser());
            registerBeanDefinitionParser("view-controller", new ViewControllerBeanDefinitionParser());
            registerBeanDefinitionParser("redirect-view-controller", new ViewControllerBeanDefinitionParser());
            registerBeanDefinitionParser("status-controller", new ViewControllerBeanDefinitionParser());
            registerBeanDefinitionParser("view-resolvers", new ViewResolversBeanDefinitionParser());
            registerBeanDefinitionParser("tiles-configurer", new TilesConfigurerBeanDefinitionParser());
            registerBeanDefinitionParser("freemarker-configurer", new FreeMarkerConfigurerBeanDefinitionParser());
            registerBeanDefinitionParser("velocity-configurer", new VelocityConfigurerBeanDefinitionParser());
            registerBeanDefinitionParser("groovy-configurer", new GroovyMarkupConfigurerBeanDefinitionParser());
        }
    }

}
