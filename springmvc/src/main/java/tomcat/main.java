package tomcat;

import org.apache.catalina.startup.Bootstrap;

import java.lang.reflect.InvocationTargetException;

import static org.apache.tomcat.util.ExceptionUtils.handleThrowable;

public class main {
    public static void main(String args[]) {
        // 先新建一个Bootstrap
        if (daemon == null) {
            Bootstrap bootstrap = new Bootstrap();
            try {
                //初始化了ClassLoader，并用ClassLoader创建了Catalina实例，赋给catalinaDaemon变量
                bootstrap.init();
            } catch (Throwable t) {
                handleThrowable(t);
                t.printStackTrace();
                return;
            }
            daemon = bootstrap;
        } else {
            Thread.currentThread().setContextClassLoader(daemon.catalinaLoader);
        }
        try {
            String command = "start";
            if (args.length > 0) {
                command = args[args.length - 1];
            }
            if (command.equals("startd")) {
                args[args.length - 1] = "start";
                daemon.load(args);
                daemon.start();
            } else if (command.equals("stopd")) {
                args[args.length - 1] = "stop";
                daemon.stop();
            } else if (command.equals("start")) {
                daemon.setAwait(true);
                daemon.load(args);
                daemon.start();
            } else if (command.equals("stop")) {
                daemon.stopServer(args);
            } else if (command.equals("configtest")) {
                daemon.load(args);
                if (null==daemon.getServer()) {
                    System.exit(1);
                }
                System.exit(0);
            }


            else {
                log.warn("Bootstrap: command \"" + command + "\" does not exist.");
            }
        } catch (Throwable t) {
            if (t instanceof InvocationTargetException &&
                    t.getCause() != null) {
                t = t.getCause();
            }
            handleThrowable(t);
            t.printStackTrace();
            System.exit(1);
        }
    }

        }
