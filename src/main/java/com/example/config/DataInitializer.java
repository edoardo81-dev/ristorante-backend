package com.example.config;

import com.example.model.Piatto;
import com.example.repository.PiattoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("local-mysql") // <-- attivalo solo quando vuoi seedare
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PiattoRepository piattoRepo;

    @Override
    @Transactional
    public void run(String... args) {
        long count = piattoRepo.count();
        if (count > 0) {
            System.out.println("[DataInitializer] DB già popolato (" + count + " record). Niente da fare.");
            return; // idempotente: non tocca i dati esistenti
        }

        // -------- PRIMI / SECONDI (6) --------
        Piatto carbonara = piatto(
            "Carbonara", "Primi", "carbonara.jpg", "10.00",
            List.of("Spaghetti","Uova","Guanciale","Pecorino Romano","Pepe nero")
        );

        Piatto amatriciana = piatto(
            "Amatriciana", "Primi", "amatriciana.jpg", "9.50",
            List.of("Bucatini","Guanciale","Pomodoro","Pecorino Romano","Peperoncino")
        );

        Piatto polloPeperoni = piatto(
            "Pollo con i Peperoni", "Secondi", "pollo_peperoni.jpg", "12.00",
            List.of("Pollo","Peperoni","Cipolla","Olio d'oliva","Sale")
        );

        Piatto filettoPepeVerde = piatto(
            "Filetto al Pepe Verde", "Secondi", "filetto_pepe_verde.jpg", "18.00",
            List.of("Filetto di manzo","Panna fresca","Pepe verde","Brandy","Burro")
        );

        Piatto risottoFunghi = piatto(
            "Risotto ai Funghi Porcini", "Primi", "risotto_funghi.jpg", "11.00",
            List.of("Riso Carnaroli","Funghi porcini","Brodo vegetale","Parmigiano Reggiano","Prezzemolo")
        );

        Piatto spigolaPatate = piatto(
            "Spigola in Crosta di Patate", "Secondi", "spigola_patate.jpg", "16.00",
            List.of("Spigola","Patate","Rosmarino","Olio d'oliva","Sale")
        );

        // -------- BEVANDE (8) --------
        Piatto acquaLiscia = piatto(
            "Acqua Liscia", "Bevande", "acqua_liscia.jpeg", "1.50",
            List.of()
        );

        Piatto acquaEffervescente = piatto(
            "Acqua Effervescente", "Bevande", "acqua_effervescente.jpeg", "1.80",
            List.of()
        );

        Piatto vinoBiancoChardonnay = piatto(
            "Vino Bianco Chardonnay", "Bevande", "vino_bianco_chardonnay.jpeg", "5.50",
            List.of()
        );

        Piatto vinoBiancoSauvignon = piatto(
            "Vino Bianco Sauvignon", "Bevande", "vino_bianco_sauvignon.jpeg", "6.00",
            List.of()
        );

        Piatto vinoBiancoPinotGrigio = piatto(
            "Vino Bianco Pinot Grigio", "Bevande", "vino_bianco_pinot_grigio.jpeg", "5.80",
            List.of()
        );

        Piatto vinoRossoMerlot = piatto(
            "Vino Rosso Merlot", "Bevande", "vino_rosso_merlot.jpeg", "6.50",
            List.of()
        );

        Piatto vinoRossoChianti = piatto(
            "Vino Rosso Chianti", "Bevande", "vino_rosso_chianti.jpeg", "7.00",
            List.of()
        );

        Piatto vinoRossoNeroDAvola = piatto(
            "Vino Rosso Nero d'Avola", "Bevande", "vino_rosso_neroavola.jpeg", "6.80",
            List.of()
        );

        // -------- DOLCI (3) --------
        Piatto tiramisu = piatto(
            "Tiramisù", "Dolci", "tiramisu.jpeg", "4.50",
            List.of("Mascarpone","Uova","Zucchero","Savoiardi","Caffè","Cacao amaro")
        );

        Piatto pannacotta = piatto(
            "Pannacotta ai Frutti di Bosco", "Dolci", "pannacotta_frutti_di_bosco.jpeg", "4.00",
            List.of("Panna fresca","Latte","Zucchero","Gelatina","Vaniglia","Frutti di bosco")
        );

        Piatto cremeCaramel = piatto(
            "Creme Caramel", "Dolci", "creme_caramel.jpeg", "3.80",
            List.of("Latte","Panna","Zucchero","Uova","Vaniglia","Zucchero per il caramello")
        );

        // Salvo tutto in un colpo
        piattoRepo.saveAll(List.of(
            carbonara, amatriciana, polloPeperoni, filettoPepeVerde, risottoFunghi, spigolaPatate,
            acquaLiscia, acquaEffervescente,
            vinoBiancoChardonnay, vinoBiancoSauvignon, vinoBiancoPinotGrigio,
            vinoRossoMerlot, vinoRossoChianti, vinoRossoNeroDAvola,
            tiramisu, pannacotta, cremeCaramel
        ));

        System.out.println("[DataInitializer] Seed completato (17 piatti).");
    }

    private Piatto piatto(String nome, String categoria, String immagine, String prezzo,
                          List<String> ingredienti) {
        Piatto p = new Piatto();
        p.setNome(nome);
        p.setCategoria(categoria);
        p.setImmagine(immagine); // verifica estensione coerente con asset FE
        p.setPrezzo(new BigDecimal(prezzo));
        // Usa una lista MUTABILE per l'ElementCollection
        p.setIngredienti(new ArrayList<>(ingredienti));
        return p;
    }
}
