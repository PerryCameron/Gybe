package com.ecsail.Gybe.repository.interfaces;



import com.ecsail.Gybe.dto.InvoiceWithMemberInfoDTO;
import com.ecsail.Gybe.dto.NotesDTO;

import java.util.List;

public interface NotesRepository {

    List<NotesDTO> getMemosByMsId(int ms_id);
    List<NotesDTO> getMemosByBoatId(int boat_id);
    NotesDTO getMemoByInvoiceIdAndCategory(InvoiceWithMemberInfoDTO invoice, String category);
    int insertNote(NotesDTO notesDTO);
    int update(NotesDTO notesDTO);
    int delete(NotesDTO notesDTO);
}
