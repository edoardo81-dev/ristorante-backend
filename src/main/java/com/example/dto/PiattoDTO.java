package com.example.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PiattoDTO {
    private Long id;
    private String nome;
    private String categoria;
    private String immagine;
    private BigDecimal prezzo;
    private List<String> ingredienti;
}
