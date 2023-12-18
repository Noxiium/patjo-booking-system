package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * MainPageController class handles redirecting to the mainPageView and loginView.
 * 
 */

@Controller
public class MainPageController {
    
    @RequestMapping("/main")
    public String showMainView(){

        return "mainPageView";
    }
    
    @RequestMapping("/logout")
    public String logout(){
        System.out.println("logout - method");
    return "redirect:/";
    } 
    
}
