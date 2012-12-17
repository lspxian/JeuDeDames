package com.lishuopeng.info.JeuDeDames;
import java.util.ArrayList;
import java.util.List;

//
public class Damier {
	public static final int TAILLE = 10;
	private static Case[][] cases = new Case[TAILLE][TAILLE];
	private List<Pion> pionOrdinateur = new ArrayList<Pion>();
	private List<Pion> pionJoueur = new ArrayList<Pion>();

	Damier() {
		for (int i = 0; i < TAILLE; i++)
			for (int j = 0; j < TAILLE; j++) {
				cases[i][j] = new Case(i, j);
				// initialisation de chaque case, les Pions sont fabriqué dans
				// Case()
				Pion p = cases[i][j].getPion();
				if (p instanceof PionJoueur)
					this.pionJoueur.add(p);// add Pion dans la liste
				else if (p instanceof PionOrdinateur)
					this.pionOrdinateur.add(p);
			}
	}

	public Case[][] getCases() {
		return cases;
	}
	
	public static Case getCase(int x,int y){
		return cases[x][y];
	}

	public void echangeDesPions(Case d, Case a,Case m) {
		if(m!=null){// supprimer le pion au milieu s'il exsite
			pionOrdinateur.remove(m.getPion()); 
			pionJoueur.remove(m.getPion());
			m.setPion(null);
		}
		Pion p = d.getPion();// changement d'un pion et d'un espace
		try{p.setLigne(a.getLigne());} catch(NullPointerException e){
			System.out.println("error");
			//TODO
		}
		p.setColonne(a.getColonne());
		d.setPion(a.getPion());
		// un pion devient un dame
		if (a.getLigne() == 0 && p instanceof PionJoueur) {
			Pion dame = new DameJoueur(p);
			a.setPion(dame);
			pionJoueur.set(pionJoueur.indexOf(p), dame);
		} else if (a.getLigne() == TAILLE-1 && p instanceof PionOrdinateur) {
			Pion dame = new DameOrdinateur(p);
			a.setPion(dame);
			pionOrdinateur.set(pionOrdinateur.indexOf(p), dame);
		} else
			a.setPion(p);// pas de changement de dame
/*		DamierPanel2 d2=new DamierPanel2(pionOrdinateur,pionJoueur);
		frame.add()*/
	}

	public boolean jeuJoueur(int ligneArri, int colonneArri, int ligneDepa,
			int colonneDepa) {
		Case depart = cases[ligneDepa][colonneDepa];
		Case arrivee = cases[ligneArri][colonneArri];
		Coup actuel=new Coup(ligneDepa,colonneDepa,ligneArri-ligneDepa,colonneArri-colonneDepa,depart.getPion());
		List<Coup> coupsPrise = cherchePrise(pionJoueur);
		List<Coup> coupsDeplace=chercheDeplace(pionJoueur);
		if(!coupsPrise.isEmpty()){
			for(Coup c: coupsPrise) {
				if (actuel.equal(c)) {
					Case milieu=c.milieuCase();
					echangeDesPions(depart, arrivee, milieu);
					reprise(arrivee);
					return true;
				}
			}
			return false;
		}
		for(Coup c:coupsDeplace){
			if(actuel.equal(c)){
				echangeDesPions(depart,arrivee,null);
				return true;
			}
		}
		return false;
	}

	public void annulerEchange(Coup c){
		Case d=c.departCase();
		Case a=c.arriveeCase();
		Pion p=c.getDepartPion();
		p.setLigne(d.getLigne());
		p.setColonne(d.getColonne());
		d.setPion(p);
			if(a.getPion() instanceof PionJoueur||a.getPion() instanceof DameJoueur)
				try{pionJoueur.set(pionJoueur.indexOf(a.getPion()), p);}catch(ArrayIndexOutOfBoundsException e){
					System.out.println("error");
				}
			else if(a.getPion() instanceof PionOrdinateur||a.getPion() instanceof DameOrdinateur)
				pionOrdinateur.set(pionOrdinateur.indexOf(a.getPion()), p); 
		if(c instanceof CoupPrise){
			Case m=c.milieuCase();
			Pion p2=((CoupPrise) c).getMilieuPion();
			p2.setLigne(m.getLigne());
			p2.setColonne(m.getColonne());
			m.setPion(p2);
			if(p2 instanceof PionOrdinateur|| p2 instanceof DameOrdinateur)
				pionOrdinateur.add(p2);
			else if(p2 instanceof PionJoueur|| p2 instanceof DameJoueur)
				pionJoueur.add(p2);
		}
		a.setPion(null);
		
	}
	
	// faire la prise et la reprise
	public List<Coup> reprise(Case arrivee) {
		List<Coup> alc=new ArrayList<Coup>();
		try{alc=arrivee.getPion().cherchePrise();}catch(NullPointerException e){
			System.out.println("error");
			//TODO
		}
		List<Coup> tousLesPrise=new ArrayList<Coup>();
		while (!alc.isEmpty()) {
			Coup p=alc.get(0);
			echangeDesPions(p.departCase(),p.arriveeCase(),p.milieuCase());
			tousLesPrise.add(p);
			alc=p.arriveeCase().getPion().cherchePrise();
		}
		return tousLesPrise;
	}
	
	public void annulerReprise(List<Coup> lc){
		while(!lc.isEmpty()){
			annulerEchange(lc.get(lc.size()-1));
			lc.remove(lc.size()-1);
		}
	}
	
	public List<Pion> getPionOrdinateur() {
		return pionOrdinateur;
	}

	public List<Pion> getPionJoueur() {
		return pionJoueur;
	}

