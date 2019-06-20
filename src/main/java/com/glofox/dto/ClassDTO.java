package com.glofox.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassDTO {
    private String className;
    private LocalDate startDate;
    private LocalDate endDate;
    private int capacity;
    private Map<Long, List<String>> membersClassDay = new HashMap<>();

    public ClassDTO() {
    }

    public ClassDTO(String className, LocalDate startDate, LocalDate endDate, int capacity) {
        this.className = className;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Map<Long, List<String>> getMembersClassDay() {
        return membersClassDay;
    }

    public void setMembersClassDay(Map<Long, List<String>> membersClassDay) {
        this.membersClassDay = membersClassDay;
    }
}
