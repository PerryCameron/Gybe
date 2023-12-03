package com.ecsail.Gybe.repository.implementations;

import com.ecsail.Gybe.dto.MembershipListDTO;
import com.ecsail.Gybe.repository.interfaces.MembershipRepository;
import com.ecsail.Gybe.repository.rowmappers.MembershipListRowMapper;
import com.ecsail.Gybe.repository.rowmappers.MembershipListRowMapper1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MembershipRepositoryImpl implements MembershipRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate template;

    @Autowired
    public MembershipRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<MembershipListDTO> getActiveRoster(Integer selectedYear) {
        String query = """
                SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,id.fiscal_year,m.join_date,id.mem_type, 
                s.SLIP_NUM,p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip 
                FROM (select * from membership_id where FISCAL_YEAR=? and RENEW=1) id 
                INNER JOIN membership m on m.MS_ID = id.MS_ID 
                LEFT JOIN (select * from person where MEMBER_TYPE=1) p on m.MS_ID= p.MS_ID 
                LEFT JOIN slip s on m.MS_ID = s.MS_ID;
                """;
        List<MembershipListDTO> membershipListDTOS
                = template.query(query, new MembershipListRowMapper(), new Object[]{selectedYear.intValue()});
        return membershipListDTOS;
    }

    @Override
    public List<MembershipListDTO> getInActiveRoster(Integer selectedYear) {
        String query = """
                SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,id.fiscal_year,m.join_date,id.mem_type, 
                s.SLIP_NUM,p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip 
                FROM (select * from membership_id where FISCAL_YEAR=? and RENEW=0) id 
                INNER JOIN membership m on m.MS_ID = id.MS_ID 
                LEFT JOIN (select * from person where MEMBER_TYPE=1) p on m.MS_ID= p.MS_ID  
                LEFT JOIN slip s on m.MS_ID = s.MS_ID;
                """;
        List<MembershipListDTO> membershipListDTOS
                = template.query(query, new MembershipListRowMapper(), new Object[]{selectedYear.intValue()});
        return membershipListDTOS;
    }

    @Override
    public List<MembershipListDTO> getAllRoster(Integer selectedYear) {
        String query = """
                SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,id.fiscal_year,m.join_date,id.mem_type,
                s.SLIP_NUM,p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip 
                FROM (select * from membership_id where FISCAL_YEAR=?) id 
                INNER JOIN membership m on m.MS_ID = id.MS_ID 
                LEFT JOIN (select * from person where MEMBER_TYPE=1) p on m.MS_ID= p.MS_ID 
                LEFT JOIN slip s on m.MS_ID = s.MS_ID;
                """;
        List<MembershipListDTO> membershipListDTOS
                = template.query(query, new MembershipListRowMapper(), new Object[]{selectedYear.intValue()});
        return membershipListDTOS;
    }

    @Override
    public List<MembershipListDTO> getNewMemberRoster(Integer selectedYear) {
        String query = """
                SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,id.fiscal_year,m.join_date,id.mem_type,s.SLIP_NUM,p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip
                FROM (select * from membership where YEAR(JOIN_DATE)=?) m
                INNER JOIN (select * from membership_id where FISCAL_YEAR=? and RENEW=1) id ON m.ms_id=id.ms_id
                INNER JOIN (select * from person where MEMBER_TYPE=1) p ON m.p_id=p.p_id
                LEFT JOIN slip s on m.MS_ID = s.MS_ID
                                """;
        List<MembershipListDTO> membershipListDTOS
                = template.query(query, new MembershipListRowMapper(), new Object[]{selectedYear, selectedYear.intValue()});
        return membershipListDTOS;
    }

    @Override
    public List<MembershipListDTO> getReturnMemberRoster(Integer selectedYear) {
        String query = """
                SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,m.join_date,id.mem_type,s.SLIP_NUM,p.l_name,
                       p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip
                FROM membership_id id
                         LEFT JOIN membership m ON id.ms_id = m.ms_id
                         LEFT JOIN person p ON p.p_id = m.p_id
                         LEFT JOIN slip s ON s.ms_id = m.ms_id
                WHERE fiscal_year = ?
                  AND id.membership_id >
                      (SELECT membership_id
                       FROM membership_id
                       WHERE fiscal_year = ?
                         AND ms_id = (SELECT ms_id
                                      FROM membership_id
                                      WHERE membership_id = (SELECT MAX(membership_id)
                                                             FROM membership_id
                                                             WHERE fiscal_year = (? - 1)
                                                               AND membership_id < 500
                                                               AND renew = 1)
                                        AND fiscal_year = (? - 1)))
                  AND id.membership_id < 500
                  AND YEAR(m.join_date) != ?
                  AND (SELECT NOT EXISTS(SELECT mid
                                         FROM membership_id
                                         WHERE fiscal_year = (? - 1)
                                           AND renew = 1
                                           AND ms_id = id.ms_id));
                                """;
        List<MembershipListDTO> membershipListDTOS
                = template.query(query, new MembershipListRowMapper(), new Object[]{selectedYear.intValue(),
                selectedYear.intValue(), selectedYear.intValue(), selectedYear.intValue(), selectedYear.intValue(),
                selectedYear.intValue()});
        return membershipListDTOS;
    }

    @Override
    public List<MembershipListDTO> getSlipWaitList(Integer selectedYear) {
        String query = """
                SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,id.fiscal_year,m.join_date,id.mem_type,
                               s.SLIP_NUM,p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip
                        FROM (SELECT * from wait_list where SLIP_WAIT=true) wl
                                 INNER JOIN (select * from membership_id where FISCAL_YEAR=YEAR(NOW()) and RENEW=1) id on id.MS_ID=wl.MS_ID
                                 INNER JOIN membership m on m.MS_ID=wl.MS_ID
                                 LEFT JOIN (select * from person where MEMBER_TYPE=1) p on m.MS_ID= p.MS_ID
                                 LEFT JOIN slip s on m.MS_ID = s.MS_ID;
                """;
        List<MembershipListDTO> membershipListDTOS
                = template.query(query, new MembershipListRowMapper(), new Object[]{selectedYear.intValue()});
        return membershipListDTOS;
    }

    @Override
    public List<MembershipListDTO> getMembershipByBoatId(Integer boatId) {
        String query = """
                select m.ms_id,m.p_id,m.join_date,p.l_name,p.f_name,m.address,m.city,m.state,m.zip from membership m
                join person p on m.P_ID = p.P_ID
                join boat_owner bo on m.MS_ID = bo.MS_ID where BOAT_ID=?;
                """;
        List<MembershipListDTO> membershipListDTOS
                = template.query(query, new MembershipListRowMapper1(), boatId);
        return membershipListDTOS;
    }

    @Override
    public int update(MembershipListDTO membershipListDTO) {
        String query = "UPDATE membership SET " +
                "P_ID = :pId, " +
                "JOIN_DATE = :joinDate, " +
                "MEM_TYPE = :memType, " +
                "ADDRESS = :address, " +
                "CITY = :city, " +
                "STATE = :state, " +
                "ZIP = :zip WHERE MS_ID = :msId";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(membershipListDTO);
        return namedParameterJdbcTemplate.update(query, namedParameters);
    }

    @Override
    public int updateJoinDate(MembershipListDTO membershipListDTO) {
        String query = "UPDATE membership SET " +
                "JOIN_DATE = :joinDate WHERE MS_ID=:msId";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(membershipListDTO);
        return namedParameterJdbcTemplate.update(query, namedParameters);
    }

    @Override
    public MembershipListDTO getMembershipByMembershipId(int membershipId) {
        String query = """
                SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,m.join_date,
                id.mem_type,p.l_name,p.f_name,m.address,m.city,m.state,m.zip FROM 
                membership m LEFT JOIN person p ON m.p_id=p.p_id LEFT JOIN membership_id 
                id ON m.ms_id=id.ms_id WHERE id.fiscal_year=YEAR(NOW()) AND membership_id=?
                """;
        return template.queryForObject(query, new MembershipListRowMapper1(), membershipId);
    }
