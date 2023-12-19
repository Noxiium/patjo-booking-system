package controller;

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
    public String logout(){
        System.out.println("logout - method");
    return "redirect:/";
    } 
    
}
