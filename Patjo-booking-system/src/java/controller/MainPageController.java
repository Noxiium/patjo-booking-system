
package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * MainPageController class handles redirecting to the mainPageView.
 * 
 */

@Controller
@RequestMapping("/mainPage")
public class MainPageController {
    
    @GetMapping
    public String showMainView(){
    
        return "mainPageView";
    }
    
}
