package com.example.educenter.controller;

import com.example.educenter.model.Subject;
import com.example.educenter.model.User;
import com.example.educenter.model.UserType;
import com.example.educenter.repository.SubjectRepository;
import com.example.educenter.repository.UserRepository;
import com.example.educenter.util.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class ManagerController {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailServiceImpl emailService;
private static final String URL="http://localhost:8080/verify?token=%s&email=%s";
private static  final  String EMAIL_TEXT="Dear %s Thank you, you have successfully registered to our EShop," +
        "Please visit by link in order to activate your profile. %s";
    @GetMapping("/manager")
    public String managerPage(ModelMap map) {
        map.addAttribute("subjects", subjectRepository.findAll());
        map.addAttribute("subjectModel", new Subject());
        map.addAttribute("users", userRepository.findAll());
        map.addAttribute("userModel", new User());
        return "manager";
    }

    @PostMapping("/manager/addStudent")
    public String saveUser(@Valid @ModelAttribute("userModel") User user, BindingResult result) {
        StringBuilder sb = new StringBuilder();
        if (result.hasErrors()) {
            for (ObjectError objectError : result.getAllErrors()) {
                sb.append(objectError.getDefaultMessage() + "<br>");
            }
            return "redirect:/manager?message=" + sb.toString();
        }
        user.setType(UserType.STUDENT);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setToken(UUID.randomUUID().toString());
        userRepository.save(user);

        String url = String.format(URL, user.getToken(), user.getEmail());
        String text = String.format(EMAIL_TEXT, user.getName(), url);
        emailService.sendSimpleMessage(user.getEmail(), "Welcome", text);
        return "redirect:/manager";

    }

    @PostMapping("/manager/addSubject")
    public String saveSubject(@Valid @ModelAttribute("subjectModel") Subject subject, BindingResult result) {
        StringBuilder sb = new StringBuilder();
        if (result.hasErrors()) {
            for (ObjectError objectError : result.getAllErrors()) {
                sb.append(objectError.getDefaultMessage() + "<br>");
            }
            return "redirect:/manager?message=" + sb.toString();
        }
        subjectRepository.save(subject);
        return "redirect:/manager";
    }


    @GetMapping("/manager/deleteSubject")
    public String deleteSubject(@RequestParam("id") int id) {
        Subject subject = subjectRepository.findOneById(id);
        subjectRepository.delete(subject);
        return "redirect:/manager";
    }


    @GetMapping("/manager/deleteStudent")
    public String deleteStudent(@RequestParam("id") int id) {
        User user = userRepository.findOneById(id);
        userRepository.delete(user);
        return "redirect:/manager";
    }


    @GetMapping("/manager/editStudent")
    public String editSubject(@RequestParam("id") int id, ModelMap map) {
        map.addAttribute("user", userRepository.findOne(id));
        return "editStudent";
    }

    @PostMapping("/manager/updateStudent")
    public String updateSubject(@ModelAttribute("user") User user, @RequestParam("id") int id) {
        user.setId(id);
        userRepository.save(user);
        return "redirect:/manager";

    }

}
