package com.wpate.myvent.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByName(String name);
    List<Event> findByCategoryId(long id);
}
