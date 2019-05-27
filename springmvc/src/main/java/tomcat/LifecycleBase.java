package tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.LifecycleState;

public class LifecycleBase implements  Lifecycle {


    @Override
    public void init() throws LifecycleException {

    }

    @Override
    public void start() throws LifecycleException {

    }

    @Override
    public void stop() throws LifecycleException {

    }

    @Override
    public void destroy() throws LifecycleException {

    }

    @Override
    public LifecycleState getState() {
        return null;
    }

    @Override
    public String getStateName() {
        return null;
    }
    private final LifecycleSupport lifecycle = new LifecycleSupport(this);
    @Override
    public void addLifecycleListener(LifecycleListener listener) {
        lifecycle.addLifecycleListener(listener);
    }
    public LifecycleListener[] findLifecycleListeners() {
        return lifecycle.findLifecycleListeners();
    }
    @Override
    public void removeLifecycleListener(LifecycleListener listener) {
        lifecycle.removeLifecycleListener(listener);
    }

}
