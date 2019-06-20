package com.glofox.service;

import com.glofox.dto.BookingDTO;
import com.glofox.dto.ClassDTO;
import com.glofox.enums.ClassErrors;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService {

    private static List<ClassDTO> persistedClasses = new ArrayList<>();

    @Override
    public List<ClassDTO> getClassList() {
        return persistedClasses;
    }

    @Override
    public Optional<List<String>> createClassList(List<ClassDTO> classes) {
        Optional<List<String>> optionalList = Optional.empty();
        List<String> classesNames = classes.stream().map(ClassDTO::getClassName).collect(Collectors.toList());
        List<String> duplicatedClasses = persistedClasses.stream()
                .filter(classObj -> classesNames.contains(classObj.getClassName()))
                .map(ClassDTO::getClassName).collect(Collectors.toList());
        //If there are duplicates it will return the classes names
        if (!duplicatedClasses.isEmpty()) {
            optionalList = Optional.of(duplicatedClasses);
        } else {
            for (ClassDTO classDTO : classes) {
                LocalDate day = classDTO.getStartDate();
                while (!day.isAfter(classDTO.getEndDate())) {
                    Map<Long, List<String>> map = classDTO.getMembersClassDay();
                    map.put(day.toEpochDay(), new ArrayList<>());
                    day = day.plusDays(1);
                }
            }
            persistedClasses.addAll(classes);
        }

        return optionalList;
    }

    @Override
    public Optional<ClassErrors> bookClass(BookingDTO bookingDTO) {
        Optional<ClassErrors> classesErrorsOptional = Optional.empty();
        Optional<ClassDTO> optionalClassFound = persistedClasses.stream()
                .filter(classDTO -> classDTO.getClassName().equals(bookingDTO.getClassName())).findFirst();
        if (optionalClassFound.isPresent()) {
            ClassDTO classDTO = optionalClassFound.get();
            long dateMilis = bookingDTO.getClassDate().toEpochDay();
            //If it contains the key means that the date is between start and end date
            if (classDTO.getMembersClassDay().containsKey(dateMilis)) {
                List<String> members = classDTO.getMembersClassDay().get(dateMilis);
                if (members.contains(bookingDTO.getMemberName())) {
                    classesErrorsOptional = Optional.of(ClassErrors.MEMBER_ALREADY_BOOKED);
                } else {
                    members.add(bookingDTO.getMemberName());
                }
            } else {
                classesErrorsOptional = Optional.of(ClassErrors.NOT_EXISTING_DATE);
            }
        } else {
            classesErrorsOptional = Optional.of(ClassErrors.NOT_EXISTING_CLASS);
        }
        return classesErrorsOptional;
    }
}
