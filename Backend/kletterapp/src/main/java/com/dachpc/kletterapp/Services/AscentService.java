package com.dachpc.kletterapp.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dachpc.kletterapp.Dtos.AscentCreateDTO;
import com.dachpc.kletterapp.Dtos.AscentResponseDTO;
import com.dachpc.kletterapp.Entities.Ascent;
import com.dachpc.kletterapp.Entities.Style;
import com.dachpc.kletterapp.Mappers.AscentMapper;
import com.dachpc.kletterapp.Repositories.AscentRepository;
import com.dachpc.kletterapp.Repositories.RoutenRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class AscentService {

    @Autowired
    private AscentRepository ascentRepository;

    @Autowired
    private RoutenRepository routenRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AscentMapper ascentMapper;
    
    public AscentResponseDTO addAscent(AscentCreateDTO dto) {
        Ascent ascent = ascentMapper.toEntity(dto);
        if (ascent.getDatum() == null) {
            ascent.setDatum(LocalDate.now());
        }
        ascent.setRoute(routenRepository.getReferenceById(dto.getRoutenId()));
        Ascent savedAscent = ascentRepository.save(ascent);
        reward(ascent);
        return ascentMapper.toResponseDTO(savedAscent);
    }

    public List<AscentResponseDTO> findAscents(String userId, Integer routenId) {
        List<Ascent> ascents = ascentRepository.search(userId, routenId);
        return ascents.stream().map(ascentMapper::toResponseDTO).toList();
    }

    public List<AscentResponseDTO> findAllAscents(String userId, List<Integer> routenIdList) {
        List<Ascent> ascents = new ArrayList<>();
        for (Integer routenId : routenIdList) {
            ascents.addAll(ascentRepository.search(userId, routenId));
        }
        return ascents.stream().map(ascentMapper::toResponseDTO).toList();
    }

    public AscentResponseDTO updateAscent(int id, AscentCreateDTO dto) {
        Ascent ascent = ascentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ascent nicht gefunden"));
        ascentMapper.updateEntity(dto, ascent);
        Ascent updated = ascentRepository.save(ascent);
        return ascentMapper.toResponseDTO(updated);
    }

    public void deleteAscent(int id) {
        ascentRepository.deleteById(id);
    }


    private void reward(Ascent ascent) {
        double points = Math.floor(ascent.getRoute().getSchwierigkeit() / 5.0) * styleToFactor(ascent.getStyle());
        userService.reward(ascent.getUserId(), (int) points);
    }

    private double styleToFactor(Style style) {
        if (style == null) return 1;
        if (style == Style.onsight) return 2.5;
        if (style == Style.flash) return 2;
        if (style == Style.redpoint) return 1.5;
        if (style == Style.pinkpoint) return 1;
        if (style == Style.toprope) return 0.5;
        if (style == Style.hangdog) return 0.5;
        if (style == Style.attempt) return 0.1;
        return 0;
      };

}
