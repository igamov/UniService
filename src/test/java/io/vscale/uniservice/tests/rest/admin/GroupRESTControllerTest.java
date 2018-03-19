package io.vscale.uniservice.tests.rest.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vscale.uniservice.controllers.rest.admin.GroupRESTController;
import io.vscale.uniservice.domain.Group;
import io.vscale.uniservice.forms.rest.GroupForm;
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

import java.sql.Timestamp;
import java.util.Collections;

/**
 * 18.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = {GroupRESTController.class})
@ActiveProfiles("test")
public class GroupRESTControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupRESTController groupRESTController;

    @Test
    public void shouldAddNewGroup() throws Exception{

        GroupForm groupForm = GroupForm.builder()
                                       .title("11-604")
                                       .creationDate("25.02.17")
                                       .build();

        Group group = Group.builder()
                           .title(groupForm.getTitle())
                           .creationDate(Timestamp.valueOf(groupForm.getCreationDate()))
                           .build();

        BDDMockito.given(this.groupRESTController.addStudentsGroup(groupForm))
                  .willReturn(Collections.singletonList(group));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInString = objectMapper.writeValueAsString(groupForm);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api_v1/admin_role/add_group")
                                      .with(mockHttpServletRequest -> {
                                          mockHttpServletRequest.setContentType("application/json");
                                          mockHttpServletRequest.setContent(jsonInString.getBytes());
                                          return mockHttpServletRequest;
                                      })
                                      .accept(MediaType.APPLICATION_JSON)
                                      .contentType(MediaType.APPLICATION_JSON))
                                      .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }

}
