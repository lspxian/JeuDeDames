package com.lishuopeng.info.JeuDeDames;

import java.util.ArrayList;
import java.util.List;

class DameOrdinateur extends PionOrdinateur {
	DameOrdinateur(Pion p) {
		this.ligne = p.getLigne();
		this.colonne = p.getColonne();
	}

	public List<Coup> cherchePrise() {
		List<Coup> lc=new ArrayList<Coup>();
		int m=Math.min(Damier.TAILLE-ligne-1, Damier.TAILLE-colonne-1);
		int n=Math.min(Damier.TAILLE-ligne-1, colonne);
		int p=Math.min(ligne, Damier.TAILLE-colonne-1);
		int q=Math.min(ligne, colonne);
		lc.addAll(cherchePriseGene(m,n,p,q,advs));
		return lc;
	}

	public List<Coup> chercheDeplace() {
		List<Coup> lc=new ArrayList<Coup>();
		int m=Math.min(Damier.TAILLE-1-ligne, Damier.TAILLE-1-colonne);
		int n=Math.min(Damier.TAILLE-1-ligne, colonne);
		lc.addAll(chercheDeplaceVersBas(m,n));
		m=Math.min(ligne, Damier.TAILLE-1-colonne);
		n=Math.min(ligne, colonne);
		lc.addAll(chercheDeplaceVersHaut(m,n));
		return lc;
	}
}