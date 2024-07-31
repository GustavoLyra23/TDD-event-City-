package com.devsuperior.demo.service;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.Event;
import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.repositories.EventRepository;
import com.devsuperior.demo.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CityRepository cityRepository;

    @Transactional()
    public EventDTO update(Long id, EventDTO eventDto) {
        try {
            Event event = eventRepository.getReferenceById(id);
            copyDtoToEntity(eventDto, event);
            event = eventRepository.save(event);
            return new EventDTO(event);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resource not found");
        }
    }

    private void copyDtoToEntity(EventDTO eventDto, Event eventEntity) {
        eventEntity.setName(eventDto.getName());
        eventEntity.setDate(eventDto.getDate());
        eventEntity.setUrl(eventDto.getUrl());
        eventEntity.setCity(cityRepository.getReferenceById(eventDto.getCityId()));
    }


}
