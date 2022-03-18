package com.ibt.userList.Service;


import com.ibt.userList.Model.User;
import com.ibt.userList.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(User user){
        boolean userExist = userRepository.findByEmail(user.getEmail()) //kullanıcının unique özelliği olan email ile kullanıcı var mı diye kontrol ediyoruz
                .isPresent();
        if(userExist){//varsa
            throw new IllegalStateException("email already taken");//hata fırlatıyoruz
        }
        String encodedPassword= bCryptPasswordEncoder.encode(user.getPassword());//parolasını hashliyoruz

        user.setPassword(encodedPassword);//hashlenmiş parolayı set ediyoruz

        userRepository.save(user);//ve kaydediyoruz

        return user.getEnabled().toString();
    }

    public Optional<User> findById(String email){
        return userRepository.findByEmail(email);
    }
}
