package service;

import com.glofox.dto.BookingDTO;
import com.glofox.dto.ClassDTO;
import com.glofox.service.ClassService;
import com.glofox.service.ClassServiceImpl;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ClassesServiceTest {

    private ClassService classService = new ClassServiceImpl();

    @Test
    public void testCreateClass() {
        List<ClassDTO> classDTOList = new ArrayList<>();
        classDTOList.add(getMockClassDTO());

        assert classService.createClassList(classDTOList).equals(Optional.empty());
    }

    @Test
    public void testGetAllClasses() {
        assert classService.getClassList() != null;
    }

    @Test
    public void testBookClass() {
        LocalDate classDate = LocalDate.parse("2019-12-20");
        BookingDTO bookingDTO = new BookingDTO("Test member", "Test class", classDate);
        List<ClassDTO> persistedClasses = new ArrayList<>();
        ClassDTO classDTO = getMockClassDTO();
        Map<Long, List<String>> membersClassDay = new HashMap<>();
        membersClassDay.put(LocalDate.parse("2019-12-20").toEpochDay(), new ArrayList<>());
        classDTO.setMembersClassDay(membersClassDay);
        persistedClasses.add(classDTO);

        ReflectionTestUtils.setField(classService, "persistedClasses", persistedClasses);
        assert classService.bookClass(bookingDTO).equals(Optional.empty());
    }

    private ClassDTO getMockClassDTO() {
        LocalDate startDate = LocalDate.parse("2019-12-01");
        LocalDate endDate = LocalDate.parse("2019-12-20");
        return new ClassDTO("Test class", startDate, endDate, 10);
    }
}
