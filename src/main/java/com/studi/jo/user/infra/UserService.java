package com.studi.jo.user.infra;

import com.studi.jo.user.domain.User;
import com.studi.jo.user.domain.Email;
import com.studi.jo.user.domain.Role;
import com.studi.jo.user.domain.User;
import com.studi.jo.user.domain.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

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

    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new EntityNotFoundException("ERROR: User with id " + id + " not found.");
        }
        return user.get();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(new Email(email));
        if(!user.isPresent()){
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return user.get();
    }
}
