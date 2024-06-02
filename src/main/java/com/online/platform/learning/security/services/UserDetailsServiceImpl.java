package com.online.platform.learning.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.online.platform.learning.models.User;
import com.online.platform.learning.repository.UserRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String emailUserName) throws UsernameNotFoundException {
    User user;
    if(isValidEmail(emailUserName)) {
      user = userRepository.findByEmail(emailUserName)
              .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + emailUserName));
    } else {
      user = userRepository.findByUsername(emailUserName)
              .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + emailUserName));
    }
    return UserDetailsImpl.build(user);
  }

  public boolean isValidEmail(String email) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    Pattern pattern = Pattern.compile(emailRegex);
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
  }

}