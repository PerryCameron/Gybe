package com.ecsail.Gybe.dto;

import com.ecsail.Gybe.pdf.enums.Pages;

public class PersonListDTO {

    int year;
    String firstName;
    String lastName;
    Pages listType;

    public PersonListDTO(int year, String firstName, String lastName, Pages listType) {
        this.year = year;
        this.firstName = firstName;
        this.lastName = lastName;
        this.listType = listType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Pages getListType() {
        return listType;
    }

    public void setListType(Pages listType) {
        this.listType = listType;
    }
}
