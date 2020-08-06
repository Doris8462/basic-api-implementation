package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
    private final UserRepository userRepository;
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("/user")
    public void register(@RequestBody @Valid User user){
        UserEntity entity =UserEntity.builder()
                .userName(user.getUserName())
                .age(user.getAge())
                .gender(user.getGender())
                .email(user.getEmail())
                .phone(user.getPhone())
                .voteNum(user.getVote())
                .build();
        userRepository.save(entity);
    }

    @PostMapping("/user/delete/{index}")
    public void deleteUser(@PathVariable int index) {
        userRepository.deleteById(index);
    }

}
