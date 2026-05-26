package com.dachpc.kletterapp.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dachpc.kletterapp.Dtos.HalleCreateDTO;
import com.dachpc.kletterapp.Dtos.HalleResponseDTO;
import com.dachpc.kletterapp.Entities.Halle;
import com.dachpc.kletterapp.Mappers.HalleMapper;
import com.dachpc.kletterapp.Repositories.HallenRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class HalleService {
    
    @Autowired
    private HallenRepository hallenRepository;

    @Autowired
    private HalleMapper halleMapper;

    public List<HalleResponseDTO> findHalle(String search) {
        return hallenRepository.search(search).stream().map(halleMapper::toResponseDTO).toList();
    }

    public List<HalleResponseDTO> findAll() {
        return hallenRepository.findAll().stream().map(halleMapper::toResponseDTO).toList();
    }

    public HalleResponseDTO addHalle(HalleCreateDTO dto) {
        Halle entity = halleMapper.toEntity(dto);
        return halleMapper.toResponseDTO(hallenRepository.save(entity));
    }

    public HalleResponseDTO updateHalle(int id, HalleCreateDTO dto) {
        System.out.println(dto.toString());
        Halle entity = hallenRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Halle nicht gefunden"));
        entity.setName(dto.getName());
        entity.setAdresse(dto.getAdresse());
        entity.setBetreiber(dto.getBetreiber());
        return halleMapper.toResponseDTO(hallenRepository.save(entity));
    }

    public void deleteHalle(int id) {
        hallenRepository.deleteById(id);
    }

}
