package shop.gaship.gashipfront.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipfront.tag.controller.TagController;
import shop.gaship.gashipfront.tag.service.TagService;
import shop.gaship.gashipfront.testDummy.FrontTagTestDummy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * packageName    : shop.gaship.gashipfront.controller
 * fileName       : TagControllerTest
 * author         : choijungwoo
 * date           : 2022/07/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/15        choijungwoo       최초 생성
 */
@WebMvcTest(
        controllers = TagController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                OAuth2ClientAutoConfiguration.class
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class})
        }
)
//    @WebMvcTest(TagController.class)
//@ExtendWith(SpringExtension.class)
//@Import(value = {CustomUserDetailService.class})
//@EnableConfigurationProperties(ServerConfig.class)
class TagControllerTest {
    @MockBean
    TagService tagService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @DisplayName("test")
    @Test
    void registerTag() throws Exception {
        doNothing().when(tagService).register(any(), any());
        String body = objectMapper.writeValueAsString(FrontTagTestDummy.CreateTestTagRegisterRequestDto());

        mockMvc.perform(post("/admins/1/tags")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());

        verify(tagService, times(1)).register(any(), any());
    }

    @Test
    void getTag() throws Exception {
        when(tagService.getTag(any(), any())).thenReturn(null);

        mockMvc.perform(get("/admins/1/tags/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());

        verify(tagService, times(1)).getTag(any(), any());
    }

    @Test
    void getTags() throws Exception {
        doNothing().when(tagService).register(any(), any());

        mockMvc.perform(get("/admins/1/tags")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());

        verify(tagService, times(1)).getTags(any(), any(), any());
    }

    @Test
    void modifyTag() throws Exception {
        doNothing().when(tagService).modifyTag(any(), any());
        String body = objectMapper.writeValueAsString(FrontTagTestDummy.CreateTestTagModifyRequestDto());

        mockMvc.perform(put("/admins/1/tags/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());

        verify(tagService, times(1)).modifyTag(any(), any());
    }

    @Test
    void deleteTag() throws Exception {
        doNothing().when(tagService).register(any(), any());

        mockMvc.perform(post("/admins/1/tags/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());

        verify(tagService, times(1)).deleteTag(any(), any());
    }
}