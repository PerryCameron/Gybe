package com.ecsail.Gybe.wrappers;

import com.ecsail.Gybe.dto.LeadershipDTO;
import com.ecsail.Gybe.dto.ThemeDTO;

import java.util.List;

public class BoardOfDirectorsResponse {
    private List<LeadershipDTO> leadership;
    private ThemeDTO theme;
    private int year;

    public BoardOfDirectorsResponse(List<LeadershipDTO> leadership, ThemeDTO theme, int year) {
        this.leadership = leadership;
        this.theme = theme;
        this.year = year;
    }
    public BoardOfDirectorsResponse() {
    }

    public List<LeadershipDTO> getLeadership() {
        return leadership;
    }

    public void setLeadership(List<LeadershipDTO> leadership) {
        this.leadership = leadership;
    }

    public ThemeDTO getTheme() {
        return theme;
    }

    public void setTheme(ThemeDTO theme) {
        this.theme = theme;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
