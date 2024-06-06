package com.cryptogate.service;

import com.cryptogate.dto.BaseUser;
import com.cryptogate.enums.Department;
import com.cryptogate.enums.Role;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private PAPService papService;

    @Autowired
    private PIPService pipService;

    @Autowired
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void registerUser(String userAddress, String username,
                             String password, Long roleId, Long depId) throws Exception {
        BaseUser user = BaseUser.builder()
                .username(username)
                .password(passwordEncoder().encode(password))
                .role(Role.getById(roleId))
                .department(Department.getByDepId(depId))
                .build();
        TransactionReceipt transactionReceipt = papService.registerUser(userAddress, user);
    }

    public boolean authenticateUser(String userAddress, String password) throws Exception {
        return papService.authenticateUser(userAddress, passwordEncoder().encode(password));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            String userAddress = username;
            BaseUser user = pipService.getUserAttributes(userAddress);

            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

            return new User(userAddress, "", authorities);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found", e);
        }
    }
}
