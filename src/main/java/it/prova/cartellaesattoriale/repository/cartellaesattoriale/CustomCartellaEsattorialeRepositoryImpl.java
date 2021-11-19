package it.prova.cartellaesattoriale.repository.cartellaesattoriale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.prova.cartellaesattoriale.model.CartellaEsattoriale;

public class CustomCartellaEsattorialeRepositoryImpl implements CustomCartellaEsattorialeRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<CartellaEsattoriale> findByExample(CartellaEsattoriale example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder("select e from CartellaEsattoriale e where e.id = e.id ");

		if (StringUtils.isNotEmpty(example.getDescrizione())) {
			whereClauses.add(" e.descrizione  like :descrizione ");
			paramaterMap.put("titolo", "%" + example.getDescrizione() + "%");
		}
		if (example.getImporto() != null) {
			whereClauses.add(" e.importo =:importo ");
			paramaterMap.put("importo", example.getImporto());
		}
		if (example.getStato() != null) {
			whereClauses.add(" e.stato =:stato ");
			paramaterMap.put("stato", example.getStato());
		}
		if (example.getContribuente() != null && example.getContribuente().getId() != null) {
			whereClauses.add("e.contribuente.id = :contribuente_id ");
			paramaterMap.put("contribuente_id", example.getContribuente().getId());
		}

		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<CartellaEsattoriale> typedQuery = entityManager.createQuery(queryBuilder.toString(), CartellaEsattoriale.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}
}
