package com.xxx.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;

/**
 * Created by 游戏2 on 2016/10/24.
 */
public class AJAXServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        try {

            //1，取页面的参数
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            String name = request.getParameter("name");

            //2, 检查参数
            if (name == null || name.length() == 0) {
                out.println("用户名不能为空");
            } else {

                //3, 校验操作
                if (name.equals("haha")) {

                    //4，将信息返回原页面
                    out.println("用户名【" + name + "】已存在");
                } else {
                    //4，将信息返回原页面
                    out.println("用户名【" + name + "】不存在");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
