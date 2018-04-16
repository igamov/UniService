package io.vscale.uniservice.tests.rest.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vscale.uniservice.controllers.rest.student.StudentRESTController;
import io.vscale.uniservice.domain.Group;
import io.vscale.uniservice.domain.Student;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * 06.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = {StudentRESTController.class})
@ActiveProfiles("test")
public class StudentRESTControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRESTController studentRESTController;

    @Test
    public void shouldGetStudentsByPreciseExplanationOfGroup() throws Exception{

        Group group = Group.builder()
                           .title("11-604")
                           .build();

        Student stud1 = Student.builder()
                               .gender("male")
                               .groups(Collections.singleton(group))
                               .build();

        Student stud2 = Student.builder()
                               .gender("female")
                               .groups(Collections.singleton(group))
                               .build();


        BDDMockito.given(this.studentRESTController.getStudentByGroup(group))
                  .willReturn(new ArrayList<>(Arrays.asList(stud1, stud2)));

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonInString = objectMapper.writeValueAsString(group);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api_v1/student_role/show/group")
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
