package com.ahf.exam.action;

import com.ahf.exam.model.Message;
import com.ahf.exam.model.Student;
import com.ahf.exam.service.IMessage;
import com.ahf.exam.service.IStuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class BackAction {

    @Autowired
    private IStuService stuService;
    @Autowired
    private IMessage message;

    @PostMapping("stuLogin")
    public  String stuLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String url = "login.html";
        String uname = request.getParameter("name");
        String pwd = request.getParameter("pwd");
        String code = request.getParameter("code");
        String reg_code = (String) session.getAttribute("Code");


        Student stu = stuService.stuLogin(uname, pwd);
        if (reg_code.equals(code) && stu != null) {
            session.setAttribute("login_student", stu);
            int role = stu.getS_role();
            if (role == 3) {
                url = "Studentindex.html";

            } else {
                url = "index.html";
            }
        } else {
            url = "login";
        }
        return url;

    }

    @GetMapping(value = "/doGetMessage")
    public String doGetMessage(Model model){
        List<Message> messages=message.findAll();
        model.addAttribute("messages",messages);
        return "message";

    }
    @PostMapping(value = "/delMessage")
    @ResponseBody
    public boolean delMessage(int id){
        boolean ok =false;
    message.deleteById(id);
    ok=true;
    return ok;
    }

}
