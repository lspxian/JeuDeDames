package com.lishuopeng.info.JeuDeDames;
public class Case {
	private Pion pion;
	private int ligne;
	private int colonne;

	Case() {
	}

	Case(int i, int j) {
		this.ligne = i;
		this.colonne = j;
		if ((i + j) % 2 == 1 && i < 4)
			this.pion = new PionOrdinateur(i, j);
		else if ((i + j) % 2 == 1 && i > 5)
			this.pion = new PionJoueur(i, j);
		else
			this.pion = null;
	}

	public Pion getPion() {
		return pion;
	}

	public void setPion(Pion pion) {
		this.pion = pion;
	}

	public int getLigne() {
		return ligne;
	}

	public void setLigne(int ligne) {
		this.ligne = ligne;
	}

	public int getColonne() {
		return colonne;
	}

	public void setColonne(int colonne) {
		this.colonne = colonne;
	}

}
