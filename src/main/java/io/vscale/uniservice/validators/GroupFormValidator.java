package io.vscale.uniservice.validators;

import io.vscale.uniservice.domain.Group;
import io.vscale.uniservice.forms.rest.GroupForm;
import io.vscale.uniservice.repositories.data.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 11.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Component
public class GroupFormValidator implements Validator{

    private final GroupRepository groupRepository;

    @Autowired
    public GroupFormValidator(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.getName().equals(GroupForm.class.getName());
    }

    @Override
    public void validate(Object target, Errors errors) {

        GroupForm groupForm = (GroupForm) target;
        Optional<Group> existedGroup = this.groupRepository.findByTitle(groupForm.getTitle());

        if(existedGroup.isPresent()){
            errors.reject("invalid.group", "Такая группа уже существует");
        }

        Field[] fields = GroupForm.class.getDeclaredFields();

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

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        try{
            Date date = formatter.parse(groupForm.getCreationDate());
        }catch(ParseException e){
            errors.reject("invalid.date", "Неверно введена дата создания");
        }

    }
}
