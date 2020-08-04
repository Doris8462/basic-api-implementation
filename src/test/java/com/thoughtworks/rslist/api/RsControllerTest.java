package com.thoughtworks.rslist.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class RsControllerTest {
    MockMvc mocMvc;

    void shouldGetPsList() throws Exception {
        mocMvc.perform(get("/rs/list"))
                .andExpect(content().string("[第一事件,第二事件,第三事件]"))
                .andExpect(status().isOk());

    }
}