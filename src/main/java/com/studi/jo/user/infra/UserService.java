package com.studi.jo.user.infra;

import com.studi.jo.user.domain.Role;
import com.studi.jo.user.domain.User;
import com.studi.jo.user.domain.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    private User saveUser(User user) {
        return userRepository.save(user);
    }

    public User createUser(UserDTO userDTO, Role role) {
        return saveUser(userDTO.toUser(role));
    }

}
