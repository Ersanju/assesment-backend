package com.assessment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.assessment.dto.UserResponse;
import com.assessment.entity.User;
import com.assessment.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void loadUsers() {
        
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://dummyjson.com/users";
        UserResponse response = restTemplate.getForObject(url, UserResponse.class);

        if (response != null) {
            userRepository.saveAll(response.getUsers());
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    public List<User> getUsersSortedByAge(boolean ascending) {
        Sort sort = ascending ? Sort.by("age").ascending() : Sort.by("age").descending();
        return userRepository.findAll(sort);
    }
    
    public User getUserByIdOrSsn(Long id, String ssn) {
        return id != null ? userRepository.findById(id).orElse(null) 
                          : userRepository.findBySsn(ssn).orElse(null);
    }
}
