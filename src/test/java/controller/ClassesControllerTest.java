package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.glofox.controller.ClassController;
import com.glofox.dto.BookingDTO;
import com.glofox.dto.ClassDTO;
import com.glofox.service.ClassService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ClassesControllerTest {

    @InjectMocks
    private ClassController classController;

    @Mock
    private ClassService classService;
    private MockMvc mvc;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(classController).build();
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testGetAllClasses() throws Exception {
        when(classService.getClassList()).thenReturn(new ArrayList<>());
        StringBuilder stringBuilder = new StringBuilder("/api/v1/classes");
        mvc.perform(MockMvcRequestBuilders.get(stringBuilder.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateClass() throws Exception {
        List<ClassDTO> classDTOList = new ArrayList<>();
        LocalDate startDate = LocalDate.parse("2019-12-01");
        LocalDate endDate = LocalDate.parse("2019-12-20");
        ClassDTO classDTO = new ClassDTO("Test class", startDate, endDate, 10);
        classDTOList.add(classDTO);

        when(classService.createClassList(any(List.class))).thenReturn(Optional.empty());
        StringBuilder stringBuilder = new StringBuilder("/api/v1/classes");
        mvc.perform(MockMvcRequestBuilders.post(stringBuilder.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(classDTOList)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateBooking() throws Exception {
        LocalDate classDate = LocalDate.parse("2019-12-01");
        BookingDTO bookingDTO = new BookingDTO("Test member", "Test class", classDate);

        when(classService.bookClass(any(BookingDTO.class))).thenReturn(Optional.empty());
        StringBuilder stringBuilder = new StringBuilder("/api/v1/bookings");
        mvc.perform(MockMvcRequestBuilders.post(stringBuilder.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(bookingDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
