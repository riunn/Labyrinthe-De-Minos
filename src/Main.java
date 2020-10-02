import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 
 */

/**
 * @author Nassim
 * @author Sibory
 * cette classe est la phase de lancement du programme 
 */
public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		try {
			Fenetre fenetre = new Fenetre(); 
			
		} catch(NumberFormatException exception) {
			System.out.println("Le numéro d'entrée pour la taille du labyrinthe doit être un entier");
		}

	}
	
}

/**
 * Ceci est le remplacement de JPanel pour les labyrinthes qui stocke comme élément de données le labyrinthe
 * et appelle la fonction de dessin des labyrinthes
 */

class LabyrinthePanel extends JPanel{

	private Labyrinthe labyrinthe; // l'objet labyrinthe
	
	/*
	 * Constructeur de la classe LabyrinthePanel
	 */
	public LabyrinthePanel(Labyrinthe monLabyrinthe) {
		labyrinthe = monLabyrinthe;
		
		JButton bouton = new JButton("Générer labyrinthe");
		add(bouton);
		
		bouton.addActionListener(new ActionListener() {		//ACTION LISTENERS
			@Override
			public void actionPerformed(ActionEvent e) {
				// Appelle d'une nouvelle fenêtre afin de generer le labyrinthe
				
				new Fenetre().setVisible(true);
			}
		});
	}
	
	/*
	 * La méthode paintComponent est appelée à chaque fois que le panneau doit être affiché ou actualisé
	 * Tout ce qu'on veut dessiner sur le panel doit être dessiné dans cette méthode.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.darkGray); 
		
		this.setPreferredSize(labyrinthe.tailleFenetre());
		
		labyrinthe.draw(g);
		
	
	}
}



