package com.ververica.demo.backend.users.service;

import com.ververica.demo.backend.users.domain.User;
import com.ververica.demo.backend.users.dto.UserDto.UserReq;
import com.ververica.demo.backend.users.exception.UserJpaException.DuplicateEmailException;
import com.ververica.demo.backend.users.exception.UserJpaException.InvalidUserException;
import com.ververica.demo.backend.users.repository.UserRepository;
import com.ververica.demo.backend.utils.EntityProxy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ververica.demo.backend.users.exception.UserJpaErrorCode.DUPLICATE_EMAIL_FOUND;
import static com.ververica.demo.backend.users.exception.UserJpaErrorCode.INVALID_USER;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User checkUserValidity(String userEmail) throws Throwable {
        return new EntityProxy<User, InvalidUserException>().entityCheckThrowException(
                userRepository.findByEmail(userEmail), new InvalidUserException(userEmail, INVALID_USER));
    }

    public String postUser(UserReq userRequest) throws DuplicateEmailException {
        String email = userRequest.getEmail();
        if(isUserEmailDuplicated(email)) throw new DuplicateEmailException(email, DUPLICATE_EMAIL_FOUND);
        User user = userRepository.save(new User(userRequest));
        return user.getName();
    }

    private boolean isUserEmailDuplicated(String email){
        return !userRepository.findByEmail(email).isEmpty();
    }

}
