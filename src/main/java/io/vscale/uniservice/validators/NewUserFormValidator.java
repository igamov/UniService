package io.vscale.uniservice.validators;

import io.vscale.uniservice.forms.NewUserForm;
import io.vscale.uniservice.domain.User;
import io.vscale.uniservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public class NewUserFormValidator implements Validator{

    private UserRepository userRepository;

    @Autowired
    private NewUserFormValidator(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.getName().equals(NewUserForm.class.getName());
    }

    @Override
    public void validate(Object target, Errors errors) {

        NewUserForm newUserForm = (NewUserForm) target;

        Field[] fields = NewUserForm.class.getDeclaredFields();

        Optional<User> existedUser = this.userRepository.findByLogin(newUserForm.getLogin());

        existedUser.ifPresent(user ->
                errors.reject("bad.login", "Пользователь с логином " + newUserForm.getLogin() + " уже существует"));

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
