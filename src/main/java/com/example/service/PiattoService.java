package com.example.service;

import com.example.dto.PiattoDTO;
import com.example.exception.NotFoundException;
import com.example.mapper.PiattoMapper;
import com.example.model.Piatto;
import com.example.repository.PiattoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PiattoService {

    private final PiattoRepository piattoRepo;
    private final PiattoMapper mapper;
    
    @Transactional
    public PiattoDTO update(Long id, PiattoDTO dto) {
        // 1) carico l’entità esistente
        Piatto entity = piattoRepo.findById(id)
                .orElseThrow(() -> new com.example.exception.NotFoundException("Piatto non trovato: id=" + id));

        // 2) aggiorno i campi “semplici”
        entity.setNome(dto.getNome());
        entity.setCategoria(dto.getCategoria());
        entity.setImmagine(dto.getImmagine());
        entity.setPrezzo(dto.getPrezzo());

        // 3) rimpiazzo completamente la lista ingredienti (se null → lista vuota)
        entity.getIngredienti().clear();
        if (dto.getIngredienti() != null) {
            entity.getIngredienti().addAll(dto.getIngredienti());
        }

        // 4) salvo e ritorno DTO
        Piatto saved = piattoRepo.save(entity);
        return mapper.toDto(saved);
    }


    /** Tutti i piatti (ingredienti inclusi) mappati a DTO, senza Stream. */
    public List<PiattoDTO> findAll() {
        List<Piatto> sorgente = piattoRepo.findAllWithIngredienti();
        List<PiattoDTO> risultato = new ArrayList<>(sorgente.size());
        for (Piatto p : sorgente) {
            risultato.add(mapper.toDto(p));
        }
        return risultato;
    }

    /** Piatti per categoria (ingredienti inclusi) mappati a DTO, senza Stream. */
    public List<PiattoDTO> findByCategoria(String categoria) {
        List<Piatto> sorgente = piattoRepo.findByCategoriaIgnoreCase(categoria);
        List<PiattoDTO> risultato = new ArrayList<>(sorgente.size());
        for (Piatto p : sorgente) {
            risultato.add(mapper.toDto(p));
        }
        return risultato;
    }

    /** Dettaglio per id (ingredienti inclusi) mappato a DTO. */
    public PiattoDTO findById(Long id) {
        Piatto p = piattoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Piatto non trovato: id=" + id));
        return mapper.toDto(p);
    }

    /** Crea nuovo piatto. */
    @Transactional
    public PiattoDTO create(PiattoDTO dto) {
        Piatto entity = mapper.toEntity(dto);
        Piatto saved = piattoRepo.save(entity);
        return mapper.toDto(saved);
    }

    /** Elimina per id. */
    @Transactional
    public void delete(Long id) {
        if (!piattoRepo.existsById(id)) {
            throw new NotFoundException("Piatto non trovato: id=" + id);
        }
        piattoRepo.deleteById(id);
    }

    /**
     * Lista ordinata per il form “conto”:
     * categorie: Primi → Secondi → Dolci → Bevande, poi nome asc.
     */
    public List<PiattoDTO> findAllOrderedForBill() {
        // ranking categorie
        Map<String, Integer> rank = new HashMap<>();
        rank.put("Primi", 1);
        rank.put("Secondi", 2);
        rank.put("Dolci", 3);
        rank.put("Bevande", 4);

        // carico tutti i piatti con ingredienti
        List<Piatto> piatti = new ArrayList<>(piattoRepo.findAllWithIngredienti());

        // ordino senza stream
        piatti.sort((p1, p2) -> {
            int r1 = rank.getOrDefault(p1.getCategoria(), Integer.MAX_VALUE);
            int r2 = rank.getOrDefault(p2.getCategoria(), Integer.MAX_VALUE);
            if (r1 != r2) return Integer.compare(r1, r2);
            return p1.getNome().compareToIgnoreCase(p2.getNome()); // stessa categoria → per nome
        });

        // mapping manuale in lista DTO
        List<PiattoDTO> ordinati = new ArrayList<>(piatti.size());
        for (Piatto p : piatti) {
            ordinati.add(mapper.toDto(p));
        }
        return ordinati;
    }
}
