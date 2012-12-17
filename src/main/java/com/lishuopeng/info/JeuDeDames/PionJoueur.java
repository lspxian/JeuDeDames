package com.lishuopeng.info.JeuDeDames;

import java.util.List;

class PionJoueur extends Pion {
	protected Class advs = PionOrdinateur.class;

	PionJoueur() {
	}

	PionJoueur(int i, int j) {
		super(i, j);
	}

	public List<Coup> cherchePrise() {
		return cherchePriseGene(2,2,2,2,advs);
	}

	public List<Coup> chercheDeplace() {
		return chercheDeplaceVersHaut(1,1);
	}
}