package com.benzourry.cloqr.core.dao;

import com.benzourry.cloqr.core.model.LogEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by MohdRazif on 4/10/2015.
 */
@Repository
public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
    //
    @Query("SELECT l from LogEntry l WHERE l.account.username = :username AND (l.event.token = :token)")
    public LogEntry findByTokenAndUsername(@Param("token")String token, @Param("username") String username);

//    @Query("SELECT l from LogEntry l WHERE l.account.username = :username AND (l.event = :eventId)")
//    public LogEntry findByEventIdAndUsername(@Param("eventId")String eventId, @Param("username") String username);

    @Query("SELECT l from LogEntry l WHERE l.event.id = :id")
    public Page<LogEntry> findByEventId(@Param("id") Long id, Pageable pageable);

    @Query("SELECT l from LogEntry l WHERE l.account.username = :username")
    public Page<LogEntry> findByUsername(@Param("username") String username, Pageable pageable);
}
