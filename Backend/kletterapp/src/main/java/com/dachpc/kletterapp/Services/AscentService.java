package com.dachpc.kletterapp.Services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dachpc.kletterapp.Dtos.AscentCreateDTO;
import com.dachpc.kletterapp.Dtos.AscentResponseDTO;
import com.dachpc.kletterapp.Entities.Ascent;
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
    private AscentMapper ascentMapper;
    
    public AscentResponseDTO addAscent(AscentCreateDTO dto) {
        Ascent ascent = ascentMapper.toEntity(dto);
        if (ascent.getDatum() == null) {
            ascent.setDatum(LocalDate.now());
        }
        ascent.setRoute(routenRepository.getReferenceById(dto.getRoutenId()));
        Ascent savedAscent = ascentRepository.save(ascent);
        return ascentMapper.toResponseDTO(savedAscent);
    }

    public List<AscentResponseDTO> findAscents(String userId, Integer routenId) {
        List<Ascent> ascents = ascentRepository.search(userId, routenId);
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

}
