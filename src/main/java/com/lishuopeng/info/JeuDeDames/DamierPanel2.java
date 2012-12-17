package com.lishuopeng.info.JeuDeDames;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class DamierPanel2 extends JPanel{
	private ArrayList<Ellipse2D> pionsRouge;
	private ArrayList<Ellipse2D> pionsJaune;

	public DamierPanel2(List<Pion> haut,List<Pion> bas) {
		miseAJour(haut,bas);
		repaint();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void miseAJour(List<Pion> haut,List<Pion> bas) {
		pionsRouge = new ArrayList<Ellipse2D>();
		pionsJaune = new ArrayList<Ellipse2D>();
		for (Pion p : haut) {
			if (DameOrdinateur.class.isInstance(p))
				// dame rayon=30
				pionsRouge.add(new Ellipse2D.Double(p.getColonne() * 50, p
						.getLigne() * 50, 30, 30));
			else
				// pion rayon=50
				pionsRouge.add(new Ellipse2D.Double(p.getColonne() * 50, p
						.getLigne() * 50, 50, 50));
		}
		for (Pion p : bas) {
			if (DameJoueur.class.isInstance(p))
				pionsJaune.add(new Ellipse2D.Double(p.getColonne() * 50, p
						.getLigne() * 50, 30, 30));
			else
				pionsJaune.add(new Ellipse2D.Double(p.getColonne() * 50, p
						.getLigne() * 50, 50, 50));
		}
	}
		public void paintComponent(Graphics g) {
			Graphics2D go = (Graphics2D) g;
			// le damier
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					if ((i + j) % 2 == 0)
						go.setPaint(Color.white);
					else
						go.setPaint(Color.gray);
					go.fill(new Rectangle2D.Double(i * 50, j * 50, 50, 50));
				}
			}
			// les pions
			for (Ellipse2D e : pionsRouge) {
				go.setPaint(Color.red);
				go.fill(e);
			}
			for (Ellipse2D e : pionsJaune) {
				go.setPaint(Color.yellow);
				go.fill(e);
			}
		}
}
