package com.softserve.edu.controller;

import com.softserve.edu.exception.EntityNotFoundException;
import com.softserve.edu.exception.MarathonDependencyWarningException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerMaster {

    @ExceptionHandler({EntityNotFoundException.class, IllegalArgumentException.class, MarathonDependencyWarningException.class})
    public ModelAndView handleMyCustomException(HttpServletRequest request, Exception exception) {
        log.error("Error occurred on path: {}; Status code: {}"
                , request.getRequestURL().toString(), exception.getLocalizedMessage());
        ModelAndView model = new ModelAndView("/error-pages/error_page");
        model.addObject("info", exception.getMessage());
        model.setStatus(HttpStatus.BAD_REQUEST);
        return model;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleMyCustomException(HttpServletRequest request, DataIntegrityViolationException exception) {
        log.error("Error occurred on path: {}; Status code: {}"
                , request.getRequestURL().toString(), exception.getMostSpecificCause().toString());
        ModelAndView mav = new ModelAndView("/error-pages/error_page");
        String message = "User with this email already exists. Please use another email address.";
        mav.addObject("info", message);
        mav.setStatus(HttpStatus.BAD_REQUEST);
        return mav;
    }

}
