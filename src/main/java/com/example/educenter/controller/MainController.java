package com.example.educenter.controller;


import com.example.educenter.model.User;
import com.example.educenter.model.UserType;
import com.example.educenter.repository.UserRepository;
import com.example.educenter.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/")
    public String userHome(ModelMap map) {
        map.addAttribute("users", userRepository.findAll());
        map.addAttribute("user",new User());
        return "login";
    }

    @RequestMapping( value = "/verify", method = RequestMethod.GET)
    public  String verify(@RequestParam("token")String token, @RequestParam("email")String email){
        User oneByEmail = userRepository.findOneByEmail(email);
        if (oneByEmail!=null){
            if (oneByEmail.getToken()!=null&&oneByEmail.getToken().equals(token)){
                oneByEmail.setToken(null);
                oneByEmail.setVerify(true);
                userRepository.save(oneByEmail);
            }
        }
return "redirect:/";
    }


    @RequestMapping(value = "/loginSuccess", method = RequestMethod.GET)
    public String loginSuccess() {
        CurrentUser principal = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getUser().getType() == UserType.MANAGER) {
            return "redirect:/manager";
        }
        return "redirect:/student?userId=" + principal.getId();
    }

    @GetMapping("/login")

    public String log( ModelMap map){
        map.addAttribute("users", userRepository.findAll());
        map.addAttribute("user", new User());
        return "login";
    }


}