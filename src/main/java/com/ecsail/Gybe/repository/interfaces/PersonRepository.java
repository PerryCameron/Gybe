package com.ecsail.Gybe.repository.interfaces;


import com.ecsail.Gybe.dto.PersonDTO;

import java.util.List;

public interface PersonRepository {

//    List<PersonDTO> getPeople(int ms_id);

    List<PersonDTO> getActivePeopleByMsId(int ms_id);

    int update(PersonDTO personDTO);

    int insert(PersonDTO personDTO);

    int delete(PersonDTO personDTO);
    public List<PersonDTO> getChildrenByMsId(int msId);
}
