package com.ecsail.Gybe.dto;

import com.ecsail.Gybe.pdf.enums.Pages;

public class PersonListDTO {

    int year;
    String name;
    Pages listType;

    public PersonListDTO(int year, String name, Pages listType) {
        this.year = year;
        this.name = name;
        this.listType = listType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pages getListType() {
        return listType;
    }

    public void setListType(Pages listType) {
        this.listType = listType;
    }
}
