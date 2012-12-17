package com.lishuopeng.info.JeuDeDames;

import java.util.List;

class PionOrdinateur extends Pion {
	protected Class advs = PionJoueur.class;

	PionOrdinateur() {
	}

	PionOrdinateur(int i, int j) {
		super(i, j);
	}

	public List<Coup> cherchePrise() {
		return cherchePriseGene(2,2,2,2,advs);
	}

	public List<Coup> chercheDeplace() {
		return chercheDeplaceVersBas(1,1);
	}
}