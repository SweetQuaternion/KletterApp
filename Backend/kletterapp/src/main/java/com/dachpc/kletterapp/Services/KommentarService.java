package com.dachpc.kletterapp.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dachpc.kletterapp.Dtos.KommentarCreateDTO;
import com.dachpc.kletterapp.Dtos.KommentarResponseDTO;
import com.dachpc.kletterapp.Entities.Kommentar;
import com.dachpc.kletterapp.Mappers.KommentarMapper;
import com.dachpc.kletterapp.Repositories.KommentarRepository;
import com.dachpc.kletterapp.Repositories.RoutenRepository;
import com.dachpc.kletterapp.Repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional // Stellt sicher, dass die Hibernate-Session wärend der ganzen Methode offen bleibt, damit die Lazy-Loaded-Properties der Entities geladen werden können
public class KommentarService {

    @Autowired
    private KommentarRepository kommentarRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoutenRepository routenRepository;

    @Autowired
    private KommentarMapper kommentarMapper;
    

    public KommentarResponseDTO addKommentar(KommentarCreateDTO dto) {
        Kommentar kommentar = kommentarMapper.toEntity(dto);
        kommentar.setDatum(java.time.LocalDateTime.now());
        kommentar.setUser(userRepository.getReferenceById(dto.getUserId()));
        kommentar.setRoute(routenRepository.getReferenceById(dto.getRoutenId()));
        Kommentar added = kommentarRepository.save(kommentar);
        return kommentarMapper.toResponseDTO(added);
    }

    public List<KommentarResponseDTO> findKommentarByRouteId(int routeId) {
        List<Kommentar> kommentare = kommentarRepository.findByRoute_id(routeId);
        return kommentare.stream().map(kommentarMapper::toResponseDTO).toList();
    }

    public KommentarResponseDTO updateKommentar(int id, String newText) {
        Kommentar prevKommentar = kommentarRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Kommentar nicht gefunden"));
        prevKommentar.setText(newText);
        Kommentar updated = kommentarRepository.save(prevKommentar);
        return kommentarMapper.toResponseDTO(updated);
    }

    public void deleteKommentar(int id) {
        Kommentar kommentar = kommentarRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Kommentar nicht gefunden"));
        kommentarRepository.delete(kommentar);
    }

}
