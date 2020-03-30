package run.kabuctl.vaulttokenizationdemoui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UiController {
    private final UiService uiService;

    public UiController(UiService uiService) {
        this.uiService = uiService;
    }

    @GetMapping(value = "/")
    public String home(Model model) throws Exception {
        model.addAttribute("eusers", this.uiService.getEncryptedUsers());
        model.addAttribute("tusers", this.uiService.getTransformedUsers());
        return "ui/index";
    }

    @PostMapping(value = "/encrypt")
    public String encrypt(HttpServletRequest request) {
        String howto = "";

        if(request.getParameter("transit") != null) {
            howto = "transit";
        } else if (request.getParameter("transformation") != null) {
            howto = "transformation";
        }

        System.out.println("DEBUG:" + howto);

        this.uiService.addOneEncryptedUser(
                request.getParameter("username"),
                request.getParameter("password"),
                request.getParameter("email"),
                request.getParameter("creditcard"),
                howto
        );

        return "redirect:/";
    }
}

