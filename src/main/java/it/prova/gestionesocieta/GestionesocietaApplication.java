package it.prova.gestionesocieta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.gestionesocieta.service.test.BatteriaDiTestService;

@SpringBootApplication
public class GestionesocietaApplication implements CommandLineRunner{
	@Autowired
	private BatteriaDiTestService batteriaDiTestService;

	public static void main(String[] args) {
		SpringApplication.run(GestionesocietaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("############## Inizio Batteria test #################\n");
		
		batteriaDiTestService.inserisciSocieta();
		batteriaDiTestService.findSocietaByExample();
		batteriaDiTestService.rimuoviSocieta();
		batteriaDiTestService.inserisciDipendente();
		batteriaDiTestService.modificaDipendente();
		batteriaDiTestService.listaDistintaSocietaConRALDipendenteMaggiore3000();
		batteriaDiTestService.caricaDipendentePiuAnzianoInSocietaFondataPrima1990();
		
		System.out.println("############## Fine Batteria test #################\n");
	}

}
