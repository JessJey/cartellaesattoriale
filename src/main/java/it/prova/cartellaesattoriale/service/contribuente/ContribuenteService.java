package it.prova.cartellaesattoriale.service.contribuente;

import java.util.List;

import it.prova.cartellaesattoriale.model.Contribuente;


public interface ContribuenteService {

	List<Contribuente> listAllElements();
	
	List<Contribuente> listAllElementsEager();

	Contribuente caricaSingoloElemento(Long id);
	
	Contribuente caricaSingoloElementoConCartelleEsattoriali(Long id);

	Contribuente aggiorna(Contribuente contribuenteInstance);

	Contribuente inserisciNuovo(Contribuente contribuenteInstance);

	void rimuovi(Contribuente contribuenteInstance);
	
	List<Contribuente> findByExample(Contribuente example);
}
