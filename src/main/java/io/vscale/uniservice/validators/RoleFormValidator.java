package io.vscale.uniservice.validators;

import io.vscale.uniservice.domain.RoleType;
import io.vscale.uniservice.domain.User;
import io.vscale.uniservice.forms.rest.RoleForm;
import io.vscale.uniservice.repositories.data.RoleTypeRepository;
import io.vscale.uniservice.repositories.data.UserRepository;
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
 * 11.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Component
public class RoleFormValidator implements Validator{

    private final UserRepository userRepository;
    private final RoleTypeRepository roleTypeRepository;

    @Autowired
    public RoleFormValidator(UserRepository userRepository, RoleTypeRepository roleTypeRepository) {
        this.userRepository = userRepository;
        this.roleTypeRepository = roleTypeRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.getName().equals(RoleForm.class.getName());
    }

    @Override
    public void validate(Object target, Errors errors) {

        RoleForm roleForm = (RoleForm) target;

        Optional<User> existedUser = Optional.ofNullable(this.userRepository.findOne(roleForm.getUserId()));
        Optional<RoleType> existedRole = Optional.ofNullable(this.roleTypeRepository.findOne(roleForm.getRoleId()));

        if(!existedUser.isPresent()){
            errors.reject("bad.userId", "Пользователь с таким id не найден");
        }

        if(!existedRole.isPresent()){
            errors.reject("bad.roleId", "Роль с таким id не найдена");
        }

        Field[] fields = RoleForm.class.getDeclaredFields();
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
