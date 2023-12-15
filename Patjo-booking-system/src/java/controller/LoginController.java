package controller;

import javax.servlet.http.HttpSession;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
    
        return "loginView";
    }

    @PostMapping
    public String handleUserLogin(Model model,
                                  @RequestParam String username, 
                                  @RequestParam String password, HttpSession session) {
    
        
        User user = userService.handleUserLogin(username, password); // <-- Kanske inte returnera hela user objektet i controller?? IDK
        if (user == null){
            return "loginView"; // <-- Visa att användarnamn/lösenord var fel??
        }
        model.addAttribute("username", username);
        model.addAttribute("userId", user.getUserId());
        
        session.setAttribute("username", username);
        session.setAttribute("userId", user.getUserId());
        
        if (user.getIsAdmin() == 0){
            session.setAttribute("role", "Student");
            return "mainPageView";
        }
        else {
            session.setAttribute("role", "Admin");
            return "adminMainPageView";
        }
    }

}
