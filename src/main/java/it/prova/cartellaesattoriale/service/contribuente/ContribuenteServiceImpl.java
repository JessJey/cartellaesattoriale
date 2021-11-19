package it.prova.cartellaesattoriale.service.contribuente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.cartellaesattoriale.model.Contribuente;
import it.prova.cartellaesattoriale.repository.contribuente.ContribuenteRepository;

@Service
public class ContribuenteServiceImpl implements ContribuenteService {

	@Autowired
	private ContribuenteRepository repository;

	@Override
	@Transactional
	public List<Contribuente> listAllElements() {
		return (List<Contribuente>) repository.findAll();
	}

	@Override
	@Transactional
	public List<Contribuente> listAllElementsEager() {
		return repository.findAllEager();
	}

	@Override
	@Transactional
	public Contribuente caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Contribuente caricaSingoloElementoConCartelleEsattoriali(Long id) {
		return repository.findByIdEager(id);
	}

	@Override
	@Transactional
	public Contribuente aggiorna(Contribuente contribuenteInstance) {
		return repository.save(contribuenteInstance);
	}

	@Override
	@Transactional
	public Contribuente inserisciNuovo(Contribuente contribuenteInstance) {
		return repository.save(contribuenteInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Contribuente contribuenteInstance) {
		repository.delete(contribuenteInstance);

	}

	@Override
	@Transactional
	public List<Contribuente> findByExample(Contribuente example) {
		return repository.findByExample(example);
	}

}
