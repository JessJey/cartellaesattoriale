package it.prova.cartellaesattoriale.model;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;


public enum Stato {

	CREATA("Creata"), IN_VERIFICA("In verifica"), CONCLUSA("Conclusa"), IN_CONTENZIOSO("In contenzioso");
	
	private String abbreviazione;

	Stato(String abbreviazione) {
		this.abbreviazione = abbreviazione;
	}

	public String getAbbreviazione() {
		return abbreviazione;
	}

	@JsonCreator
	public static Stato getStatoFromCode(String input) {
		if(StringUtils.isBlank(input))
			return null;
		
		for (Stato statoItem : Stato.values()) {
			if (statoItem.equals(Stato.valueOf(input))) {
				return statoItem;
			}
		}
		return null;
	}
}
