package com.thoughtworks.rslist.api;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper;
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setup(){
       objectMapper=new ObjectMapper();
       rsEventRepository.deleteAll();
       userRepository.deleteAll();
    }

    @Test
    public void shouldRegisterUser() throws Exception {
        User user=new User("Alibaba",18,"male","a@b.com","11234567890");
        String request = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isOk());
    }

    @Test
    void nameShouldNotLongerThan8()throws Exception {
        User user=new User("Alibaba12",18,"male","a@b.com","11234567890");
        ObjectMapper objectMapper=new ObjectMapper();
        String userJson=objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType
                (MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error",is("invalid param")));
    }
    @Test
    void nameShouldNotNull()throws Exception {
        User user=new User(null,18,"male","a@b.com","11234567890");
        ObjectMapper objectMapper=new ObjectMapper();
        String userJson=objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType
                (MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error",is("invalid param")));
    }
    @Test
    void genderShouldNotNull()throws Exception {
        User user=new User("Alibaba",18,null,"a@b.com","11234567890");
        ObjectMapper objectMapper=new ObjectMapper();
        String userJson=objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType
                (MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error",is("invalid param")));
    }
    @Test
    void ageShouldNotLessThan18()throws Exception {
        User user=new User("Alibaba",17,"male","a@b.com","11234567890");
        ObjectMapper objectMapper=new ObjectMapper();
        String userJson=objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType
                (MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error",is("invalid param")));
    }
    @Test
    void ageShouldNotMoreThan100()throws Exception {
        User user=new User("Alibaba",101,"male","a@b.com","11234567890");
        ObjectMapper objectMapper=new ObjectMapper();
        String userJson=objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType
                (MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error",is("invalid param")));
    }
    @Test
    void emailShouldValid()throws Exception {
        User user=new User("Alibaba",20,"male","ab.com","11234567890");
        ObjectMapper objectMapper=new ObjectMapper();
        String userJson=objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType
                (MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error",is("invalid param")));
    }
    @Test
    void phoneSHouleValid()throws Exception {
        User user=new User("Alibaba",20,"male","a@b.com","1123456789");
        ObjectMapper objectMapper=new ObjectMapper();
        String userJson=objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType
                (MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error",is("invalid param")));
    }
    @Test
    public void shouldDeleteUser() throws Exception {
        UserEntity save=userRepository.save(UserEntity.builder().email("a@b.com").phone("19999999999")
                .gender("female").age(19).userName("idolice").voteNum(10).build());
        RsEventEntity rsEventEntity= RsEventEntity.builder().userId(save.getId()).keyword("keyword").
                eventName("eventName").build();
        rsEventRepository.save(rsEventEntity);
        assertEquals(1,userRepository.findAll().size());
        assertEquals(1,rsEventRepository.findAll().size());

        mockMvc.perform(delete("/user/{id}/delete",save.getId())).andExpect(status().isOk());
        assertEquals(0,userRepository.findAll().size());
        assertEquals(0,rsEventRepository.findAll().size());

    }
}


