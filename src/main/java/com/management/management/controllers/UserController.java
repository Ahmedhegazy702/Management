package com.management.management.controllers;

import com.management.management.config.JwtUtil;
import com.management.management.entity.Employee;
import com.management.management.entity.Login;
import com.management.management.entity.User;
import com.management.management.entity.UserDto;
import com.management.management.repository.AuthenticationService;
import com.management.management.repository.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil ;
    @Autowired
    private AuthenticationService authenticationService ;


    @PostMapping("/register")
    public ResponseEntity<User> register( @RequestBody @Valid User registerRequest) {

      return ResponseEntity.ok(userService.registerUser(registerRequest));

    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Login loginRequest) {
        boolean success= authenticationService.login(loginRequest.getEmail(),loginRequest.getPassword());
        if(success) {
            //  return ResponseEntity.ok("Login Successful!");
          String token=  jwtUtil.generateToken(loginRequest.getEmail());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("token", token);

            return ResponseEntity.ok("Login Successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @PostMapping("/register/employee")
    public ResponseEntity<Employee> registerEmployee(@RequestBody @Valid Employee registerRequest) {
       return ResponseEntity.ok(userService.registerEmployee(registerRequest));
    }




    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        List<User> users = userService.getAllUsers();
         return ResponseEntity.ok( userService.convertToDtoList(users));
        //return ResponseEntity.ok(users);
    }
    @GetMapping("/profile")
    public ResponseEntity<User> viewProfile(@RequestParam String email, Authentication authentication) {

        String loggedInEmail = authentication.getName();



        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !email.equals(loggedInEmail)) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        User user = userService.viewProfile(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
