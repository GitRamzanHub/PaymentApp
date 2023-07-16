//package com.paymentapp.gfg.resource;
//
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class OAuth2CallBackController {
//    @GetMapping("/login/oauth2/code/github")
//    public String githubCallback(@AuthenticationPrincipal OAuth2User principal) {
//        // Process the authorization code and obtain the access token
//        // Store the access token or perform any necessary actions
//        // Redirect the user to the desired page
//        String username = principal.getAttribute("login");
//        String name = principal.getAttribute("name");
//        String email = principal.getAttribute("email");
//        System.out.println(username);
//        System.out.println(name);
//        System.out.println(email);
//        return "redirect:/dashboard";
//    }
//}
