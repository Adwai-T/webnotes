package com.example.Notes.security_manager;

import com.example.Notes.models.KnownUsers;
import com.example.Notes.repositories.UserRepository;
import com.example.Notes.security_manager.models.UserDetails_Implementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KnownUserDetailsService implements UserDetailsService {

    private UserRepository repository;

    @Autowired
    public KnownUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<KnownUsers> user = repository.findByUserName(userName);

        user.orElseThrow(()-> new UsernameNotFoundException("User with UserName : " + userName + " not found."));

        return user.map(UserDetails_Implementation::new).get();
    }
}
