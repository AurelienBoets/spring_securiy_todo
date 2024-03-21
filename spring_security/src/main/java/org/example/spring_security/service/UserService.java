package org.example.spring_security.service;

import org.example.spring_security.config.jwt.JwtProvider;
import org.example.spring_security.entity.Role;
import org.example.spring_security.entity.User;
import org.example.spring_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Lazy
    @Autowired
    private PasswordEncoder encoder;

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtProvider provider;

    public boolean checkUserNameExists(String email) {
        return repository.findByMail(email).isPresent();
    }

    public boolean createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
        return true;
    }

    public User findUserByRole(Role role){
        return repository.findFirstByRole(role);
    }

    public boolean verifyUser(String email, String password) {
        return repository.findByMail(email).map(user -> encoder.matches(password, user.getPassword()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
    }

    public String generateToken(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return provider.generateToken(authentication);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByMail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with mail " + username));
    }
}
