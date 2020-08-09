package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.domain.Votes;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;


@AutoConfigureMockMvc
@SpringBootTest
class VoteControllerTest {
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    VoteRepository voteRepository;

    @AfterEach
    void cleanup() {
        voteRepository.deleteAll();
        rsEventRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldGetVoteWhenGivenUserIdAndEventId() throws Exception {
        UserEntity user = UserEntity.builder()
                .userName("user 0")
                .gender("male")
                .age(20)
                .email("12@34.com")
                .phone("13579246810")
                .voteNum(3)
                .build();
        user = userRepository.save(user);
        RsEventEntity event = RsEventEntity.builder()
                .eventName("event 0")
                .keyword("key")
                .userId(user.getId())
                .build();
        event = rsEventRepository.save(event);
        VoteEntity vote0 = VoteEntity.builder()
                .localDateTime(LocalDateTime.now())
                .num(0)
                .user(user)
                .rsEvent(event)
                .build();
        voteRepository.save(vote0);

        VoteEntity vote1 = VoteEntity.builder()
                .localDateTime(LocalDateTime.now())
                .num(1)
                .user(user)
                .rsEvent(event)
                .build();
        voteRepository.save(vote1);

        VoteEntity vote2 = VoteEntity.builder()
                .localDateTime(LocalDateTime.now())
                .num(2)
                .user(user)
                .rsEvent(event)
                .build();
        voteRepository.save(vote2);

        VoteEntity vote3 = VoteEntity.builder()
                .localDateTime(LocalDateTime.now())
                .num(3)
                .user(user)
                .rsEvent(event)
                .build();
        voteRepository.save(vote3);

        VoteEntity vote4 = VoteEntity.builder()
                .localDateTime(LocalDateTime.now())
                .num(4)
                .user(user)
                .rsEvent(event)
                .build();
        voteRepository.save(vote4);

        VoteEntity vote5 = VoteEntity.builder()
                .localDateTime(LocalDateTime.now())
                .num(5)
                .user(user)
                .rsEvent(event)
                .build();
        voteRepository.save(vote5);

        VoteEntity vote6 = VoteEntity.builder()
                .localDateTime(LocalDateTime.now())
                .num(6)
                .user(user)
                .rsEvent(event)
                .build();
        voteRepository.save(vote6);

        VoteEntity vote7 = VoteEntity.builder()
                .localDateTime(LocalDateTime.now())
                .num(7)
                .user(user)
                .rsEvent(event)
                .build();
        voteRepository.save(vote7);

        VoteEntity vote8 = VoteEntity.builder()
                .localDateTime(LocalDateTime.now())
                .num(8)
                .user(user)
                .rsEvent(event)
                .build();
        voteRepository.save(vote8);

        mockMvc.perform(get("/vote?userId=" + user.getId() + "&rsEventId=" + event.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));

        mockMvc.perform(get("/vote?userId=" + user.getId() + "&rsEventId=" + event.getId() + "&pageIndex=" + 2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    void shouldVoteGivenNumber(){
        UserEntity user = UserEntity.builder()
                .userName("user 0")
                .gender("male")
                .age(20)
                .email("12@34.com")
                .phone("13579246810")
                .voteNum(3)
                .build();
        user = userRepository.save(user);
        RsEventEntity event = RsEventEntity.builder()
                .eventName("event 0")
                .keyword("key")
                .userId(user.getId())
                .build();
        event = rsEventRepository.save(event);
        VoteEntity vote0 = VoteEntity.builder()
                .localDateTime(LocalDateTime.now())
                .num(0)
                .user(user)
                .rsEvent(event)
                .build();
        voteRepository.save(vote0);

        VoteEntity vote1 = VoteEntity.builder()
                .localDateTime(LocalDateTime.now())
                .num(1)
                .user(user)
                .rsEvent(event)
                .build();
        voteRepository.save(vote1);

        VoteEntity vote2 = VoteEntity.builder()
                .localDateTime(LocalDateTime.now())
                .num(2)
                .user(user)
                .rsEvent(event)
                .build();
        voteRepository.save(vote2);

        List<VoteEntity> votes= voteRepository.findAllByAaa(0);

        assertEquals(1, votes.size());
    }
}
