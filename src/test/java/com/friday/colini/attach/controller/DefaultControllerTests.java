package com.friday.colini.attach.controller;

import org.hamcrest.Matchers;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DefaultControllerTests extends ControllerSupport {
    @Test
    public void healthCheck() throws Exception {
        final var result = mockMvc.perform(get("/health"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                ;

        assertThat(result.getRequest().getContentAsString()).isNull();
    }

    @Test
    public void notFound() throws Exception {
        mockMvc.perform(get("/sfasdfadfdasfadfdsfas"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("errCode").value(Matchers.is("404")))
                .andExpect(jsonPath("errMessage").value(Matchers.is("NOT_FOUND")))
        ;
    }
}
