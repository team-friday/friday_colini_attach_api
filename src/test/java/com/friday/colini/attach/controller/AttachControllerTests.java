package com.friday.colini.attach.controller;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.friday.colini.attach.utils.ApiDocumentUtils.getDocumentRequest;
import static com.friday.colini.attach.utils.ApiDocumentUtils.getDocumentResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AttachControllerTests extends ControllerSupport {
    private static final String contentType = "text/plain";
    private static final byte[] content = "test".getBytes();

    @Test
    public void saveSingleAttachRequestTest() throws Exception {
        mockMvc.perform(fileUpload("/attach/attachments").file(getTestFile()))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("createdAt").exists())
                .andExpect(jsonPath("attachFiles").exists())
                .andExpect(jsonPath("attachFiles", hasSize(1)))

                .andExpect(jsonPath("attachFiles[*].id").exists())
                .andExpect(jsonPath("attachFiles[*].originalName").exists())
                .andExpect(jsonPath("attachFiles[*].contentType").exists())
                .andExpect(jsonPath("attachFiles[*].downloadCount").exists())
                .andExpect(jsonPath("attachFiles[*].lastDownloadAt").exists())
                .andExpect(jsonPath("attachFiles[*].createdAt").exists())

                .andDo(
                        document(
                                "attach/save-request",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                pathParameters(),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("Attach Request 식별자"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("파일 업로드 요청 날짜"),
                                        fieldWithPath("attachFiles").type(JsonFieldType.ARRAY).description("Attach File List"),

                                        fieldWithPath("attachFiles[*].id").type(JsonFieldType.NUMBER).description("Attach File 식별자"),
                                        fieldWithPath("attachFiles[*].originalName").type(JsonFieldType.STRING).description("파일 이름"),
                                        fieldWithPath("attachFiles[*].contentType").type(JsonFieldType.STRING).description("파일 Content Type"),
                                        fieldWithPath("attachFiles[*].downloadCount").type(JsonFieldType.NUMBER).description("다운로드 횟수"),
                                        fieldWithPath("attachFiles[*].lastDownloadAt").type(JsonFieldType.STRING).description("마지막 다운로드 날짜"),
                                        fieldWithPath("attachFiles[*].createdAt").type(JsonFieldType.STRING).description("파일 업로드 날짜")
                                )
                        )
                )
        ;
    }

    @Test
    public void saveMultiAttachRequestTest() throws Exception {
        mockMvc.perform(
                fileUpload("/attach/attachments")
                        .file(getTestFile())
                        .file(getTestFile())
                        .file(getTestFile())
        )
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("createdAt").exists())
                .andExpect(jsonPath("attachFiles").exists())
                .andExpect(jsonPath("attachFiles", hasSize(3)))

                .andExpect(jsonPath("attachFiles[*].id").exists())
                .andExpect(jsonPath("attachFiles[*].originalName").exists())
                .andExpect(jsonPath("attachFiles[*].contentType").exists())
                .andExpect(jsonPath("attachFiles[*].downloadCount").exists())
                .andExpect(jsonPath("attachFiles[*].lastDownloadAt").exists())
                .andExpect(jsonPath("attachFiles[*].createdAt").exists())

                .andDo(
                        document(
                                "attach/multi-save-request",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                pathParameters(),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("Attach Request 식별자"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("파일 업로드 요청 날짜"),
                                        fieldWithPath("attachFiles").type(JsonFieldType.ARRAY).description("Attach File List"),

                                        fieldWithPath("attachFiles[*].id").type(JsonFieldType.NUMBER).description("Attach File 식별자"),
                                        fieldWithPath("attachFiles[*].originalName").type(JsonFieldType.STRING).description("파일 이름"),
                                        fieldWithPath("attachFiles[*].contentType").type(JsonFieldType.STRING).description("파일 Content Type"),
                                        fieldWithPath("attachFiles[*].downloadCount").type(JsonFieldType.NUMBER).description("다운로드 횟수"),
                                        fieldWithPath("attachFiles[*].lastDownloadAt").type(JsonFieldType.STRING).description("마지막 다운로드 날짜"),
                                        fieldWithPath("attachFiles[*].createdAt").type(JsonFieldType.STRING).description("파일 업로드 날짜")
                                )
                        )
                )
        ;
    }

    @Test
    public void findAttachRequestTest() throws Exception {
        // Given
        final var result = mockMvc.perform(
                fileUpload("/attach/attachments").file(getTestFile())
        ).andReturn();
        final var attachRequestId = objectMapper.readTree(result.getResponse().getContentAsByteArray()).get("id").asLong();

        // When / Then
        mockMvc.perform(
                get("/attach/attachments/{attachRequestId}", attachRequestId)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("createdAt").exists())
                .andExpect(jsonPath("attachFiles").exists())
                .andExpect(jsonPath("attachFiles", hasSize(1)))

                .andExpect(jsonPath("attachFiles[*].id").exists())
                .andExpect(jsonPath("attachFiles[*].originalName").exists())
                .andExpect(jsonPath("attachFiles[*].contentType").exists())
                .andExpect(jsonPath("attachFiles[*].downloadCount").exists())
                .andExpect(jsonPath("attachFiles[*].lastDownloadAt").exists())
                .andExpect(jsonPath("attachFiles[*].createdAt").exists())

                .andDo(
                        document(
                                "attach/save-request-find",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                pathParameters(
                                        parameterWithName("attachRequestId").description("Attach Request 식별자")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("Attach Request 식별자"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("파일 업로드 요청 날짜"),
                                        fieldWithPath("attachFiles").type(JsonFieldType.ARRAY).description("Attach File List"),

                                        fieldWithPath("attachFiles[*].id").type(JsonFieldType.NUMBER).description("Attach File 식별자"),
                                        fieldWithPath("attachFiles[*].originalName").type(JsonFieldType.STRING).description("파일 이름"),
                                        fieldWithPath("attachFiles[*].contentType").type(JsonFieldType.STRING).description("파일 Content Type"),
                                        fieldWithPath("attachFiles[*].downloadCount").type(JsonFieldType.NUMBER).description("다운로드 횟수"),
                                        fieldWithPath("attachFiles[*].lastDownloadAt").type(JsonFieldType.STRING).description("마지막 다운로드 날짜"),
                                        fieldWithPath("attachFiles[*].createdAt").type(JsonFieldType.STRING).description("파일 업로드 날짜")
                                )
                        )
                )
        ;
    }

    @Test
    public void notExistsAttachRequestTest() throws Exception {
        mockMvc.perform(
                get("/attach/attachments/{attachRequestId}", 1234567890)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errCode").value(Matchers.is("400")))
                .andExpect(jsonPath("errMessage").value(Matchers.is("BAD_REQUEST")))

                .andDo(
                        document(
                                "attach/save-request-not-found",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                pathParameters(
                                        parameterWithName("attachRequestId").description("Attach Request 식별자")
                                ),
                                responseFields(
                                        fieldWithPath("errCode").type(JsonFieldType.STRING).description("에러 코드"),
                                        fieldWithPath("errMessage").type(JsonFieldType.STRING).description("에러 메시지")
                                )
                        )
                )
        ;
    }

    @Test
    public void downloadAttachFileTest() throws Exception {
        // Given
        final var result = mockMvc.perform(fileUpload("/attach/attachments").file(getTestFile())).andReturn();

        final var attachId = objectMapper.readTree(result.getResponse().getContentAsByteArray()).get("id").asLong();
        final var fileId = objectMapper.readTree(result.getResponse().getContentAsByteArray()).get("attachFiles").get(0).get("id").asLong();

        // When / Then
        mockMvc.perform(
                get("/attach/attachments/{attachRequestId}/files/{attachFileId}" , attachId, fileId)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_OCTET_STREAM)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().bytes(content))

                .andDo(
                        document(
                                "attach/download-attach-file",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                pathParameters(
                                        parameterWithName("attachRequestId").description("Attach Request 식별자"),
                                        parameterWithName("attachFileId").description("Attach File 식별자")
                                )
                        )
                )
        ;
    }

    @Test
    public void notFoundAttachFileTest() throws Exception {
        // Given
        final var result = mockMvc.perform(fileUpload("/attach/attachments").file(getTestFile())).andReturn();

        final var attachId = objectMapper.readTree(result.getResponse().getContentAsByteArray()).get("id").asLong();

        // When / Then
        mockMvc.perform(
                get("/attach/attachments/{attachRequestId}/files/{attachFileId}" , attachId, 1234567890)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_OCTET_STREAM)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())

                .andDo(
                        document(
                                "attach/not-found-download-attach-file",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                pathParameters(
                                        parameterWithName("attachRequestId").description("Attach Request 식별자"),
                                        parameterWithName("attachFileId").description("Attach File 식별자")
                                ),
                                responseFields(
                                        fieldWithPath("errCode").type(JsonFieldType.STRING).description("에러 코드"),
                                        fieldWithPath("errMessage").type(JsonFieldType.STRING).description("에러 메시지")
                                )
                        )
                )
        ;
    }

    @Test
    public void downloadIncrementAttachFileTest() throws Exception {
        // Given
        final var saveResult = mockMvc.perform(fileUpload("/attach/attachments").file(getTestFile())).andReturn();

        final var attachId = objectMapper.readTree(saveResult.getResponse().getContentAsByteArray()).get("id").asLong();
        final var fileId = objectMapper.readTree(saveResult.getResponse().getContentAsByteArray()).get("attachFiles").get(0).get("id").asLong();

        // When / Then
        mockMvc.perform(get("/attach/attachments/" + attachId + "/files/" + fileId).contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_OCTET_STREAM))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().bytes(content))
        ;

        final var downloadResult = mockMvc.perform(get("/attach/attachments/" + attachId).contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(print()).andReturn();
        final var downloadCount = objectMapper.readTree(downloadResult.getResponse().getContentAsByteArray()).get("attachFiles").get(0).get("downloadCount").asLong();

        assertThat(downloadCount).isEqualTo(1);
    }

    //
    //
    //

    private MockMultipartFile getTestFile() {
        return new MockMultipartFile(
                "files",
                "testfile",
                contentType,
                content
        );
    }
}