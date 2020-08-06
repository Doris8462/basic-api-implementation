package com.thoughtworks.rslist;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    @Autowired
    RsEventRepository rsEventRepository;

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
    @Test
    void shouldAddOneRsEventUserAlreadyExist() throws Exception{
        UserEntity save=userRepository.save(UserEntity.builder().email("a@b.com").phone("19999999999")
        .gender("female").age(19).userName("idolice").voteNum(10).build());

        String userJson="{\"eventName\":\"猪肉涨价了\",\"keyword\":\"经济\",\"userId\":"+save.getId()+"}";
        mockMvc.perform(post("/rs/add").content(userJson).contentType
                (MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
        List<RsEventEntity> all=rsEventRepository.findAll();
        assertNotNull(all);
        assertEquals(1,all.size());
        assertEquals("猪肉涨价了",all.get(0).getEventName());
        assertEquals("经济",all.get(0).getKeyword());
        assertEquals(save.getId(),all.get(0).getUserId());

        String anotherUserJson="{\"eventName\":\"猪肉又涨价了\",\"keyword\":\"民生\",\"userId\":"+save.getId()+"}";
        mockMvc.perform(post("/rs/add").content(anotherUserJson).contentType
                (MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
        all=rsEventRepository.findAll();
        assertNotNull(all);
        assertEquals(2,all.size());
        assertEquals("猪肉又涨价了",all.get(1).getEventName());
        assertEquals("民生",all.get(1).getKeyword());
        assertEquals(save.getId(),all.get(1).getUserId());
        List<UserEntity> users = userRepository.findAll();
        assertEquals(1,users.size());
    }

    @Test
    void shouldAddOneRsEventUserNotExist() throws Exception{
        String userJson="{\"eventName\":\"猪肉涨价了\",\"keyword\":\"经济\",\"userId\":100}";
        mockMvc.perform(post("/rs/add").content(userJson).contentType
                (MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

}
