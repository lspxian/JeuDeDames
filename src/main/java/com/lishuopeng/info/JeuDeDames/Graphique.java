package com.lishuopeng.info.JeuDeDames;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Graphique {
	public static void main(String[] args) {
		DamierFrame frame = new DamierFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

class DamierFrame extends JFrame {
	public DamierFrame() {
		this.setTitle("Jeu de Dames");
		this.setSize(800, 600);
		DamierPanel panel = new DamierPanel();
		add(panel);
	}
}

class DamierPanel extends JPanel {
	private ArrayList<Ellipse2D> pionsRouge;
	private ArrayList<Ellipse2D> pionsJaune;
	private Ellipse2D unPion;
	private Ellipse2D unPionOr;
	private Damier damier;

	public DamierPanel() {
		damier = new Damier();
		unPion = null;
		unPionOr = null;
		// initialiser les pions dans une liste
		miseAJour();
		this.addMouseListener(new MouseHandler());
		this.addMouseMotionListener(new MouseMotionHandler());
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

	public Ellipse2D find(Point2D d) {
		for (Ellipse2D e : pionsJaune)
			if (e.contains(d))
				return e;
		/*
		 * for(Ellipse2D e:pionsRouge) if(e.contains(d)) return e;
		 */
		return null;
	}

	public void miseAJour() {
		List<Pion> haut = damier.getPionOrdinateur();
		List<Pion> bas = damier.getPionJoueur();
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

	private class MouseHandler extends MouseAdapter {
		public void mousePressed(MouseEvent event) {
			unPion = find(event.getPoint());
			unPionOr = (Ellipse2D) unPion.clone();
		}

		public void mouseReleased(MouseEvent event) {
			int x = ((int) event.getX() / 50);
			int y = ((int) event.getY() / 50);
			// arrivee cases[y][x] depart cases[getx][gety]
			// regle fait aussi la modification
			if (unPion != null
					&& damier.jeuJoueur(y, x, (int) unPionOr.getY() / 50,
							(int) unPionOr.getX() / 50)) {
				miseAJour();
				repaint();
				if (damier.gagnantHomme()) {//
					event.consume();
					System.out.println("Vous avez gagne");
				} else {
					damier.jeuOrdinateur();
					//miseAJour();
					//repaint();
					if (damier.gagnantOrdi()) {
						event.consume();
						System.out.println("Vous avez perdu");
					}
				}
			} else {
				unPion.setFrame(unPionOr.getX(), unPionOr.getY(),
						unPion.getWidth(), unPion.getHeight());
				repaint();
			}
		}
	}

	private class MouseMotionHandler implements MouseMotionListener {
		public void mouseDragged(MouseEvent arg0) {
			if (unPion != null) {
				int x = arg0.getX();
				int y = arg0.getY();
				unPion.setFrame(x - 25, y - 25, unPion.getWidth(),
						unPion.getHeight());
				repaint();
			}
		}

		public void mouseMoved(MouseEvent arg0) {
			if (find(arg0.getPoint()) == null)
				setCursor(Cursor.getDefaultCursor());
			else
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}
}
