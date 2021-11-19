package it.prova.cartellaesattoriale.service.cartellaesattoriale;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.prova.cartellaesattoriale.model.CartellaEsattoriale;
import it.prova.cartellaesattoriale.repository.cartellaesattoriale.CartellaEsattorialeRepository;

public class CartellaEsattorialeServiceImpl implements CartellaEsattorialeService {

	@Autowired
	private CartellaEsattorialeRepository repository;
	
	@Override
	@Transactional
	public List<CartellaEsattoriale> listAllElements(boolean eager) {
		if (eager)
			return (List<CartellaEsattoriale>) repository.findAllCartellaEsattorialeEager();

		return (List<CartellaEsattoriale>) repository.findAll();
	}

	@Override
	@Transactional
	public CartellaEsattoriale caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public CartellaEsattoriale caricaSingoloElementoEager(Long id) {
		return repository.findSingleCartellaEsattorialeEager(id);
	}

	@Override
	@Transactional
	public CartellaEsattoriale aggiorna(CartellaEsattoriale cartellaEsattorialeInstance) {
		return repository.save(cartellaEsattorialeInstance) ;
	}

	@Override
	@Transactional
	public CartellaEsattoriale inserisciNuovo(CartellaEsattoriale cartellaEsattorialeInstance) {
		return repository.save(cartellaEsattorialeInstance) ;
	}

	@Override
	@Transactional
	public void rimuovi(CartellaEsattoriale cartellaEsattorialeInstance) {
		repository.delete(cartellaEsattorialeInstance);
	}

	@Override
	@Transactional
	public List<CartellaEsattoriale> findByExample(CartellaEsattoriale example) {
		return repository.findByExample(example);
	}

}
