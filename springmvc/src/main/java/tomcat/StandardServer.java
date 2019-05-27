package tomcat;

import org.apache.catalina.Executor;
import org.apache.catalina.JmxEnabled;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.util.LifecycleMBeanBase;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class StandardServer extends LifecycleMBeanBase {
    public void await() {
        // 如果端口为-2则不进入循环，直接返回
        if (port == -2) {
            return;
        }
        // 如果端口为-1则进入循环，而且无法通过网络退出
        if (port == -1) {
            try {
                awaitThread = Thread.currentThread();
                while (!stopAwait) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        // continue and check the flag
                    }
                }
            } finally {
                awaitThread = null;
            }
            return;
        }

        // 如果端口不是-1和-2（应该大于0），则会新建一个监听关闭命令的ServerSocket
        awaitSocket = new ServerSocket(port, 1, InetAddress.getByName(address));
        while (!stopAwait) {
            ServerSocket serverSocket = awaitSocket;
            if (serverSocket == null) {
                break;
            }
            Socket socket = null;
            StringBuilder command = new StringBuilder();
            InputStream stream;
            socket = serverSocket.accept();
            socket.setSoTimeout(10 * 1000);
            stream = socket.getInputStream();

            // 检查在指定端口接收到的命令是否和shutdown命令相匹配
            boolean match = command.toString().equals(shutdown);
            // 如果匹配则跳出循环
            if (match) {
                break;
            }

        }
    }
    protected void initInternal() throws LifecycleException {
        super.initInternal();
        if (container != null) {
            container.init();
        }

        for (Executor executor : findExecutors()) {
            if (executor instanceof JmxEnabled) {
                ((JmxEnabled) executor).setDomain(getDomain());
            }
            executor.init();
        }

        mapperListener.init();

        synchronized (connectorsLock) {
            for (Connector connector : connectors) {
                connector.init();
            }
        }
    }

    protected void startInternal() throws LifecycleException {
        setState(LifecycleState.STARTING);
        if (container != null) {
            synchronized (container) {
                container.start();
            }
        }

        synchronized (executors) {
            for (Executor executor: executors) {
                executor.start();
            }
        }

        mapperListener.start();

        synchronized (connectorsLock) {
            for (Connector connector: connectors) {
                if (connector.getState() != LifecycleState.FAILED) {
                    connector.start();
                }
            }
        }
    }

}
