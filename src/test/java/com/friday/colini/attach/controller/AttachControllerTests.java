package com.friday.colini.attach.controller;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AttachControllerTests extends ControllerSupport {
    private static final String contentType = "text/plain";
    private static final byte[] content = "test".getBytes();

    @Test
    public void saveSingleAttachRequestTest() throws Exception {
        mockMvc.perform(multipart("/attach").file(getTestFile()))
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
        ;
    }

    @Test
    public void saveMultiAttachRequestTest() throws Exception {
        mockMvc.perform(multipart("/attach").file(getTestFile()).file(getTestFile()).file(getTestFile()))
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
        ;
    }

    @Test
    public void findAttachRequestTest() throws Exception {
        // Given
        final var result = mockMvc.perform(multipart("/attach").file(getTestFile())).andReturn();
        final var attachId = objectMapper.readTree(result.getResponse().getContentAsByteArray()).get("id").asLong();

        // When / Then
        mockMvc.perform(get("/attach/" + attachId).contentType(MediaType.APPLICATION_JSON_UTF8))
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
        ;
    }

    @Test
    public void notExistsAttachRequestTest() throws Exception {
        mockMvc.perform(get("/attach/12345678").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errCode").value(Matchers.is("400")))
                .andExpect(jsonPath("errMessage").value(Matchers.is("BAD_REQUEST")))
        ;
    }

    @Test
    public void downloadAttachFileTest() throws Exception {
        // Given
        final var result = mockMvc.perform(multipart("/attach").file(getTestFile())).andReturn();

        final var attachId = objectMapper.readTree(result.getResponse().getContentAsByteArray()).get("id").asLong();
        final var fileId = objectMapper.readTree(result.getResponse().getContentAsByteArray()).get("attachFiles").get(0).get("id").asLong();

        // When / Then
        mockMvc.perform(get("/attach/" + attachId + "/files/" + fileId).contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_OCTET_STREAM))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().bytes(content))
        ;
    }


    @Test
    public void downloadIncrementAttachFileTest() throws Exception {
        // Given
        final var saveResult = mockMvc.perform(multipart("/attach").file(getTestFile())).andReturn();

        final var attachId = objectMapper.readTree(saveResult.getResponse().getContentAsByteArray()).get("id").asLong();
        final var fileId = objectMapper.readTree(saveResult.getResponse().getContentAsByteArray()).get("attachFiles").get(0).get("id").asLong();

        // When / Then
        mockMvc.perform(get("/attach/" + attachId + "/files/" + fileId).contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_OCTET_STREAM))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().bytes(content))
        ;

        final var downloadResult = mockMvc.perform(get("/attach/" + attachId).contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(print()).andReturn();
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