package servlet.handlerAdapter.jiaoyanqi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/valid")
public class ValidatorController {
    @Autowired
    private UserValidator validator;
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @RequestMapping(value={"/index", "/", ""},method= {RequestMethod.GET})
    public String index(ModelMap m) throws Exception {
        m.addAttribute("user", new User());
        return "user.jsp";
    }

    @RequestMapping(value={"/signup"},method= {RequestMethod.POST})
    public String signup(@Valid User user, BindingResult br, RedirectAttributes ra) throws Exception {
        ra.addFlashAttribute("user", user);
        return " user.jsp ";
    }
}
