package ru.nemolyakin.resttestspring.rest;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import ru.nemolyakin.resttestspring.model.EventEntity;
import ru.nemolyakin.resttestspring.dto.EventDto;
import ru.nemolyakin.resttestspring.service.EventService;
import ru.nemolyakin.resttestspring.service.impl.EventServiceImpl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class EventRestControllerV1Test {

    @Mock
    private EventService eventService = mock(EventServiceImpl.class);

    @InjectMocks
    private EventRestControllerV1 underTest = new EventRestControllerV1(eventService);

    @Test
    void getById_Success() {
        when(eventService.getById(any())).thenReturn(getEvent());
        ResponseEntity<EventDto> eventDto = underTest.getEventById(2L);
        assertEquals(200, eventDto.getStatusCodeValue());
        assertEquals(true, eventDto.toString().contains("1"));
    }

    @Test
    void getById_Failed() {
        when(eventService.getById(any())).thenReturn(noEvent());
        ResponseEntity<EventDto> eventDto = underTest.getEventById(2L);
        assertEquals(404, eventDto.getStatusCodeValue());
    }

    @Test
    public void testGetAll_Success() {
        when(eventService.getAll()).thenReturn(getAllE());
        ResponseEntity<List<EventDto>> events = underTest.getAllEvents();
        assertEquals(200, events.getStatusCodeValue());
        assertEquals(true, events.toString().contains("2"));
    }

    @Test
    public void testGetAll_Failed() {
        when(eventService.getAll()).thenReturn(noList());
        ResponseEntity<List<EventDto>> events = underTest.getAllEvents();
        assertEquals(200, events.getStatusCodeValue());
        assertEquals(false, events.toString().contains("1"));
        assertEquals(false, events.toString().contains("2"));
    }

//    @Test
//    public void testSaveAndUpdate_Success(){
//        EventEntity event = new EventEntity();
//        EventEntity event2 = new EventEntity();
//        event.setFileId(1);
//        event2.setFileId(2);
//
//        when(eventService.save(any())).thenReturn(getEventEntity());
//        ResponseEntity<EventDto> eventDto = underTest.saveEvent(event);
//        assertEquals(201, eventDto.getStatusCodeValue());
//        assertEquals(true, eventDto.toString().contains("1"));
//
//        when(eventService.save(any())).thenReturn(getEventUpdate());
//        eventDto = underTest.updateEvent(event2);
//        assertEquals(200, eventDto.getStatusCodeValue());
//        assertEquals(true, eventDto.toString().contains("2"));
//    }
//
//    @Test
//    public void testSaveAndUpdate_Fail(){
//        when(eventService.save(any())).thenReturn(noEvent());
//        ResponseEntity<EventDto> eventDto = underTest.saveEvent(null);
//        assertEquals(400, eventDto.getStatusCodeValue());
//        assertEquals(false, eventDto.toString().contains("Maxim"));
//
//        when(eventService.save(any())).thenReturn(noEvent());
//        eventDto = underTest.updateEvent(null);
//        assertEquals(400, eventDto.getStatusCodeValue());
//        assertEquals(false, eventDto.toString().contains("Ivan"));
//    }

    @Test
    public void testDelete_Success(){
        when(eventService.getById(any())).thenReturn(getEvent());
        doNothing().when(eventService).delete(any());
        underTest.deleteEvent(1L);
        verify(eventService, times(1)).delete(1L);
    }

    @Test
    public void testDelete_Fail(){
        when(eventService.getById(any())).thenReturn(getEvent());
        doNothing().when(eventService).delete(any());
        underTest.deleteEvent(1L);
        verify(eventService, times(1)).delete(1L);
    }


    private EventEntity getEvent() {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setFileId(2);
        return eventEntity;
    }

    private EventEntity noEvent() {
        return null;
    }

    private List<EventEntity> getAllE() {
        EventEntity eventEntity = new EventEntity();
        EventEntity eventEntity2 = new EventEntity();
        eventEntity.setFileId(1);
        eventEntity2.setFileId(2);
        return Stream.of(eventEntity, eventEntity2).collect(Collectors.toList());
    }

    private List<EventEntity> noList() {
        EventEntity eventEntity = new EventEntity();
        EventEntity eventEntity2 = new EventEntity();
        return Stream.of(eventEntity, eventEntity2).collect(Collectors.toList());
    }

    private EventEntity getEventUpdate(){
        EventEntity eventEntity = new EventEntity();
        eventEntity.setFileId(2);
        return eventEntity;
    }
}