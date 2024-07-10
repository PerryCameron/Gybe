package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.service.interfaces.FontService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FontServiceImpl implements FontService {
    @Value("${app.font.location}")
    private String fontLocation;

    @Override
    public String getFontLocation() {
        return fontLocation;
    }
}
