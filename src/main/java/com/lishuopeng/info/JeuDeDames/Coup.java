package com.lishuopeng.info.JeuDeDames;
public class Coup {
	protected int ligne;
	protected int colonne;
	protected int horizontal;
	protected int vertical;
	protected Pion departPion;

	public Pion getDepartPion() {
		return departPion;
	}

	Coup(int ligne, int colonne, int vertical, int horizontal, Pion departPion) {
		this.ligne = ligne;
		this.colonne = colonne;
		this.horizontal = horizontal;
		this.vertical = vertical;
		this.departPion=departPion;
	}

	public Coup() {	}

	public boolean equal(Coup c) {
		if (this.colonne == c.getColonne() && this.ligne == c.getLigne()
				&& this.horizontal == c.getHorizontal()
				&& this.vertical == c.getVertical())
			return true;
		return false;
	}

	public int getLigne() {
		return ligne;
	}

	public int getColonne() {
		return colonne;
	}

	public int getHorizontal() {
		return horizontal;
	}

	public int getVertical() {
		return vertical;
	}
	public Case departCase(){
		return Damier.getCase(ligne, colonne);
	}
	public Case arriveeCase(){
		return Damier.getCase(ligne+vertical, colonne+horizontal);
	}
	public Case milieuCase() {return null;};
}

class CoupPrise extends Coup{
	private int milieuH;
	private int milieuV;
	private Pion milieuPion;
	
	public Pion getMilieuPion() {
		return milieuPion;
	}
	public int getmilieuH() {
		return milieuH;
	}
	public int getmilieuV() {
		return milieuV;
	}
	CoupPrise(int ligne, int colonne, int milieuV, int milieuH,int horizontal, int vertical,Pion departPion,Pion milieuPion){
		super(ligne,colonne,horizontal,vertical,departPion);
		this.milieuH=milieuH;
		this.milieuV=milieuV;
		this.milieuPion=milieuPion;
	}
	public CoupPrise() {
		super();
		}
	public boolean equal(CoupPrise c){
		if(super.equal(c)&&this.milieuH==c.getmilieuH()&&this.milieuV==c.getmilieuV())
			return true;
		return false;
	}
	public Case milieuCase(){
		return Damier.getCase(ligne+milieuV, colonne+milieuH);
	}
}