package com.example.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class PiattoCreateUpdateDTO {

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(max = 80)
    private String nome;

    @NotBlank(message = "La categoria è obbligatoria")
    @Size(max = 30)
    private String categoria;

    @NotNull(message = "Il prezzo è obbligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "Il prezzo deve essere > 0")
    private BigDecimal prezzo;

    @NotBlank(message = "Il filename dell'immagine è obbligatorio")
    @Size(max = 120)
    private String immagine;

    // opzionale
    private List<@NotBlank String> ingredienti;

    // getter/setter
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public BigDecimal getPrezzo() { return prezzo; }
    public void setPrezzo(BigDecimal prezzo) { this.prezzo = prezzo; }
    public String getImmagine() { return immagine; }
    public void setImmagine(String immagine) { this.immagine = immagine; }
    public List<String> getIngredienti() { return ingredienti; }
    public void setIngredienti(List<String> ingredienti) { this.ingredienti = ingredienti; }
}
