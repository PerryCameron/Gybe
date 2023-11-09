package com.ecsail.Gybe.repository.implementations;

import com.ecsail.Gybe.dto.BoardPositionDTO;
import com.ecsail.Gybe.repository.interfaces.BoardPositionsRepository;
import com.ecsail.Gybe.repository.rowmappers.BoardPositionsRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class BoardPositionsRepositoryImpl implements BoardPositionsRepository {
    private final JdbcTemplate template;

    @Autowired
    public BoardPositionsRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<BoardPositionDTO> getPositions() {
        String query = "SELECT * FROM board_positions";
        return template.query(query,new BoardPositionsRowMapper());
    }

    @Override
    public String getByIdentifier(String code) {
        return null;
    }

    @Override
    public String getByName(String name) {
        return null;
    }
}
