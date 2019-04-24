package com.wpate.myvent.user;

import com.wpate.myvent.user.exceptions.UserAlreadyExistsException;
import com.wpate.myvent.user.exceptions.UserNotFindException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        var user = repository.findByName(s);
        if(user == null) throw new UserNotFindException("User with such username doesnt exist");
        return user;
    }

    public void addUser(ApplicationUser user){
        if(repository.existsById(user.getId())) throw new UserAlreadyExistsException("User already exists!");
    }

}
