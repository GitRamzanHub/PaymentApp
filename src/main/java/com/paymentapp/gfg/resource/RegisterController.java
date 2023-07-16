package com.paymentapp.gfg.resource;

import com.paymentapp.gfg.entity.User;
import com.paymentapp.gfg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public User userRegistrationDTO(){
        return new User();
    }
    // showing registration page
    @GetMapping
    public ModelAndView showRegistrationForm(@AuthenticationPrincipal OAuth2User principal, Model model){
        ModelAndView modelAndView = new ModelAndView();

        String username = principal.getAttribute("name");
        System.out.println("Username from Dashboard Controller is: "+username);

        modelAndView.setViewName("register");
        modelAndView.addObject("displayName", username);
        return modelAndView;
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute User formUser){
        //model.addAttribute("user", new User());
        System.out.println(formUser.getName() + " is Registration in our database");
        userService.save(formUser);
        return "redirect:/register?success";
    }
}
