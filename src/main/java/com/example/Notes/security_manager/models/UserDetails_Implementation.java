package com.example.Notes.security_manager.models;

import com.example.Notes.models.KnownUsers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetails_Implementation implements UserDetails {

    private String userName;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNotLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private List<GrantedAuthority> authorityList;

    public UserDetails_Implementation(KnownUsers user){

        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.accountNonExpired = true;
        this.accountNotLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.authorityList = new ArrayList<>();
        this.authorityList.add(new SimpleGrantedAuthority(user.getRoles()));

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNotLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
