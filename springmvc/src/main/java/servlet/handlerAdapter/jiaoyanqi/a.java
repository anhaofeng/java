package servlet.handlerAdapter.jiaoyanqi;

import org.springframework.stereotype.Component;

public class User {
    String userName;
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> paramClass) {
        return User.class.equals(paramClass);
    }
    @Override
    public void validate(Object obj, Errors errors) {
        User user = (User) obj;
        if(user.getUserName().length()<8) {
            errors.rejectValue("userName", "valid.userNameLen", new Object[]{"minLength" ,8}, "用户名不能少于{1}位");
        }
    }
}
