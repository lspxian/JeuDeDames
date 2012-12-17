package com.lishuopeng.info.JeuDeDames;

import java.util.ArrayList;
import java.util.List;

public abstract class Pion implements Cloneable{
	protected int ligne;
	protected int colonne;

	public Pion clone(){
		try {
			return (Pion)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void setLigne(int ligne) {
		this.ligne = ligne;
	}

	public void setColonne(int colonne) {
		this.colonne = colonne;
	}

	public int getLigne() {
		return ligne;
	}

	public int getColonne() {
		return colonne;
	}

	Pion() {
	}

	Pion(int i, int j) {
		ligne = i;
		colonne = j;
	}

	public abstract List<Coup> cherchePrise();

	public abstract List<Coup> chercheDeplace();

	public List<Coup> cherchePriseGene(int m,int n, int p, int q, Class c) {
		List<Coup> lc=new ArrayList<Coup>();
		Coup a=new Coup();
		for(int i=1;i<m;i++){
			if(!Damier.pionNull(ligne+i, colonne+i)){
				if(Damier.isPionC(ligne + i, colonne + i, c)){
					for(int j=i+1;j<=m;j++){
						a=Damier.priseSens(ligne, colonne, i, i, j, j, c);
						if(a!=null) lc.add(a);
						else break;
					}
				}
				break;
			}
		}
		for(int i=1;i<n;i++){
			if(!Damier.pionNull(ligne+i, colonne-i)){
				if(Damier.isPionC(ligne + i, colonne - i, c)){
					for(int j=i+1;j<=n;j++){
						a=Damier.priseSens(ligne, colonne, i, -i, j, -j, c);
						if(a!=null) lc.add(a);
						else break;
					}
				}
				break;
			}
		}
		for(int i=1;i<p;i++){
			if(!Damier.pionNull(ligne-i, colonne+i)){
				if(Damier.isPionC(ligne - i, colonne + i, c)){
					for(int j=i+1;j<=p;j++){
						a=Damier.priseSens(ligne, colonne, -i, i, -j, j, c);
						if(a!=null) lc.add(a);
						else break;
					}
				}
				break;
			}
		}
		for(int i=1;i<q;i++){
			if(!Damier.pionNull(ligne-i, colonne-i)){
				if(Damier.isPionC(ligne - i, colonne - i, c)){
					for(int j=i+1;j<=q;j++){
						a=Damier.priseSens(ligne, colonne, -i, -i, -j, -j, c);
						if(a!=null) lc.add(a);
						else break;
					}
					break;
				}
			}
		}
		return lc;
	}

//m No d'itératin vers bas à droit. n No d'itération vers bas à gouche
	public List<Coup> chercheDeplaceVersBas(int m,int n) {
		List<Coup> lc=new ArrayList<Coup>();
		Coup a=new Coup();
		for(int i=1;i<=m;i++){
			a=Damier.deplaceSens(ligne, colonne, i, i);
			if(a!=null) lc.add(a);
			else break;
		}
		for(int i=1;i<=n;i++){
			a=Damier.deplaceSens(ligne, colonne, i, -i);
			if(a!=null) lc.add(a);
			else break;
		}
		return lc;
	}
	//m No d'itératin vers haut à droit. n No d'itération vers haut à gouche
	public List<Coup> chercheDeplaceVersHaut(int m, int n) {
		List<Coup> lc=new ArrayList<Coup>();
		Coup a=new Coup();
		for(int i=1;i<=m;i++){
			a=Damier.deplaceSens(ligne, colonne, -i, i);
			if(a!=null) lc.add(a);
			else break;
		}
		for(int i=1;i<=n;i++){
			a=Damier.deplaceSens(ligne, colonne, -i, -i);
			if(a!=null) lc.add(a);
			else break;
		}
		return lc;
	}
}
