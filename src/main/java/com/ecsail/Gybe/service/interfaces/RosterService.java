package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.MembershipListDTO;

import java.util.List;

public interface RosterService {
    List<MembershipListDTO> getRoster(int year, String rosterType, String sort, List<String> searchParams);

    List<MembershipListDTO> getRosterType(int year, String rosterType, List<String> searchParams);
}
