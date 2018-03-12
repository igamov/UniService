package io.vscale.uniservice.validators;

import io.vscale.uniservice.domain.Profile;
import io.vscale.uniservice.domain.User;
import io.vscale.uniservice.forms.ProfileForm;
import io.vscale.uniservice.repositories.ProfileRepository;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 11.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Component
public class ProfileFormValidator implements Validator{

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    private final String EMAIL_REGEXP =
            "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    @Autowired
    public ProfileFormValidator(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.getName().equals(ProfileForm.class.getName());
    }

    @Override
    public void validate(Object target, Errors errors) {

        ProfileForm profileForm = (ProfileForm) target;

        Optional<User> existedUser = Optional.ofNullable(this.userRepository.findOne(profileForm.getUserId()));

        if(!existedUser.isPresent()){
            errors.reject("invalid.userId", "Пользователь с таким id не найден");
        }

        Optional<Profile> existedProfile =
                this.profileRepository.findBySurnameAndName(profileForm.getSurname(), profileForm.getName());

        if(existedProfile.isPresent()){
            errors.reject("invalid.profile", "Такой профиль уже существует");
        }

        Field[] fields = ProfileForm.class.getDeclaredFields();

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

        Pattern pattern = Pattern.compile(this.EMAIL_REGEXP);
        Matcher matcher = pattern.matcher(profileForm.getEmail());

        if(!matcher.matches()){
            errors.reject("invalid.email", "Неправильно введён адрес электронной почты");
        }
    }
}
