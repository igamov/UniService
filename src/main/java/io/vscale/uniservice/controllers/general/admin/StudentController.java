package io.vscale.uniservice.controllers.general.admin;

import io.vscale.uniservice.domain.Student;
import io.vscale.uniservice.services.interfaces.admin.StudentAdminService;
import io.vscale.uniservice.services.interfaces.student.StudentService;
import io.vscale.uniservice.utils.PageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import lombok.AllArgsConstructor;

/**
 * 05.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StudentController {

    private StudentService studentService;
    private StudentAdminService studentAdminService;

    @GetMapping("/students")
    public ModelAndView showStudents(@PageableDefault(value = 4) Pageable pageable){

        PageWrapper<Student> pageWrapper =
                new PageWrapper<>(this.studentService.findAll(pageable), "/admin/students");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/admin-students");
        modelAndView.addObject("pageWrapper", pageWrapper);

        return modelAndView;

    }

    @PostMapping("/students/asc")
    public ModelAndView showStudentsAsc(@PageableDefault(value = 4) Pageable pageable){

        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                                                  Sort.Direction.ASC, "profile.surname");
        PageWrapper<Student> pageWrapper =
                new PageWrapper<>(this.studentService.findAll(pageRequest), "/admin/students/asc");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/admin-students");
        modelAndView.addObject("pageWrapper", pageWrapper);

        return modelAndView;

    }

    @PostMapping("/students/desc")
    public ModelAndView showStudentsDesc(@PageableDefault(value = 4) Pageable pageable){

        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                                                  Sort.Direction.DESC, "profile.surname");
        PageWrapper<Student> pageWrapper =
                new PageWrapper<>(this.studentService.findAll(pageRequest), "/admin/students/desc");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/admin-students");
        modelAndView.addObject("pageWrapper", pageWrapper);

        return modelAndView;

    }

}
