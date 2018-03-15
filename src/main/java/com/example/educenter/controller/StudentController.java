package com.example.educenter.controller;

import com.example.educenter.model.User;
import com.example.educenter.model.UserType;
import com.example.educenter.repository.SubjectRepository;
import com.example.educenter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @GetMapping(value = "/student")
    public String studentPage(ModelMap map, @RequestParam("userId") int id) {
        User user = userRepository.findOne(id);
        map.addAttribute("user", user);
        map.addAttribute("subjects", user.getSubjects());
        return "student";
    }

    @GetMapping(value = "/student/printSubject")
    public String printSubject(@RequestParam("subjectId") int id, ModelMap map) {
        map.addAttribute("subject", subjectRepository.findOne(id));
        return "mySubject";
    }

    @PostMapping(value = "/student/changeData")
    public String changeUserData(@RequestParam("id") int id, @ModelAttribute("user") User user) {
        user.setId(id);
        user.setSubjects(subjectRepository.findAllByUsersIsContaining(user));
        user.setType(UserType.STUDENT);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/student?userId=" + user.getId();
    }

}
