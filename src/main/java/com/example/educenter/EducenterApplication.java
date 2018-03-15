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
		String email = "manager@gmail.com";
		User oneByEmail = userRepository.findOneByEmail(email);
		if (oneByEmail == null) {
			userRepository.save(User.builder()
					.email(email)
					.password(new BCryptPasswordEncoder().encode("123456"))
					.name("manager")
					.surname("manager")
					.type(UserType.MANAGER)
					.build());
		}

	}

}
