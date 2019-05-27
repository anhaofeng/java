package servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

public class HttpServlet extends javax.servlet.http.HttpServlet implements Servlet {
    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }


    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void service(ServletRequest req, ServletResponse res)
            throws ServletException, IOException {
        HttpServletRequest request;
        HttpServletResponse response;
        //如果请求类型不相符，则抛出异常
        if (!(req instanceof HttpServletRequest &&
                res instanceof HttpServletResponse)) {
            throw new ServletException("non-HTTP request or response");
        }
        //转换request和response的类型
        request = (HttpServletRequest) req;
        response = (HttpServletResponse) res;
        //调用http的处理方法
        service(request, response);
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //获取请求类型
        String method = req.getMethod();
        //将不同的请求类型路由到不同的处理方法
        if (method.equals(METHOD_GET)) {
            long lastModified = getLastModified(req);
            if (lastModified == -1) {
                doGet(req, resp);
            } else {
                long ifModifiedSince = req.getDateHeader(HEADER_IFMODSINCE);
                if (ifModifiedSince < lastModified) {
                    maybeSetLastModified(resp, lastModified);
                    this.doGet(req, resp);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                }
            }
        } else if (method.equals(METHOD_HEAD)) {
            long lastModified = getLastModified(req);
            maybeSetLastModified(resp, lastModified);
            doHead(req, resp);
        } else if (method.equals(METHOD_POST)) {
            doPost(req, resp);
        } else if (method.equals(METHOD_PUT)) {
            doPut(req, resp);
        } else if (method.equals(METHOD_DELETE)) {
            doDelete(req, resp);
        } else if (method.equals(METHOD_OPTIONS)) {
            doOptions(req, resp);
        } else if (method.equals(METHOD_TRACE)) {
            doTrace(req, resp);
        } else {
            String errMsg = lStrings.getString("http.method_not_implemented");
            Object[] errArgs = new Object[1];
            errArgs[0] = method;
            errMsg = MessageFormat.format(errMsg, errArgs);
            resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, errMsg);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_get_not_supported");
        if (protocol.endsWith("1.1")) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
        }
    }

    }
