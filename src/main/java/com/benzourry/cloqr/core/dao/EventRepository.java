package com.benzourry.cloqr.core.dao;

import com.benzourry.cloqr.core.model.Event;
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
public interface EventRepository extends JpaRepository<Event, Long> {

    public Event findByToken(@Param("token") String token);

    public Page<Event> findByOrganizeBy(@Param("organizeBy") String organizeBy, Pageable pageable);

//    @Query("SELECT e FROM Event e WHERE e.organizedBy = :staffNo")
//    public List<Event> findByStaffNo(@Param("staffNo") String staffNo);

}
