package com.wpate.myvent.event;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EventController {

    private EventService service;
    private ModelMapper mapper;

    @Autowired
    public EventController(EventService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/categories/events")
    public List<EventDTO> getAll(){
        service.addEvent(Event.EventBuilder.anEvent(0, "Gymning", new Date()).build());
        return service.getAll().stream()
                .map(e -> mapper.map(e, EventDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/categories/{category_id}/events")
    public List<EventDTO> getFromCategory(@PathVariable long category_id){
        return service.getFromCategory(category_id).stream()
                .map(e -> mapper.map(e, EventDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/categories/{id}/events/{event_id}")
    public EventDTO getEvent(@PathVariable long event_id){
        return mapper.map(service.getEvent(event_id), EventDTO.class);
    }

    @PostMapping("/categories/{id}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public void addEvent(@Valid @RequestBody EventDTO eventDTO){
        var event = mapper.map(eventDTO, Event.class);
        service.addEvent(event);
    }

    @PutMapping("/categories/{id}/events/{event_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEvent(@PathVariable long event_id, @Valid @RequestBody EventDTO eventDTO){
        var event = mapper.map(eventDTO, Event.class);
        service.updateEvent(event);
    }

    @DeleteMapping("/categories/{id}/events/{event_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable long event_id){
        service.deleteEvent(event_id);
    }


}
