package com.ahf.exam.action;

import com.ahf.exam.model.Questions;
import com.ahf.exam.model.Subject;
import com.ahf.exam.service.IRole;
import com.ahf.exam.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class SubjectAction {
    @Autowired
    private ISubjectService subjectService;


    @PostMapping(value = "/addSub")
    public  void  addSub(Subject sub){
        subjectService.save(sub);
    }
    @PostMapping(value = "/addAjaxSub")
    public void addAjaxSub(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String qes_subname=request.getParameter("qes_subname");
        PrintWriter out=response.getWriter();
        if(subjectService.findByName(qes_subname)==null){
            out.print("");
        }else{
            out.print("*该科目已存在");
        }
    }
    @GetMapping(value = "/getSubList")
    public String  getSubList(HttpServletRequest request )  {
        List<Subject> subjects= subjectService.findAll();
       request.setAttribute("subs",subjects);
       return "select_sub";


    }
    @GetMapping(value = "subQesList")
    public  String subQesList(int sub, Model model){
    List<Questions> qes=null;
    qes=subjectService.getQesBySub(sub);

        System.out.println("+++++++++++++"+qes);
    model.addAttribute("qesList",qes);
    return  "subQesList";
    }

}
