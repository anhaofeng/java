package toSpring;

public class ServletRequestAttributes {
    //通过scope判断是request,session
    public void setAttribute(String name, Object value, int scope) {
        if (scope == SCOPE_REQUEST) {
            if (!isRequestActive()) {
                throw new IllegalStateException(
                        "Cannot set request attribute - request is not active anymore!");
            }
            this.request.setAttribute(name, value);
        }else {
            HttpSession session = getSession(true);
            this.sessionAttributesToUpdate.remove(name);
            session.setAttribute(name, value);
        }
    }

}
