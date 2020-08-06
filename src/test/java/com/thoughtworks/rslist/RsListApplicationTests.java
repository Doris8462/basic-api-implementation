package com.thoughtworks.rslist;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RsListApplicationTests {
    ObjectMapper objectMapper=new ObjectMapper();
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

    @AfterEach
    void cleanup(){
        userRepository.deleteAll();
    }

    @Test
    void shouldAddUser() throws Exception{
        User user=new User("Alibaba",19,"male","a@b.com","11234567890");
        ObjectMapper objectMapper=new ObjectMapper();
        String userJson=objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType
                (MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        List<UserEntity> users = userRepository.findAll();

        assertEquals(1, users.size());
        assertEquals("Alibaba", users.get(0).getUserName());
    }

    @Test
    void shouldDeleteUser() throws Exception{
        User user=new User("Alibaba",19,"male","a@b.com","11234567890");
        ObjectMapper objectMapper=new ObjectMapper();
        String userJson=objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType
                (MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        mockMvc.perform(post("/user/delete/1"))
                .andExpect(status().isOk());

        List<UserEntity> users = userRepository.findAll();

        assertEquals(0, users.size());
    }

}
