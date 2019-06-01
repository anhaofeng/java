package servletStudy;

import javax.servlet.ServletContext;
import java.util.Enumeration;
public interface ServletConfig {
    public String getServletName();
    public ServletContext getServletContext();
    public String getInitParameter(String name);
    public Enumeration<String> getInitParameterNames();
}
