package com.school.schoolproject.service;

import com.school.schoolproject.entities.RegForm;
import com.school.schoolproject.repository.RegFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RegFormDetailsService implements UserDetailsService {

    @Autowired
    private RegFormRepository regFormRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        java.util.Optional<RegForm> regForm = regFormRepository.findByUsername(username);
        if (regForm.isPresent()) {
            return User
                    .builder()
                    .username(regForm.get().getUsername())
                    .password(regForm.get().getPassword())
                    .roles(regForm.get().getRoles())
                    .build();
        } else  {
            throw new UsernameNotFoundException(username);
        }
    }
}
