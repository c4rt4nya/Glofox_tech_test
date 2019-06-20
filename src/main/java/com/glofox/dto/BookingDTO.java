package com.glofox.dto;

import java.time.LocalDate;

public class BookingDTO {
    private String memberName;
    private String className;
    private LocalDate classDate;

    public BookingDTO() {
    }

    public BookingDTO(String memberName, String className, LocalDate classDate) {
        this.memberName = memberName;
        this.className = className;
        this.classDate = classDate;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public LocalDate getClassDate() {
        return classDate;
    }

    public void setClassDate(LocalDate classDate) {
        this.classDate = classDate;
    }
}
