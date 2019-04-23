package com.wpate.myvent.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.wpate.myvent.event.exceptions.EventNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EventService eventService;

    private ObjectMapper mapper = new ObjectMapper();
    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void getEventWithId_ReturnsEventDto() throws Exception {
        //arrange
        given(eventService.getEvent(anyLong())).willReturn(Event.EventBuilder.anEvent(0, "MeetUp", new Date()).build());

        //act

        //assert
        mvc.perform(MockMvcRequestBuilders.get("/categories/0/events/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(0))
                .andExpect(jsonPath("name").value("MeetUp"))
                .andExpect(jsonPath("date").isNotEmpty());
    }

    @Test
    public void getEventFromCategoryId_ReturnsListOfEventDto() throws Exception {
        //arrange
        given(eventService.getFromCategory(anyLong())).willReturn(Arrays.asList(
                Event.EventBuilder.anEvent(0, "MeetUp", new Date()).build(),
                Event.EventBuilder.anEvent(1, "Gymning", new Date()).build()
        ));

        //act

        //assert
        mvc.perform(MockMvcRequestBuilders.get("/categories/0/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].name" , containsInAnyOrder("MeetUp", "Gymning")))
                .andExpect(jsonPath("$[*].date").isNotEmpty());
    }

    @Test
    public void getEventWithId_NotFoundThrowsException() throws Exception {
        //arrange
        given(eventService.getEvent(anyLong())).willThrow(new EventNotFoundException(""));

        //act

        //assert
        mvc.perform(MockMvcRequestBuilders.get("/categories/0/events/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void postEvent_NameTooLong_ShouldReturnValidationErrors() throws Exception {
        //arrange
        String title = Strings.repeat("x", 51);
        var eventDto = EventDTO.EventDTOBuilder.anEventDTO(0, title, new Date()).build();

        //assert
        mvc.perform(MockMvcRequestBuilders.post("/categories/0/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(eventDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors[*].field", contains("name")))
                .andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder(
                        "length must be between 3 and 30"
                )));
    }

    @Test
    public void correctPostEvent_ReturnsCreatedStatusCode() throws Exception {
        //arrange
        doNothing().when(eventService).addEvent(ArgumentMatchers.isA(Event.class));
        var eventDto = EventDTO.EventDTOBuilder.anEventDTO(0, "MeetUp", new Date()).build();
        //assert act
        mvc.perform(MockMvcRequestBuilders.post("/categories/0/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(eventDto)))
                .andExpect(status().isCreated());

        verify(eventService, times(1)).addEvent(ArgumentMatchers.isA(Event.class));
        verifyNoMoreInteractions(eventService);
    }

}
