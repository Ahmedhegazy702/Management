package com.management.management.repository;



import com.management.management.entity.Employee;
import com.management.management.entity.User;
import com.management.management.entity.UserDto;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User viewProfile(String email);
    User registerUser(User registerRequest);
    Employee registerEmployee(Employee employee);
    UserDto convertToDto(User user);
    List<UserDto> convertToDtoList(List<User> users);

}
