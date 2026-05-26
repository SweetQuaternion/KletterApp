package com.dachpc.kletterapp.Services;

import com.dachpc.kletterapp.Repositories.RoutenRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dachpc.kletterapp.Dtos.WandCreateDTO;
import com.dachpc.kletterapp.Dtos.WandResponseDTO;
import com.dachpc.kletterapp.Entities.Route;
import com.dachpc.kletterapp.Entities.Wand;
import com.dachpc.kletterapp.Mappers.WandMapper;
import com.dachpc.kletterapp.Repositories.WandRepository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class WandService {

    @Autowired
    RoutenRepository routenRepository;

    @Autowired
    private WandMapper wandMapper;

    @Autowired
    private WandRepository wandRepository;

    public WandResponseDTO addWand(WandCreateDTO dto) {
        Wand wand = wandMapper.toEntity(dto);
        Wand added = wandRepository.save(wand);
        return wandMapper.toResponseDTO(added);
    }

    public List<WandResponseDTO> getWändeByHallenId(int hallenId) {
        List<Wand> wände = wandRepository.findByHallenIdWithRouten(hallenId);
        return wände.stream().peek(wand -> wand.setRouten(wand.getRouten().stream().filter(Route::getIsActive).toList()))
            .map(wandMapper::toResponseDTO)
            .toList();
    }

    public WandResponseDTO updateWand(int id, WandCreateDTO dto) {
        Wand wand = wandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Wand nicht gefunden"));
        wandMapper.updateEntity(dto, wand);
        Wand updated = wandRepository.save(wand);
        return wandMapper.toResponseDTO(updated);
    }

    public void deleteWand(int id) {
        wandRepository.deleteById(id);
    }
    
}
