package controller;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GoController {
    private final Log logger = LogFactory.getLog(GoController.class);
    //处理HEAD类型的”/”请求
    @RequestMapping(value={"/"},method= {RequestMethod.HEAD})
    public String head() {
        return "go.jsp";
    }
    //处理GET类型的"/index"和”/”请求
    @RequestMapping(value={"/index","/"},method= {RequestMethod.GET})
    public String index(Model model) throws Exception {
        logger.info("======processed by index=======");
        //返回msg参数
        model.addAttribute("msg", "Go Go Go!");
        return "go.jsp";
    }
}
