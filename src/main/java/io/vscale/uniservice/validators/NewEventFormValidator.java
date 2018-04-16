package io.vscale.uniservice.validators;

import io.vscale.uniservice.forms.general.NewEventForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 18.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Component
public class NewEventFormValidator implements Validator{

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.getName().equals(NewEventForm.class.getName());
    }

    @Override
    public void validate(Object target, Errors errors) {

        NewEventForm form = (NewEventForm)target;

        Field[] fields = NewEventForm.class.getDeclaredFields();

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

        if(!(form.getEventDate().length() == 10)){
            errors.rejectValue("eventDate", "Неверно введена дата");
        }

        String dateRegex =
                "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))" +
                        "(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?" +
                        "(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]" +
                        "|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

        Pattern pattern = Pattern.compile(dateRegex);
        Matcher matcher = pattern.matcher(form.getEventDate());

        if(!matcher.matches()){
            errors.rejectValue("eventDate", "Неверно введена дата");
        }

        String dateCheck = form.getEventDate().substring(0, 4);

        LocalDate localDate = LocalDate.now();
        StringBuilder sb = new StringBuilder();
        sb.append(localDate.getDayOfMonth() < 10 ? "0" + localDate.getDayOfMonth() : localDate.getDayOfMonth())
          .append(localDate.getMonth().getValue());

        String semesterDate11 = "01.09";
        String semesterDate12 = "27.12";
        String semesterDate21 = "08.02";
        String semesterDate22 = "25.05";

        String date11 = "01.09";
        String date12 = "31.10";
        String date211 = "01.11";
        String date212 = "27.12";
        String date221 = "08.02";
        String date222 = "01.04";

        int semesterCheck11 = sb.toString().compareToIgnoreCase(semesterDate11);
        int semesterCheck12 = sb.toString().compareToIgnoreCase(semesterDate12);
        int semesterCheck21 = sb.toString().compareToIgnoreCase(semesterDate21);
        int semesterCheck22 = sb.toString().compareToIgnoreCase(semesterDate22);

        int firstCheck1 = dateCheck.compareToIgnoreCase(date11);
        int firstCheck2 = dateCheck.compareToIgnoreCase(date12);

        int secondCheck11 = dateCheck.compareToIgnoreCase(date211);
        int secondCheck12 = dateCheck.compareToIgnoreCase(date212);
        int secondCheck21 = dateCheck.compareToIgnoreCase(date221);
        int secondCheck22 = dateCheck.compareToIgnoreCase(date222);

        if(semesterCheck11 > 0 && semesterCheck12 < 0){
            if(!(firstCheck1 > 0 && firstCheck2 < 0)){
                errors.rejectValue("eventDate", "Мероприятие не проходит по дате проведения");
            }
        }else if(semesterCheck21 > 0 && semesterCheck22 < 0){

            if(!( (secondCheck11 > 0 && secondCheck12 < 0) || (secondCheck21 > 0 && secondCheck22 < 0))){
                errors.rejectValue("eventDate", "Мероприятие не проходит по дате проведения");
            }

        }else{
            errors.rejectValue("eventDate", "Неверно введена дата");
        }

    }
}
