package io.vscale.uniservice.controllers.rest.admin;

import io.vscale.uniservice.domain.Student;
import io.vscale.uniservice.forms.rest.StudentForm;
import io.vscale.uniservice.services.interfaces.admin.StudentAdminService;
import io.vscale.uniservice.services.interfaces.student.StudentService;
import io.vscale.uniservice.validators.StudentFormValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 17.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@RestController
@RequestMapping("/api_v1/admin_role")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StudentAdminRESTController {

    private StudentFormValidator studentFormValidator;
    private StudentAdminService studentAdminService;
    private StudentService studentService;

    @InitBinder
    public void initUserFormValidator(WebDataBinder binder){
        binder.addValidators(this.studentFormValidator);
    }

    @PostMapping("/create_student")
    public List<Student> createStudent(@RequestBody @Valid @ModelAttribute("studentForm")StudentForm studentForm){
        this.studentAdminService.makeRESTStudent(studentForm);
        return this.studentService.getAllStudents();
    }

}
