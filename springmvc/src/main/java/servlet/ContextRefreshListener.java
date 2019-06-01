package servlet;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.servlet.FrameworkServlet;

private class ContextRefreshListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        FrameworkServlet.this.onApplicationEvent(event);
    }
}

//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        this.refreshEventReceived = true;
//        onRefresh(event.getApplicationContext());
//    }
