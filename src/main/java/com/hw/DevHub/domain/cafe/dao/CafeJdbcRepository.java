package com.hw.DevHub.domain.cafe.dao;

import com.hw.DevHub.domain.cafe.domain.Cafe;
import jakarta.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CafeJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void bulkInsert(List<Cafe> cafeList) {
        String sql = "insert into Cafe(name, road_named_address, zip_code,longitude,latitude) values(?,?,?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Cafe cafe = cafeList.get(i);
                ps.setString(1, cafe.getName());
                ps.setString(2, cafe.getRoadNamedAddress());
                ps.setString(3, cafe.getZipCode());
                ps.setDouble(4, cafe.getLongitude());
                ps.setDouble(5, cafe.getLatitude());
            }

            @Override
            public int getBatchSize() {
                return cafeList.size();
            }
        });
    }
}
