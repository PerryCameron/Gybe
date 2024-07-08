package com.ecsail.Gybe.service.interfaces;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface PDFService {

    void createDirectory(List<JsonNode> memberships);
}
