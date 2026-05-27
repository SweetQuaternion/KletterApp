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

    public List<WandResponseDTO> updateWände(int hallenId, List<WandResponseDTO> dtoList) {
        for (WandResponseDTO dto : dtoList) {
            // Wand wurde angelegt und dann wieder gelöscht, hier passiert nix
            if (dto.getId() == -1 && dto.getWandNr() == -1) {
                continue;
            }
            // Wand hat noch keine ID, also anlegen
            else if (dto.getId() == -1) {
                addWand(wandMapper.toCreateDTO(dto));
            }
            // WandNr -1 bedeutet, dass die Wand gelöscht werden soll, also löschen
            else if (dto.getWandNr() == -1) {
                deleteWand(dto.getId());
            }
            // Wand hat eine ID und wird nicht gelöscht, also war sie vorher schon da, also updaten (egal ob sich was geändert hat oder nicht)
            else {
                System.out.println("Wand mit ID " + dto.getId() + " und WandNr " + dto.getWandNr() + ", updaten...");
                updateWand(dto.getId(), wandMapper.toCreateDTO(dto));
            }
        }
        return getWändeByHallenId(hallenId);
    }

    // public List<WandResponseDTO> updateWände(int hallenId, List<Integer> ids, List<WandCreateDTO> dtoList) {
    //     if (ids.size() != dtoList.size()) {
    //         throw new IllegalArgumentException("Die Anzahl der IDs muss mit der Anzahl der DTOs übereinstimmen.");
    //     }
    //     List<Wand> wände = wandRepository.findByHallenId(hallenId);
    //     List<Integer> prevIds = wände.stream().map(Wand::getId).toList();
    //     Set<Integer> totalIdsSet = Set.copyOf(ids);
    //     totalIdsSet.addAll(prevIds);
    //     List<Integer> totalIds = totalIdsSet.stream().toList();
    //     for (int i = 0; i < totalIds.size(); i++) {
    //         int id = totalIds.get(i);
    //         if (prevIds.contains(id) && ids.contains(id)) {
    //             updateWand(id, dtoList.get(ids.indexOf(id)));
    //         }
    //         else if (!prevIds.contains(id) && ids.contains(id)) {
    //             addWand(dtoList.get(ids.indexOf(id)));
    //         }
    //         else if (prevIds.contains(id) && !ids.contains(id)) {
    //             deleteWand(id);
    //         }
    //     }
    //     return wandRepository.findByHallenId(hallenId).stream().map(wandMapper::toResponseDTO).toList();
    // }
}