package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.FeeDTO;
import com.ecsail.Gybe.repository.interfaces.InvoiceRepository;
import com.ecsail.Gybe.service.interfaces.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
public class FeeServiceImpl implements FeeService {
    private InvoiceRepository invoiceRepository;

    @Autowired
    public FeeServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<FeeDTO> getFees() {
        return invoiceRepository.getFeesFromYear(Year.now().getValue());
    }


}
