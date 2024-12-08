package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.repository.interfaces.*;
import com.ecsail.Gybe.service.interfaces.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final EmailRepository emailRepository;
    private final GeneralRepository generalRepository;
    private final PhoneRepository phoneRepository;
    private final OfficerRepository officerRepository;
    private final AwardRepository awardRepository;
    private final MembershipIdRepository membershipIdRepository;
    private final MembershipRepository membershipRepository;

    public PersonServiceImpl(AwardRepository awardRepository,
                             EmailRepository emailRepository,
                             PersonRepository personRepository,
                             GeneralRepository generalRepository,
                             PhoneRepository phoneRepository,
                             OfficerRepository officerRepository,
                             MembershipIdRepository membershipIdRepository,
                             MembershipRepository membershipRepository) {
        this.awardRepository = awardRepository;
        this.personRepository = personRepository;
        this.emailRepository = emailRepository;
        this.generalRepository = generalRepository;
        this.phoneRepository = phoneRepository;
        this.officerRepository = officerRepository;
        this.membershipIdRepository = membershipIdRepository;
        this.membershipRepository = membershipRepository;
    }

    @Override
    public boolean updatePerson(PersonDTO personDTO) {
        int success = personRepository.update(personDTO);
        return success > 0;
    }

    @Override
    public boolean batchUpdatePhones(List<PhoneDTO> phoneDTOList) {
        for(PhoneDTO phoneDTO : phoneDTOList) {
            System.out.println(phoneDTO);
        }
        int result = 0;
        if(phoneDTOList.size() == 1) {
            result = phoneRepository.update(phoneDTOList.get(0));
            if(result == 1) {
                System.out.println("Successfully updated phone");
            }
        }
        else if(phoneDTOList.size() > 1) {
            result = phoneRepository.batchUpdate(phoneDTOList);
            if(result == 1) {
                System.out.println("Successfully updated phone batch");
            }
        }
        return result > 0;
    }

    @Override
    public int insertNewPhoneRow(PhoneDTO phoneDTO) {
        return phoneRepository.insert(phoneDTO);
    }

    @Override
    public int insertNewPositionRow(OfficerDTO officerDTO) {
        return officerRepository.insert(officerDTO);
    }

    @Override
    public int insertNewEmailRow(EmailDTO emailDTO) {
        return emailRepository.insert(emailDTO);
    }

    @Override
    public int insertNewAwardRow(AwardDTO awardDTO) {
        return awardRepository.insert(awardDTO);
    }

    @Override
    public int insertNewMembershipId(MembershipIdDTO membershipIdDTO) {
        return membershipIdRepository.insert(membershipIdDTO);
    }

    @Override
    public boolean deletePhoneRow(PhoneDTO phoneDTO) {
        int success = phoneRepository.delete(phoneDTO);
        if (success > 0) return true;
        return false;
    }

    @Override
    public boolean deletePositionRow(OfficerDTO officerDTO) {
        int success = officerRepository.delete(officerDTO);
        if (success > 0) return true;
        return false;
    }

    @Override
    public boolean deleteEmailRow(EmailDTO emailDTO) {
        int result = emailRepository.delete(emailDTO);
        return result > 0; // Returns true if the row was successfully deleted, false otherwise
    }

    @Override
    public boolean deleteAwardRow(AwardDTO awardDTO) {
        int result = awardRepository.delete(awardDTO);
        return result > 0;
    }

    @Override
    public boolean deleteMembershipIdRow(MembershipIdDTO membershipIdDTO) {
        int result = membershipIdRepository.delete(membershipIdDTO);
        return result > 0;
    }

    @Override
    public boolean updateEmail(List<EmailDTO> emailDTOList) {
        if (emailDTOList == null || emailDTOList.isEmpty()) return false; // No updates to perform
        int result = 0;
        if(emailDTOList.size() == 1) result = emailRepository.update(emailDTOList.get(0));
        else if(emailDTOList.size() > 1) result = emailRepository.batchUpdate(emailDTOList);
        return result > 0;
    }

    @Override
    public boolean updatePositions(List<OfficerDTO> officerDTOList) {
        if (officerDTOList == null || officerDTOList.isEmpty()) return false; // No updates to perform
        int result = 0;
        if(officerDTOList.size() == 1) result = officerRepository.update(officerDTOList.get(0));
        else if(officerDTOList.size() > 1) result = officerRepository.batchUpdate(officerDTOList);
        return result > 0;
    }

    @Override
    public boolean updateAwards(List<AwardDTO> awardDTOS) {
        if (awardDTOS == null || awardDTOS.isEmpty()) return false; // No updates to perform
        int result = 0;
        if(awardDTOS.size() == 1) result = awardRepository.update(awardDTOS.get(0));
        else if(awardDTOS.size() > 1) result = awardRepository.batchUpdate(awardDTOS);
        return result > 0;
    }

    @Override
    public boolean updateMembershipIds(List<MembershipIdDTO> membershipIdDTOS) {
        if (membershipIdDTOS == null || membershipIdDTOS.isEmpty()) return false; // No updates to perform
        int result = 0;
        if(membershipIdDTOS.size() == 1) result = membershipIdRepository.update(membershipIdDTOS.get(0));
        else if(membershipIdDTOS.size() > 1) result = membershipIdRepository.batchUpdate(membershipIdDTOS);
        return result > 0;
    }

    @Override
    public boolean updateAddress(MembershipDTO membershipDTO) {
        if (membershipDTO == null) return false; // No updates to perform
        int result = membershipRepository.updateAddress(membershipDTO);
        return result > 0;
    }
}
