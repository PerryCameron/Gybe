package com.ecsail.Gybe.wrappers;

import com.ecsail.Gybe.dto.LeadershipDTO;
import com.ecsail.Gybe.dto.ThemeDTO;

import java.util.List;

public class BoardOfDirectorsResponse {
    private List<LeadershipDTO> leadership;
    private ThemeDTO theme;

    public BoardOfDirectorsResponse(List<LeadershipDTO> leadership, ThemeDTO theme) {
        this.leadership = leadership;
        this.theme = theme;
    }

    // Getters and setters
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
}
