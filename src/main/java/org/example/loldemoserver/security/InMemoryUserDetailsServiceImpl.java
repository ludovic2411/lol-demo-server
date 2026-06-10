package org.example.loldemoserver.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class InMemoryUserDetailsServiceImpl implements UserDetailsService {

    // Simule une BDD — remplace par un vrai repo plus tard
    private final Map<String, String> users = Map.of(
            "alice", new BCryptPasswordEncoder().encode("password"),  // bcrypt de "password"
            "bob",   new BCryptPasswordEncoder().encode("password")
    );

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = users.get(username);
        if (password == null) throw new UsernameNotFoundException("User not found: " + username);

        return User.withUsername(username)
                .password(password)
                .roles("USER")
                .build();
    }
}
