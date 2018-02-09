package com.management.money.monspier.infrastructure.web;

import com.management.money.monspier.core.ErrorState;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public final class ErrorHandlerResponseBodyAdvice {
  private static final Pattern FOREIGN_KEY_CONSTRAINT =
      Pattern.compile("((.|\\n|\\r)+)Key \\((.+)\\)=\\((.+)\\) is not present in table(.+)");

  /**
   * Handles validation errors and translates to response.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorState handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {

    ErrorState errorState = new ErrorState();
    for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
      errorState.addError(fe.getField(), fe.getDefaultMessage());
    }

    return errorState;
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorState handleHttpMessageNotReadableException(
      HttpMessageNotReadableException ex) {

    ErrorState errorState = new ErrorState();
    errorState.addError("invalid request, please see documentation");
    return errorState;
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorState handleDataIntegrityViolationException(
      DataIntegrityViolationException ex) {

    ErrorState errorState = new ErrorState();
    String localizedMessage = ex.getRootCause().getLocalizedMessage();
    Matcher matcher = FOREIGN_KEY_CONSTRAINT.matcher(localizedMessage);
    if (matcher.find()) {
        errorState.addError(matcher.group(3),
                            String.format("Value %s is not present", matcher.group(4)));
        return errorState;
    }

    errorState.addError(localizedMessage);
    return errorState;
  }
}