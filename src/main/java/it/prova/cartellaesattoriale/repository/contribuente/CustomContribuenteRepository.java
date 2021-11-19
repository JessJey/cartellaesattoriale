package it.prova.cartellaesattoriale.repository.contribuente;

import java.util.List;

import it.prova.cartellaesattoriale.model.Contribuente;

public interface CustomContribuenteRepository {

	List<Contribuente> findByExample(Contribuente example);

}
