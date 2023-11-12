package com.ecsail.Gybe.service;

import com.ecsail.Gybe.dto.MembershipListDTO;
import com.ecsail.Gybe.repository.interfaces.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembershipService {
    private final MembershipRepository membershipRepository;

    @Autowired
    public MembershipService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    public MembershipListDTO getMembership(int msId) {
        return membershipRepository.getMembershipByMsId(msId);
    }

}
