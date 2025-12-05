package com.example.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter                
@NoArgsConstructor             
@AllArgsConstructor
@Builder  
public class Piatto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String nome;

    @Column(nullable = false, length = 30)
    private String categoria;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prezzo;

    @Column(nullable = false, length = 120)
    private String immagine;

    @ElementCollection
    @CollectionTable(name = "piatto_ingredienti",
            joinColumns = @JoinColumn(name = "piatto_id"))
    @Column(name = "ingredienti", nullable = false, length = 60)
    private List<String> ingredienti = new ArrayList<>();
}
