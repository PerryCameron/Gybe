package com.ecsail.Gybe.repository.rowmappers;





import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.JsonParser;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class JsonRowMapper implements RowMapper<JsonNode> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public JsonNode mapRow(ResultSet rs, int rowNum) throws SQLException {
        String jsonString = rs.getString("membership_info");
        try {
            return objectMapper.readTree(jsonString);
        } catch (Exception e) {
            throw new SQLException("Failed to parse JSON", e);
        }
    }
}
