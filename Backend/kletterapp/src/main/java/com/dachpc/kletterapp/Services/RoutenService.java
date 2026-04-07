package com.dachpc.kletterapp.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dachpc.kletterapp.Dtos.RouteCreateDTO;
import com.dachpc.kletterapp.Dtos.RouteResponseDTO;
import com.dachpc.kletterapp.Entities.Route;
import com.dachpc.kletterapp.Entities.WandId;
import com.dachpc.kletterapp.Mappers.RouteMapper;
import com.dachpc.kletterapp.Repositories.RoutenRepository;
import com.dachpc.kletterapp.Repositories.WandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class RoutenService {

    @Autowired
    private RouteMapper routeMapper;

    @Autowired
    private WandRepository wandRepository;

    @Autowired
    private RoutenRepository routenRepository;
    
    public RouteResponseDTO addRoute(RouteCreateDTO dto) {
        Route route = routeMapper.toEntity(dto);
        route.setWand(wandRepository.getReferenceById(new WandId(dto.getHallenId(), dto.getWandNr())));
        route.setIsActive(true);
        Route added = routenRepository.save(route);
        return routeMapper.toResponseDTO(added);
    }

    public List<RouteResponseDTO> getRoutenByHallenId(int hallenId) {
        List<Route> routen = routenRepository.findByWand_Id_HallenId(hallenId);
        return routen.stream().map(routeMapper::toResponseDTO).toList();
    }

    public RouteResponseDTO updateRoute(int id, RouteCreateDTO dto) {
        Route route = routenRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Zu aktualisierende Route nicht gefunden"));
        routeMapper.updateEntity(dto, route);
        Route updated = routenRepository.save(route);
        return routeMapper.toResponseDTO(updated);
    }

    public void deleteRoute(int id) {
        Route route = routenRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Zu löschende Route nicht gefunden"));
        routenRepository.delete(route);
    }

}
