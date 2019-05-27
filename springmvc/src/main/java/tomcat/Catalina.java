package tomcat;

import org.apache.catalina.LifecycleException;

public class Catalina {
    public void load() {
        long t1 = System.nanoTime();
        // 省略创建 server代码，创建过程使用Digester完成
        try {
            getServer().init();
        } catch (LifecycleException e) {
            if (Boolean.getBoolean("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE")) {
                throw new java.lang.Error(e);
            } else {
                log.error("Catalina.start", e);
            }
        }

        long t2 = System.nanoTime();
        if(log.isInfoEnabled()) {
            //启动过程中，控制台可以看到
            log.info("Initialization processed in " + ((t2 - t1) / 1000000) + " ms");
        }
    }
    public void start() {
        if (getServer() == null) {
            load();
        }
        long t1 = System.nanoTime();
        try {
            // 调用Server的start方法启动服务器
            getServer().start();
        } catch (LifecycleException e) {
            log.fatal(sm.getString("catalina.serverStartFail"), e);
            try {
                getServer().destroy();
            } catch (LifecycleException e1) {
                log.debug("destroy() failed for failed Server ", e1);
            }
            return;
        }
        long t2 = System.nanoTime();
        if(log.isInfoEnabled()) {
            log.info("Server startup in " + ((t2 - t1) / 1000000) + " ms");
        }
        // 此处省略了注册关闭钩子代码
        // 进入等待状态
        if (await) {
            await();
            stop();
        }
    }


    }
