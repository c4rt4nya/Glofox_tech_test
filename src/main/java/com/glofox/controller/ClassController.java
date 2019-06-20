package com.glofox.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glofox.dto.BookingDTO;
import com.glofox.dto.ClassDTO;
import com.glofox.enums.ClassErrors;
import com.glofox.service.ClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static com.glofox.enums.ClassErrors.CLASSES_NULL;
import static com.glofox.enums.ClassErrors.CLASS_ALREADY_EXIST;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json; charset=UTF-8")
public class ClassController {

    private final static Logger log = Logger.getLogger(ClassController.class.getName());

    @Resource
    private ClassService classService;

    @GetMapping(value = "/classes")
    public ResponseEntity getAllClasses() {
        log.info("Retrieving all the classes");
        List<ClassDTO> classes = classService.getClassList();
        return ResponseEntity.ok().body(classes);
    }

    @PostMapping(value = "/classes")
    public ResponseEntity createClasses(@RequestBody List<ClassDTO> classes) {
        log.info("Creating a list of classes");
        ResponseEntity responseEntity;
        try {
            if (classes != null && !classes.isEmpty()) {
                Optional<List<String>> duplicatesOptional = classService.createClassList(classes);
                if (!duplicatesOptional.isPresent()) {
                    responseEntity = ResponseEntity.ok().build();
                } else {
                    responseEntity = ResponseEntity.badRequest().body(sendError(CLASS_ALREADY_EXIST) + " " + duplicatesOptional.get());
                }
            } else {
                responseEntity = ResponseEntity.badRequest().body(sendError(CLASSES_NULL));
            }
        } catch (JsonProcessingException e) {
            log.severe("Error when processing the classesError message");
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when processing the classesError message");
        }
        return responseEntity;
    }

    @PostMapping(value = "/bookings")
    public ResponseEntity createBooking(@RequestBody BookingDTO bookingDTO) {
        log.info("Booking a class");
        ResponseEntity responseEntity;
        Optional<ClassErrors> optionalErrors = classService.bookClass(bookingDTO);
        try {
            if (!optionalErrors.isPresent()) {
                responseEntity = ResponseEntity.ok().build();
            } else {
                responseEntity = ResponseEntity.badRequest().body(sendError(optionalErrors.get()));
            }
        } catch (JsonProcessingException e) {
            log.severe("Error when processing the classesError message");
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when processing the classesError message");
        }
        return responseEntity;
    }

    private String sendError(ClassErrors classesErrors) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(classesErrors.getDescription());
    }
}
