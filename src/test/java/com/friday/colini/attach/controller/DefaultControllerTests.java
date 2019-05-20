package com.friday.colini.attach.controller;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.friday.colini.attach.utils.ApiDocumentUtils.getDocumentResponse;
import static com.friday.colini.attach.utils.ApiDocumentUtils.getDocumentRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DefaultControllerTests extends ControllerSupport {
    @Test
    public void healthCheck() throws Exception {
        final var result = mockMvc.perform(get("/health"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "default/health-check",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                pathParameters()
                        )
                )
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
                .andDo(
                        document(
                                "default/not-found",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                pathParameters(),
                                responseFields(
                                        fieldWithPath("errCode").type(JsonFieldType.STRING).description("에러 코드"),
                                        fieldWithPath("errMessage").type(JsonFieldType.STRING).description("에러 메시지")
                                )
                        )
                )
        ;
    }
}
