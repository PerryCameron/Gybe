package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.MembershipListDTO;
import com.ecsail.Gybe.wrappers.RosterResponse;

import java.util.List;
import java.util.Map;

public interface RosterService {
    RosterResponse getDefaultRosterResponse();

    List<MembershipListDTO> getRoster(int year, String rosterType, String sort, List<String> searchParams);

    List<MembershipListDTO> getRosterType(int year, String rosterType, List<String> searchParams);

    List<MembershipListDTO> getSlipWait();

    RosterResponse getRosterResponse(String type, Integer year, Map<String, String> allParams);
}
