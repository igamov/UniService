package io.vscale.uniservice.controllers.rest.student;

import io.vscale.uniservice.domain.Group;
import io.vscale.uniservice.domain.Student;
import lombok.AllArgsConstructor;

import io.vscale.uniservice.services.interfaces.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 04.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@RestController
@RequestMapping("/api_v1/student_role")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StudentRESTController {

    private StudentService studentService;

    @GetMapping("/show/group")
    public List<Student> getStudentByGroup(@RequestBody Group group){
        return this.studentService.getStudentsByGroup(group);
    }

}
