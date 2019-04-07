package com.ahf.exam.model;

import lombok.Data;

import java.io.Serializable;

@Data
class LoginUser implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long userId;          // 主键ID
    public String account;      // 账号
    public String name;         // 姓名

    public LoginUser() {
    }

    public LoginUser(String account) {
        this.account=account;
    }

    public LoginUser(Long userId, String account, String name) {
        this.userId = userId;
        this.account = account;
        this.name = name;
    }
}


class UserContext implements AutoCloseable {
    static final ThreadLocal<LoginUser> current = new ThreadLocal<>();

    public UserContext(LoginUser user) {
        current.set(user);
    }

    public static LoginUser getCurrentUser() {
        return current.get();
    }

    public void close() {
        current.remove();
    }
}
