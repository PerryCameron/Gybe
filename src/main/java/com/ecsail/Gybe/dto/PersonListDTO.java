package com.ecsail.Gybe.dto;

import com.ecsail.Gybe.pdf.enums.Pages;

public class PersonListDTO {

    int year;
    String name;
    Pages pageType;

    public PersonListDTO(int year, String name, Pages pageType) {
        this.year = year;
        this.name = name;
        this.pageType = pageType;
    }

    public String getFullLine() {
        return  year + " " + name;
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

    public Pages getPageType() {
        return pageType;
    }

    public void setPageType(Pages pageType) {
        this.pageType = pageType;
    }
}
