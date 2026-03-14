package com.fpoly.lab1.service;

import com.fpoly.lab1.dao.UserDAO;
import com.fpoly.lab1.entity.J6user;
import com.fpoly.lab1.entity.J6userrole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        J6user user = userDAO.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        List<GrantedAuthority> authorities = user.getJ6userroles().stream()
                .map(J6userrole::getRole)
                .map(role -> new SimpleGrantedAuthority(role.getId()))
                .collect(Collectors.toList());

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                // disabled = true khi enabled != true
                .disabled(!Boolean.TRUE.equals(user.getEnabled()))
                .build();
    }
}