// Below no longer used
    @Override
    public MembershipListDTO getMembershipByMsId(int msId) {  // will get by newest year available
        String query = """
                Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,id.FISCAL_YEAR,m.JOIN_DATE,
                id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip
                from slip s right join membership m on m.MS_ID=s.MS_ID left join membership_id id on
                m.MS_ID=id.MS_ID left join person p on p.MS_ID=m.MS_ID where p.MEMBER_TYPE=1
                AND id.fiscal_year=(SELECT MAX(FISCAL_YEAR) FROM membership_id where MS_ID=76) AND m.MS_ID=?
                                """;
        return template.queryForObject(query, new MembershipListRowMapper(), msId);
    }
@Override
public MembershipListDTO getMembershipByMsId(int msId, int specifiedYear) {
    String query = """
            SELECT m.MS_ID, m.P_ID, id.MEMBERSHIP_ID, id.FISCAL_YEAR, m.JOIN_DATE, 
                   id.MEM_TYPE, s.SLIP_NUM, p.L_NAME, p.F_NAME, s.SUBLEASED_TO, 
                   m.address, m.city, m.state, m.zip 
            FROM slip s 
            RIGHT JOIN membership m ON m.MS_ID = s.MS_ID 
            LEFT JOIN membership_id id ON m.MS_ID = id.MS_ID 
            LEFT JOIN person p ON p.MS_ID = m.MS_ID 
            WHERE p.MEMBER_TYPE = 1 
            AND id.fiscal_year = (
                CASE 
                    WHEN EXISTS (
                        SELECT 1 FROM membership_id 
                        WHERE FISCAL_YEAR = ? AND MS_ID = ?
                    )
                    THEN ?
                    ELSE (SELECT MAX(FISCAL_YEAR) FROM membership_id WHERE MS_ID = ?)
                END
            )
            AND m.MS_ID = ?
            """;
    return template.queryForObject(query, new MembershipListRowMapper(), specifiedYear, msId, specifiedYear, msId, msId);
}


    @Override
    public List<MembershipListDTO> getRoster(int year, boolean isActive) {
        String QUERY = "Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,id.FISCAL_YEAR,m.JOIN_DATE," +
                "id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip " +
                "from slip s right join membership m on m.MS_ID=s.MS_ID left join membership_id id on " +
                "m.MS_ID=id.MS_ID left join person p on p.MS_ID=m.MS_ID where id.FISCAL_YEAR=?" +
                " and p.MEMBER_TYPE=1 and id.RENEW=? order by membership_id";
        List<MembershipListDTO> memberships = template.query(QUERY, new MembershipListRowMapper(), year, isActive);
        return memberships;
    }

    @Override
    public List<MembershipListDTO> getRosterOfAll(int year) {
        String QUERY = "SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,m.join_date,id.mem_type,s.SLIP_NUM,p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip "
                + "FROM slip s "
                + "RIGHT JOIN membership m ON m.ms_id=s.ms_id "
                + "LEFT JOIN membership_id id ON m.ms_id=id.ms_id "
                + "LEFT JOIN person p ON p.ms_id=m.ms_id "
                + "WHERE id.fiscal_year=? AND p.member_type=1 ORDER BY membership_id";
        List<MembershipListDTO> memberships = template.query(QUERY, new MembershipListRowMapper(), year);
        return memberships;
    }

    @Override
    public List<MembershipListDTO> getReturnMembers(int year) {
        String QUERY = "SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,m.join_date,id.mem_type,s.SLIP_NUM" +
                ",p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip FROM membership_id id LEFT JOIN " +
                "membership m ON id.ms_id=m.ms_id LEFT JOIN person p ON p.p_id=m.p_id LEFT JOIN slip s ON " +
                "s.ms_id=m.ms_id WHERE fiscal_year=" + year + " AND id.membership_id > (SELECT membership_id FROM " +
                "membership_id WHERE fiscal_year=" + year + " AND ms_id=(SELECT ms_id FROM membership_id WHERE " +
                "membership_id=(SELECT MAX(membership_id) FROM membership_id WHERE fiscal_year=" + (year - 1) + " " +
                "AND membership_id < 500 AND renew=1) AND fiscal_year=" + (year - 1) + ")) AND id.membership_id < " +
                "500 AND YEAR(m.join_date)!=" + year + " AND (SELECT NOT EXISTS(SELECT mid FROM membership_id " +
                "WHERE fiscal_year=" + (year - 1) + " AND renew=1 AND ms_id=id.ms_id))";
        List<MembershipListDTO> memberships = template.query(QUERY, new MembershipListRowMapper(), year);
        return memberships;
    }

    @Override
    public MembershipListDTO getMembershipListFromMsidAndYear(String year) {
        String QUERY =
                "SELECT m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,"
                        + "id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,"
                        + "m.zip FROM slip s RIGHT JOIN membership m on m.MS_ID=s.MS_ID LEFT JOIN membership_id "
                        + "id ON m.MS_ID=id.MS_ID JOIN person p ON "
                        + "p.MS_ID=m.MS_ID WHERE id.FISCAL_YEAR=? AND m.ms_id=? AND p.MEMBER_TYPE=1";
        return (MembershipListDTO) template.queryForObject(QUERY, new MembershipListRowMapper(), year);
    }

    @Override
    public List<MembershipListDTO> getSearchRoster(List<String> searchParams) {
        // Starting part of the SQL query
        StringBuilder queryBuilder = new StringBuilder("""
                SELECT
                    m.ms_id,
                    id.membership_id,
                    m.p_id,
                    id.fiscal_year,
                    m.join_date,
                    id.mem_type,
                    s.SLIP_NUM,
                    p.l_name,
                    p.f_name,
                    s.subleased_to,
                    m.address,
                    m.city,
                    m.state,
                    m.zip
                FROM (
                         SELECT mi.*
                         FROM membership_id mi
                                  INNER JOIN (
                             SELECT MS_ID, MAX(FISCAL_YEAR) as MaxYear
                             FROM membership_id
                             WHERE MEM_TYPE != 'NR'
                             GROUP BY MS_ID
                         ) as newest
                                             ON mi.MS_ID = newest.MS_ID AND mi.FISCAL_YEAR = newest.MaxYear
                ) id
                         INNER JOIN membership m ON m.MS_ID = id.MS_ID
                         LEFT JOIN (SELECT * FROM person WHERE MEMBER_TYPE = 1) p ON m.MS_ID = p.MS_ID
                         LEFT JOIN slip s ON m.MS_ID = s.MS_ID
                                                """);

        // List to hold parameters for the prepared statement
        List<Object> params = new ArrayList<>();
//        params.add(selectedYear);

        if (searchParams != null && !searchParams.isEmpty()) {
            queryBuilder.append(" WHERE ");
            List<String> conditions = new ArrayList<>();

            for (String param : searchParams) {
                // Here, each param should be checked against every field
                String searchCondition = String.format(" (m.ms_id LIKE '%%%1$s%%' OR m.p_id LIKE '%%%1$s%%' OR id.membership_id LIKE '%%%1$s%%' OR " +
                        "id.fiscal_year LIKE '%%%1$s%%' OR m.join_date LIKE '%%%1$s%%' OR id.mem_type LIKE '%%%1$s%%' OR " +
                        "s.SLIP_NUM LIKE '%%%1$s%%' OR p.l_name LIKE '%%%1$s%%' OR p.f_name LIKE '%%%1$s%%' OR " +
                        "s.subleased_to LIKE '%%%1$s%%' OR m.address LIKE '%%%1$s%%' OR m.city LIKE '%%%1$s%%' OR " +
                        "m.state LIKE '%%%1$s%%' OR m.zip LIKE '%%%1$s%%')", param);
                conditions.add(searchCondition);
            }

            queryBuilder.append(String.join(" OR ", conditions));
        }

        // Convert StringBuilder to String
        String query = queryBuilder.toString();

        // Convert params list to an array
        Object[] paramsArray = params.toArray();

        // Execute the query
        List<MembershipListDTO> membershipListDTOS = template.query(query, new MembershipListRowMapper(), paramsArray);
        return membershipListDTOS;
    }

}
