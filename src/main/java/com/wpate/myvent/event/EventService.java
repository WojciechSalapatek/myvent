package com.wpate.myvent.event;

import com.wpate.myvent.event.exceptions.EventAlreadyExistsException;
import com.wpate.myvent.event.exceptions.EventNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private EventRepository repository;

    @Autowired
    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public Event getEvent(long id){
        return repository.findById(id).orElseThrow(() -> new EventNotFoundException("There is no event with such id"));
    }

    public Event getByName(String name){
        var event = repository.findByName(name);
        if(event == null) throw new EventNotFoundException("There is no event with such name");
        return event;
    }

    public List<Event> getAll(){
        return repository.findAll();
    }

    public List<Event> getFromCategory(long categoryId){
        return repository.findByCategoryId(categoryId);
    }

    public void updateEvent(Event e){
        if(!repository.existsById(e.getId())) throw new EventNotFoundException("Cannot update not existing event");
        repository.save(e);
    }

    public void addEvent(Event e){
        if(repository.existsById(e.getId())) throw new EventAlreadyExistsException("Event with id: " + e.getId() + " already exists");
        repository.save(e);
    }

    public void deleteEvent(long id){
        if(!repository.existsById(id)) throw new EventNotFoundException("Cannot delete not existing event");
        repository.deleteById(id);
    }

}
