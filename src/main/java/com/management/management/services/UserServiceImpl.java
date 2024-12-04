package com.management.management.services;


import com.management.management.entity.Employee;
import com.management.management.entity.User;
import com.management.management.entity.UserDto;
import com.management.management.repository.AuthenticationService;
import com.management.management.repository.EmployeeRepository;
import com.management.management.repository.UserRepository;
import com.management.management.repository.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, AuthenticationService {
    @Autowired
    private UserRepository userRepository ;
    @Autowired
    private PasswordEncoder passwordEncoder ;
    @Autowired
    private EmployeeRepository employeeRepository ;



    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }



    @Override
    public User viewProfile(String email) {
       return userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("email not found"));


    }

    @Override
    public User registerUser(User registerRequest) {

        try {

            if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
                throw new RuntimeException("Email is  already Exist");

            }
            registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

            return userRepository.save(registerRequest);
        }catch (Exception e)
        {
            throw new RuntimeException("Error while registering the employee: " + e.getMessage());

        }
    }

    @Override
    public Employee registerEmployee(Employee employee) {
        try {

            if(userRepository.findByEmail(employee.getEmail()).isPresent())
            {
                throw new RuntimeException("Email is already Exist");

            }


            employee.setPassword(passwordEncoder.encode(employee.getPassword()));



            return employeeRepository.save(employee);
        } catch (Exception e) {
            throw new RuntimeException("Error while registering the employee: " + e.getMessage());
        }

    }


    @Override
    public boolean login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("user not found"));

       return passwordEncoder.matches(password,user.getPassword());
    }
    public UserDto convertToDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getRole());
    }

    public List<UserDto> convertToDtoList(List<User> users) {
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("user not found"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword()
        ,getAuthority(user));

    }

    private Collection<?extends GrantedAuthority>getAuthority(User user){
        return Arrays.asList(new SimpleGrantedAuthority("ROLE"+user.getRole()));
    }
}
