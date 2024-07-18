package com.ecsail.Gybe.repository.interfaces;


import com.ecsail.Gybe.dto.CommodoreMessageDTO;
import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.dto.PersonListDTO;

import java.util.List;

public interface PersonRepository {

//    List<PersonDTO> getPeople(int ms_id);

    List<PersonDTO> getActivePeopleByMsId(int ms_id);

    int update(PersonDTO personDTO);

    int insert(PersonDTO personDTO);

    int delete(PersonDTO personDTO);
    public List<PersonDTO> getChildrenByMsId(int msId);

    PersonDTO getPersonByEmail(String email);

    CommodoreMessageDTO getCommodoreMessageByYear(int year);

    List<PersonListDTO> getAllCommodores();

    List<PersonListDTO> getAllSportsManOfTheYear();
}
