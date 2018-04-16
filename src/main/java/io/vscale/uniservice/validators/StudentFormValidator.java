package io.vscale.uniservice.validators;

import io.vscale.uniservice.domain.Group;
import io.vscale.uniservice.domain.Profile;
import io.vscale.uniservice.forms.rest.StudentForm;
import io.vscale.uniservice.repositories.data.GroupRepository;
import io.vscale.uniservice.repositories.data.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 11.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StudentFormValidator implements Validator{

    private ProfileRepository profileRepository;
    private GroupRepository groupRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.getName().equals(StudentForm.class.getName());
    }

    @Override
    public void validate(Object target, Errors errors) {

        StudentForm studentForm = (StudentForm) target;

        Optional<Profile> existedProfile =
                Optional.ofNullable(this.profileRepository.findOne(studentForm.getProfileId()));

        if(!existedProfile.isPresent()){
            errors.reject("bad.profileId", "Нет пользователя с таким id");
        }

        Optional<Group> existedGroup = this.groupRepository.findByTitle(studentForm.getGroupTitle());

        if(!existedGroup.isPresent()){
            errors.reject("bad.groupTitle", "Нет такой группы");
        }

        Set<String> genders = Stream.of("мужской", "женский").collect(Collectors.toSet());

        if(!genders.contains(studentForm.getGender())){
            errors.reject("bad.gender", "У человека нет такой половой принадлежности");
        }

        if(studentForm.getCourse() < 1 || studentForm.getCourse() > 4){
            errors.reject("invalid.course", "Неправильно введён номер курса");
        }

        Field[] fields = StudentForm.class.getDeclaredFields();

        List<String> fieldsNames = Arrays.stream(fields)
                                         .map(Field::getName)
                                         .collect(Collectors.toList());

        List<String> errorTypes = new ArrayList<>();
        List<String> errorDescriptions = new ArrayList<>();

        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        fieldsNames.forEach(fieldName ->{
            sb1.append("empty.").append(fieldName);
            errorTypes.add(sb1.toString());
            sb1.setLength(0);
        });

        fieldsNames.forEach(fieldName ->{
            sb2.append("Не заполнено поле для ").append(fieldName);
            errorDescriptions.add(sb2.toString());
            sb2.setLength(0);
        });

        AtomicInteger counter = new AtomicInteger();
        counter.set(0);

        fieldsNames.forEach(fieldName ->{
            int i = counter.getAndIncrement();
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, errorTypes.get(i), errorDescriptions.get(i));
        });

    }
}
