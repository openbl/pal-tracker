package test.pivotal.pal.tracker;

import io.pivotal.pal.tracker.TimeEntry;
import io.pivotal.pal.tracker.TimeEntryRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<TimeEntry> rowMapper = (rs1, rowNum) -> new TimeEntry(
            rs1.getLong("id"),
            rs1.getLong("project_id"),
            rs1.getLong("user_id"),
            rs1.getDate("date").toLocalDate(),
            rs1.getInt("hours")
    );
    private final ResultSetExtractor<TimeEntry> resultSetExtractor = rs -> rs.next() ? rowMapper.mapRow(rs, 1) : null;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        final String insertSql = "INSERT INTO time_entries (project_id, user_id, date, hours) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement statement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

                    statement.setLong(1, timeEntry.getProjectId());
                    statement.setLong(2, timeEntry.getUserId());
                    statement.setDate(3, Date.valueOf(timeEntry.getDate()));
                    statement.setInt(4, timeEntry.getHours());

                    return statement;
                },
                keyHolder
        );

        return find(keyHolder.getKey().intValue());
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        final String findSql = "SELECT id, project_id, user_id, date, hours FROM time_entries WHERE id = ?";

        return jdbcTemplate.query(
                findSql,
                new Object[]{timeEntryId},
                resultSetExtractor
        );
    }

    @Override
    public List<TimeEntry> list() {
        final String listSql = "SELECT id, project_id, user_id, date, hours FROM time_entries";

        return jdbcTemplate.query(listSql, rowMapper);
    }

    @Override
    public TimeEntry update(long timeEntryId, TimeEntry timeEntry) {
        final String updateSql = "UPDATE time_entries SET project_id = ?, user_id = ?, date = ?, hours = ? WHERE id = ?";

        jdbcTemplate.update(
                updateSql,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                Date.valueOf(timeEntry.getDate()),
                timeEntry.getHours(),
                timeEntryId
                );

        return find(timeEntryId);
    }

    @Override
    public void delete(long timeEntryId) {
        jdbcTemplate.update("DELETE FROM time_entries WHERE id = ?", timeEntryId);
    }
}
