package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * MainPageController class handles redirecting.
 * 
 */

@Controller
public class MainPageController {
    
    @RequestMapping("/main")
    public String showStudentMainView(){

        return "mainPageView";
    }
      @RequestMapping("/adminmain")
    public String showAdminMainView(){

        return "adminMainPageView";
    }
    
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        System.out.println("logout - method");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        // Prevent caching
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.
        return "redirect:/";
    } 
    
}