	public void jeuOrdinateur() {
		List<Coup> coupsPrise = cherchePrise(pionOrdinateur);
		List<Coup> coupsDeplace=chercheDeplace(pionOrdinateur);
		if(!coupsPrise.isEmpty()){
			reprise(coupsPrise.get(0).departCase());
			}
		else{//TODO
			//int n=(int)(Math.random()*coupsDeplace.size());
			//echangeDesPions(coupsDeplace.get(n).departCase(),coupsDeplace.get(n).arriveeCase(),null);
			Coup meilleur=minMax(4);
			echangeDesPions(meilleur.departCase(),meilleur.arriveeCase(),null);
		}
	}
	
	public boolean gagnantHomme() {
		if (finDuJeu(pionOrdinateur))
			return true;
		return false;
	}

	public boolean gagnantOrdi() {
		if (finDuJeu(pionJoueur))
			return true;
		return false;
	}

	public boolean finDuJeu(List<Pion> pions) {
		if (pions.size() == 0)
			return true;// il y a plus de pion
		// tous les pions sont bloqués
		else if (cherchePrise(pions).isEmpty() && chercheDeplace(pions).isEmpty())
			return true;
		return false;
	}

	// chercher un deplacement
	private List<Coup> chercheDeplace(List<Pion> pions) {
		List<Coup> lc=new ArrayList<Coup>();
		for (Pion p : pions) {
			List<Coup> tmp=p.chercheDeplace();
			if(!tmp.isEmpty()) lc.addAll(tmp);
		}
		return lc;
	}

	// chercher une prise pour tous les pions
	public List<Coup> cherchePrise(List<Pion> pions) {
		List<Coup> lc=new ArrayList<Coup>();
		for (Pion p : pions) {
			List<Coup> tmp=p.cherchePrise();
			if(!tmp.isEmpty()) lc.addAll(tmp);
		}
		return lc;
	}

	// chercher la prise dans un des quatre sens, c pion d'adversaire
	public static Coup priseSens(int x, int y, int i, int j,int k,int l, Class c) {
		try {
			if (cases[x+k][y+l].getPion() == null) {
				return new CoupPrise(x,y,i,j,k,l,cases[x][y].getPion(),cases[x+i][y+j].getPion());
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return null;
	}

	// chercher la deplacement dans un des quatre sens
	public static Coup deplaceSens(int x, int y, int i, int j) {
		try {
			if (cases[x + i][y + j].getPion() == null)
				return new Coup(x,y,i,j,cases[x][y].getPion());
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return null;
	}

	// le pion de case(x)(y) est un pion de type c
	public static boolean isPionC(int x, int y, Class c) {
		try{
			Pion p = cases[x][y].getPion();
			if (p != null)// le pion existe et comparer les Class
				return p.getClass().equals(c)
						|| p.getClass().getSuperclass().equals(c);
		}catch (ArrayIndexOutOfBoundsException e) {}
		return false;
		// return c.isInstance(cases[x][y].getPion());
	}
	
	public static boolean pionNull(int x, int y){
		try{
			Pion p = cases[x][y].getPion();
			if (p == null)// le pion existe et comparer les Class
				return true;
		}catch (ArrayIndexOutOfBoundsException e) {}
		return false;
	}
	
	public Coup minMax(int profondeur){
		List<Coup> coupsDeplace=chercheDeplace(pionOrdinateur);
		int maxValue=-10000,value;
		Coup meilleur=new Coup();
		for(Coup c:coupsDeplace){
			echangeDesPions(c.departCase(),c.arriveeCase(),null);
			value=min(profondeur);
			if(value>maxValue){
				maxValue=value;
				meilleur=c;
			}
			annulerEchange(c);
		}
		return meilleur;
	}

	private int min(int profondeur) {
		if(profondeur==0||gagnantOrdi()) return eval();
		int minValue=1000,value;
		List<Coup> coupsPrise=cherchePrise(pionJoueur);
		if(!coupsPrise.isEmpty()){
			List<Coup> lc=reprise(coupsPrise.get(0).departCase());
			minValue=max(profondeur-1);
			annulerReprise(lc);
		}
		else{
			List<Coup> coupsDeplace=chercheDeplace(pionJoueur);
			for(Coup c:coupsDeplace){
				echangeDesPions(c.departCase(),c.arriveeCase(),null);
				value=max(profondeur-1);
				if(value<minValue)	minValue=value;
				annulerEchange(c);
			}
		}
		return minValue;
	}

	private int max(int profondeur) {
		if(profondeur==0||gagnantHomme()) return eval();
		int maxValue=-1000,value;
		List<Coup> coupsPrise=cherchePrise(pionOrdinateur);
		if(!coupsPrise.isEmpty()){
			List<Coup> lc=reprise(coupsPrise.get(0).departCase());
			maxValue=min(profondeur-1);
			annulerReprise(lc);
		}
		else{
			List<Coup> coupsDeplace=chercheDeplace(pionOrdinateur);
			for(Coup c:coupsDeplace){
				echangeDesPions(c.departCase(),c.arriveeCase(),null);
				value=min(profondeur-1);
				if(value>maxValue)	maxValue=value;
				annulerEchange(c);
			}
		}
		return maxValue;
	}

	private int eval() {
		if(gagnantOrdi()) return 100;
		else if(gagnantHomme()) return -100;
		else{
			int e=pionOrdinateur.size()-pionJoueur.size();
			for(Pion p:pionOrdinateur)
				if(p instanceof DameOrdinateur)	e=e+3;
			for(Pion p:pionJoueur)
				if(p instanceof DameJoueur) e=e-3;
			return e;
		}
	}
	
}
