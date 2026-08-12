package com.school.schoolproject.service;

import com.school.schoolproject.entities.RegForm;
import com.school.schoolproject.repository.RegFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegFormDetailsService implements UserDetailsService {

    @Autowired
    private RegFormRepository regFormRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<RegForm> regFormList = regFormRepository.findByUsername(username);

        if (regFormList.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        RegForm regForm = regFormList.get(0);

        return User
                .builder()
                .username(regForm.getUsername())
                .password(regForm.getPassword())
                .roles(regForm.getRoles())  // Make sure this field exists in RegForm
                .build();
    }
}