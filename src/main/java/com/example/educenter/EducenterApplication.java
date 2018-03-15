package com.example.educenter;

import com.example.educenter.model.User;
import com.example.educenter.model.UserType;
import com.example.educenter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class EducenterApplication extends WebMvcConfigurerAdapter implements CommandLineRunner {
    private static final String MANAGER_PASSWORD = "123456";
    private static final String MANAGER_EMAIL = "manager";
    private static final String MANAGER_NAME = "manager";
    private static final String MANAGER_SURNAME = "manager";

    public static void main(String[] args) {
        SpringApplication.run(EducenterApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


    @Override
    public void run(String... strings) throws Exception {

        User oneByEmail = userRepository.findOneByEmail(MANAGER_EMAIL);
        if (oneByEmail == null) {
            userRepository.save(User.builder()
                    .email(MANAGER_EMAIL)
                    .password(new BCryptPasswordEncoder().encode(MANAGER_PASSWORD))
                    .name(MANAGER_NAME)
                    .surname(MANAGER_SURNAME)
                    .type(UserType.MANAGER)
                    .build());
        }

    }

}
