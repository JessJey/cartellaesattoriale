package it.prova.cartellaesattoriale.service.cartellaesattoriale;

import java.util.List;

import it.prova.cartellaesattoriale.model.CartellaEsattoriale;


public interface CartellaEsattorialeService {

	List<CartellaEsattoriale> listAllElements(boolean eager);

	CartellaEsattoriale caricaSingoloElemento(Long id);

	CartellaEsattoriale caricaSingoloElementoEager(Long id);

	CartellaEsattoriale aggiorna(CartellaEsattoriale cartellaEsattorialeInstance);

	CartellaEsattoriale inserisciNuovo(CartellaEsattoriale cartellaEsattorialeInstance);

	void rimuovi(CartellaEsattoriale cartellaEsattorialeInstance);

	List<CartellaEsattoriale> findByExample(CartellaEsattoriale example);

}
