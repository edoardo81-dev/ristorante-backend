package com.example.mapper;

import com.example.dto.PiattoDTO;
import com.example.model.Piatto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PiattoMapper {

    public PiattoDTO toDto(Piatto p) {
        if (p == null) return null;
        return PiattoDTO.builder()
                .id(p.getId())
                .nome(p.getNome())
                .categoria(p.getCategoria())
                .immagine(p.getImmagine())
                .prezzo(p.getPrezzo())
                .ingredienti(p.getIngredienti() == null
                        ? List.of()
                        : new ArrayList<>(p.getIngredienti()))
                .build();
    }

    public Piatto toEntity(PiattoDTO d) {
        if (d == null) return null;
        Piatto p = new Piatto();
        // se l'ID è auto-generato, puoi ignorare setId in create; tenerlo consente update/PUT futuri
        p.setId(d.getId());
        p.setNome(d.getNome());
        p.setCategoria(d.getCategoria());
        p.setImmagine(d.getImmagine());
        p.setPrezzo(d.getPrezzo());
        p.setIngredienti(d.getIngredienti() == null
                ? new ArrayList<>()
                : new ArrayList<>(d.getIngredienti()));
        return p;
    }
}
