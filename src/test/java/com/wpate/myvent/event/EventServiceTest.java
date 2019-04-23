package com.wpate.myvent.event;

import com.wpate.myvent.event.exceptions.EventAlreadyExistsException;
import com.wpate.myvent.event.exceptions.EventNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    @Mock
    private EventRepository repository;

    private EventService service;

    @Before
    public void setUp(){
        service = new EventService(repository);
    }

    @Test
    public void getEvent_ReturnsEvent(){
        //arrange
        var date = new Date();
        given(repository.findById(0L)).willReturn(java.util.Optional.of(
                Event.EventBuilder.anEvent(0, "Conference", date).build()
        ));

        //act
        var category = service.getEvent(0);

        //assert
        Assertions.assertThat(category.getId()).isEqualTo(0);
        Assertions.assertThat(category.getName()).isEqualTo("Conference");
        Assertions.assertThat(category.getDate()).isEqualTo(date);
    }

    @Test(expected = EventNotFoundException.class)
    public void getEventByName_NotFindThrowsException(){
        //arrange
        given(repository.findByName("name")).willReturn(null);

        //act
        service.getByName("name");

    }

    @Test(expected = EventNotFoundException.class)
    public void updateNotExistingEvent_ThrowsException(){
        //arrange
        given(repository.existsById(0L)).willReturn(false);

        //act
        service.updateEvent(Event.EventBuilder.anEvent(0,"", new Date()).build());

    }

    @Test(expected = EventNotFoundException.class)
    public void deleteNotExistingEvent_ThrowsException(){
        //arrange
        given(repository.existsById(0L)).willReturn(false);

        //act
        service.deleteEvent(0L);

    }

    @Test(expected = EventAlreadyExistsException.class)
    public void addAlreadyExistingEvent_ThrowsException(){
        //arrange
        given(repository.existsById(0L)).willReturn(true);

        //act
        service.addEvent(Event.EventBuilder.anEvent(0,"", new Date()).build());

    }

}
