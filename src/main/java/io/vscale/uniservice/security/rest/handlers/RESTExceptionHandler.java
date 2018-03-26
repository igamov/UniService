package io.vscale.uniservice.security.rest.handlers;

import io.vscale.uniservice.security.rest.errors.APIError;
import io.vscale.uniservice.security.rest.exceptions.EntityNotFoundException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

/**
 * 04.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@RestControllerAdvice
public class RESTExceptionHandler extends ResponseEntityExceptionHandler{

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request){

        String error = "Отсутствует параметр " + ex.getParameterName() + ", тип " + ex.getParameterType();
        APIError apiError = new APIError(HttpStatus.BAD_REQUEST, error, ex);
        return new ResponseEntity<>(apiError, apiError.getStatus());

    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request){

        StringBuilder sb = new StringBuilder();

        sb.append("Для данного запроса тип мультимедиа ").append(ex.getContentType()).append(" не поддерживается. ")
          .append("Поддерживаемые типы: ");

        ex.getSupportedMediaTypes()
          .forEach(mediaType -> sb.append(mediaType).append(", "));

        APIError apiError = new APIError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, sb.substring(0, sb.length() - 2), ex);
        return new ResponseEntity<>(apiError, apiError.getStatus());

    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request){

         StringBuilder sb = new StringBuilder();

         sb.append("В данном случае тип запроса ").append(ex.getMethod()).append(" не поддерживается. ")
           .append("Поддерживаемые типы: ");

         ex.getSupportedHttpMethods()
           .forEach(httpMethod -> sb.append(httpMethod).append(", "));

        APIError apiError = new APIError(HttpStatus.METHOD_NOT_ALLOWED, sb.substring(0, sb.length() - 2), ex);
        return new ResponseEntity<>(apiError, apiError.getStatus());

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request){

        APIError apiError = new APIError(HttpStatus.BAD_REQUEST);

        apiError.setMessage("Ошибка валидации");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
                                                         WebRequest request){

        APIError apiError = new APIError(HttpStatus.BAD_REQUEST);

        apiError.setMessage("Запрос подан к неверной сущности");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());

        return new ResponseEntity<>(apiError, apiError.getStatus());

    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex){

        APIError apiError = new APIError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request){

        String error = "Некорректное тело JSON-запроса";
        APIError apiError = new APIError(HttpStatus.BAD_REQUEST, error, ex);
        return new ResponseEntity<>(apiError, apiError.getStatus());

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request){

        String error = "Ошибка при создании тела JSON";
        APIError apiError = new APIError(HttpStatus.BAD_REQUEST, error, ex);
        return new ResponseEntity<>(apiError, apiError.getStatus());

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                                  WebRequest request){

        if(ex.getCause() instanceof ConstraintViolationException){
            APIError apiError = new APIError(HttpStatus.CONFLICT, "Произошла ошибка в БД", ex.getCause());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }

        APIError apiError = new APIError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        return new ResponseEntity<>(apiError, apiError.getStatus());

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request){

        String error = String.format("Параметр '%s' типа '%s' не конвертируется в тип '%s'", ex.getName(),
                                                                   ex.getValue(), ex.getRequiredType().getSimpleName());
        APIError apiError = new APIError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(error);
        apiError.setDebugMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());

    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request){

        String error = "Значение " + ex.getValue() + " для " + ex.getPropertyName() +
                " должно быть типа " + ex.getRequiredType();

        APIError apiError = new APIError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(error);
        apiError.setDebugMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());

    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request){

        String error = "В запросе нет заголовка " + ex.getRequestPartName();

        APIError apiError = new APIError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(error);
        apiError.setDebugMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());

    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request){

        String error = "Для " + ex.getHttpMethod() + " " + ex.getRequestURL() + " не найден нужный контроллер. " +

                "Введите его заново";

        APIError apiError = new APIError(HttpStatus.NOT_FOUND);
        apiError.setMessage(error);
        apiError.setDebugMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request){

        APIError apiError = new APIError(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setMessage("Произошла неизвестная ошибка");
        apiError.setDebugMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());

    }

}
