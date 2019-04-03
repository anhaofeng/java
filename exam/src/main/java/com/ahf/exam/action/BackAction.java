package com.ahf.exam.action;

import com.ahf.exam.model.Message;
import com.ahf.exam.model.Role;
import com.ahf.exam.model.Student;
import com.ahf.exam.service.IMessage;
import com.ahf.exam.service.IRole;
import com.ahf.exam.service.IStuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class BackAction {

    @Autowired
    private IStuService stuService;
    @Autowired
    private IMessage message;
    @Autowired
    private IRole roleService;

//    @PostMapping("stuLogin")
//    public  String stuLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        HttpSession session = request.getSession();
//        String url = "login.html";
//        String uname = request.getParameter("name");
//        String pwd = request.getParameter("pwd");
//        String code = request.getParameter("code");
//        String reg_code = (String) session.getAttribute("Code");
//
//
//        Student stu = stuService.stuLogin(uname, pwd);
//        if (reg_code.equals(code) && stu != null) {
//            session.setAttribute("login_student", stu);
//            int role = stu.getS_role();
//            if (role == 3) {
//                url = "studentindex.html";
//
//            } else {
//                url = "index.html";
//            }
//        } else {
//            url = "login";
//        }
//        return url;
//
//    }
    @PostMapping(value="/stuLogin")
    public String login(String username, String password) {
        try {
            UsernamePasswordToken passwordToken =new UsernamePasswordToken(username,password);
            Subject subject = SecurityUtils.getSubject();

            subject.login(passwordToken);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Set<Role> roles=stuService.findUserRoles(username);

        for (Role role:roles) {
            if ("student".equals(role.getRes_name())){
                return "studentindex";
            }
            if ("teacher".equals(role.getRes_name())){
                return "index";
            }
        }

        return "权限错误！";
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
    @GetMapping(value = "/doMessage/{id}/{method}")
    String doMessage(@PathVariable Integer id,@PathVariable String method,Model model){
        Optional<Message> mes=message.findById(id);
        model.addAttribute("message",(Message)mes.get());
        if (("get").equals(method)){
            return  "messageInfo";
        }
        else  {
            return "messageUpdata";
        }


    }
@PostMapping(value = "/doMessage")
@ResponseBody
    String doMessage(String mes_head,String mes_content,Integer mes_id){
        message.updateMessage(mes_head,mes_content,mes_id);
        return "success";
}
@RequestMapping(value = "/sortMessage/{id}/{top}")
@ResponseBody
    String sortMessage(@PathVariable Integer id,@PathVariable Integer top){
    top=(top > 0)? 0:1;
    message.sortMessage(top,id);
    return "ok";
}
    @GetMapping(value = "/getRoles")
    String getRoles(Model model){
        List<Role> roles=roleService.findAll();
        model.addAttribute("roleList",roles);
        return "addStudent";
    }
    @PostMapping(value = "/addStu")
    @ResponseBody
    String addStu(Model model){
        List<Role> roles=roleService.findAll();
        model.addAttribute("roleList",roles);
        return "ok";
    }
}
