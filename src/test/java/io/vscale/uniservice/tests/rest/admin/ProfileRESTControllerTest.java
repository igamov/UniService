package io.vscale.uniservice.tests.rest.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vscale.uniservice.controllers.rest.admin.ProfileRESTController;
import io.vscale.uniservice.forms.rest.ProfileForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * 18.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = {ProfileRESTController.class})
@ActiveProfiles("test")
public class ProfileRESTControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldAttachProfileToUser() throws Exception{

        ProfileForm profileForm = ProfileForm.builder()
                                             .name("test")
                                             .surname("test")
                                             .userId(1L)
                                             .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInString = objectMapper.writeValueAsString(profileForm);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api_v1/admin/add_user")
                                      .with(mockHttpServletRequest -> {
                                                  mockHttpServletRequest.setContentType("application/json");
                                                  mockHttpServletRequest.setContent(jsonInString.getBytes());
                                                  return mockHttpServletRequest;
                                              }
                                      )
                                      .accept(MediaType.APPLICATION_JSON)
                                      .contentType(MediaType.APPLICATION_JSON))
                                      .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }

}
