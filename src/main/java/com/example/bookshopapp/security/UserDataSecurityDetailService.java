package com.example.bookshopapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDataSecurityDetailService implements UserDetailsService {

    private final UserDataSecurityRepository userDataSecurityRepository;

    @Autowired
    public UserDataSecurityDetailService(UserDataSecurityRepository userDataSecurityRepository) {
        this.userDataSecurityRepository = userDataSecurityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDataSecurity userDataSecurity = userDataSecurityRepository.findUserDataSecurityByEmail(username);
        if (userDataSecurity != null) {
            return new UserDataSecurityDetails(userDataSecurity);
        }

        userDataSecurity = userDataSecurityRepository.findUserDataSecurityByPhone(username);
        if (userDataSecurity != null) {
            return new UserDataSecurityDetails(userDataSecurity);
        }

        throw new UsernameNotFoundException("user " + username + " not found doh!");
    }

    public void updateAuthenticationType(String userEmail, String oauth2ClientName) {
        AuthenticationType authType = AuthenticationType.valueOf(oauth2ClientName.toUpperCase());
        userDataSecurityRepository.updateAuthenticationType(userEmail, authType);
    }
}
