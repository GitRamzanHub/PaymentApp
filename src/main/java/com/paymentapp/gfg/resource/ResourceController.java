//package com.paymentapp.gfg.resource;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class ResourceController {
//
////    @GetMapping("/login")
////    public String showLoginPage() {
////        return "login";
////    }
////    @GetMapping("/dashboard")
////    public String showDashboardPage() {
////        return "dashboard";
////    }
//    @GetMapping("/data")
//    public ResponseEntity<Object> getData2(@AuthenticationPrincipal OAuth2User principal){
//        String username = principal.getAttribute("login");
//        String name = principal.getAttribute("name");
//        String email = principal.getAttribute("email");
//        System.out.println(username);
//        System.out.println(name);
//        System.out.println(email);
//        return new ResponseEntity<>(name+", This is a test endpoint to get the resource data", HttpStatus.OK);
//    }
//}
