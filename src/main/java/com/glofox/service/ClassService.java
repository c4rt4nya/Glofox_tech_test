package com.glofox.service;

import com.glofox.dto.BookingDTO;
import com.glofox.dto.ClassDTO;
import com.glofox.enums.ClassErrors;

import java.util.List;
import java.util.Optional;

public interface ClassService {

    List<ClassDTO> getClassList();

    Optional<List<String>> createClassList(List<ClassDTO> classes);

    Optional<ClassErrors> bookClass(BookingDTO bookingDTO);

}
