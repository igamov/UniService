package io.vscale.uniservice.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vscale.uniservice.controllers.rest.admin.AdminRESTController;
import io.vscale.uniservice.domain.User;
import io.vscale.uniservice.forms.NewUserForm;
import io.vscale.uniservice.forms.ProfileForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * 06.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = {AdminRESTController.class})
@ActiveProfiles("test")
public class AdminRESTControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminRESTController adminRESTController;

    @Test
    public void shouldAddNewUser() throws Exception{

        NewUserForm newUserForm = NewUserForm.builder()
                                             .login("test")
                                             .password("123456")
                                             .role("USER")
                                             .build();

        User user = User.builder()
                        .login(newUserForm.getLogin())
                        .password(newUserForm.getPassword())
                        .build();

        BDDMockito.given(this.adminRESTController.addNewUser(newUserForm))
                  .willReturn(user);

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonInString = objectMapper.writeValueAsString(newUserForm);

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
