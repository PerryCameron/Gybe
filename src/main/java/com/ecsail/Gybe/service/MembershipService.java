package com.ecsail.Gybe.service;

import com.ecsail.Gybe.dto.MembershipListDTO;
import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.repository.interfaces.MembershipRepository;
import com.ecsail.Gybe.repository.interfaces.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MembershipService {
    private final MembershipRepository membershipRepository;
    private final PersonRepository personRepository;

    @Autowired
    public MembershipService(MembershipRepository membershipRepository, PersonRepository personRepository) {
        this.membershipRepository = membershipRepository;
        this.personRepository = personRepository;
    }

    public MembershipListDTO getMembership(int msId) {
        MembershipListDTO membership = membershipRepository.getMembershipByMsId(msId);
        membership.setPersonDTOS((ArrayList<PersonDTO>) personRepository.getActivePeopleByMsId(msId));
        return membership;
    }

}
