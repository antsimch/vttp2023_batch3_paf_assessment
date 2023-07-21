package vttp2023.batch3.assessment.paf.bookings.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2023.batch3.assessment.paf.bookings.models.Booking;
import vttp2023.batch3.assessment.paf.bookings.models.Occupany;

@Repository
public class BookingRepository {
    
    @Autowired
    private JdbcTemplate template;

    private static final String SQL_FIND_VACANCY = """
            select * from acc_occupancy where acc_id = ?
            """;

    private static final String SQL_INSERT_BOOKING = """
            insert into reservations 
            (resv_id, name, email, acc_id, arrival_date, duration)
            values (?, ?, ?, ?, ?, ?)
            """;

    private static final String SQL_UPDATE_VACANCY = """
            update acc_occupancy set vacancy = vacancy - ? 
            where acc_id = ?
            """;

    public Occupany findVacancy(String id) {
        return template.query(
                SQL_FIND_VACANCY, 
                BeanPropertyRowMapper.newInstance(Occupany.class), 
                id).get(0);
    }

    public Integer insertBooking(Booking booking) {
        return template.update(
                SQL_INSERT_BOOKING, 
                booking.getId(), 
                booking.getName(), 
                booking.getEmail(), 
                booking.getAccId(), 
                booking.getArrivalDate(), 
                booking.getDuration());
    }

    public Integer updateVacancy(Booking booking) {
        return template.update(
                SQL_UPDATE_VACANCY,
                booking.getDuration(),
                booking.getAccId()
        );
    }
}
