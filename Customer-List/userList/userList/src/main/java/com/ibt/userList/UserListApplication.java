package com.ibt.userList;

import com.ibt.userList.Model.User;
import com.ibt.userList.Repository.UserRepository;
import com.ibt.userList.Service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class UserListApplication implements CommandLineRunner {

		private final UserService userService;
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;


	public UserListApplication(UserService userService, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}


	public static void main(String[] args) {
		SpringApplication.run(UserListApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		User user = new User("bilal","ak","ba","123");
		String encodedPassword= bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepository.save(user);


	}
}