import java.awt.*;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * @author Nassim
 * @author Sibory
 * Objet labyrinthe qui crée un labyrinthe à l'aide d'un ensemble disjoint
 * représentant des cellules et exécutant une version modifiée de Kruskal
 * Algorithme pour supprimer les murs. A la fin, les murs du labyrinthe sont dessinés ainsi 
 * qu'un deplacement unique en points rouge
 */

public class Labyrinthe {
	public static final int CELL_WIDTH = 30; // format carré du labyrinthe
	public static final int MARGIN = 50; // tampon entre le bord de la fenêtre et le labyrinthe
	public static final int DOT_SIZE = 10; // taille du carré de la solution du labyrinthe
	public static final int DOT_MARGIN = 10; // espace entre les murs et la solution
	private int N;
	private Cell[] cells; // tableau contenant toutes les cellules du labyrinthe

	/*
	 * Constructeur de la classe labyrinthe
	 */
	public Labyrinthe(int n) {    
		N = n; 
		cells = new Cell[N * N]; // crée un tableau de cellules
		
		// Boucle for qui va initialisé le tableau avec les objets Cell
		for(int i = 0; i < N * N; i++) {
			cells[i] = new Cell(); 
		}
		
		if(N > 0) {
			faireMurs(); // met à jours les informations sur le mur à l'intérieur de chaque objet Cell
			detruitMurs(); // détruit le mur jusqu'à la formation d'un labyrinthe
		}
		
	}

	/**
	 * Classe représentant une cellule dans un labyrinthe
	 */
	public class Cell {
		int[] murs; // tableau représentant les murs nord, sud, est et ouest

		/*
		 * Constructeur
		 */
		public Cell() {
			murs = new int[4]; 
		}
	}
	
	final int NORD = 0;
	final int SUD = 1;
	final int EST = 2;
	final int OUEST = 3;
	
	private void faireMurs() {
		// définit les murs nord, sud, est et ouest
		for(int i = 0; i < N * N; i++) {
			cells[i].murs[NORD] = i - N;
			cells[i].murs[SUD] = i + N; 
			cells[i].murs[EST] = i + 1; 
			cells[i].murs[OUEST] = i - 1; 
		}
		
		for(int i = 0; i < N; i++) {
			cells[i].murs[NORD] = -1; // situé dans les cellules nord de la frontière, mur nord à -1
			cells[N * N - i - 1].murs[SUD] = -1; // situé dans les cellules frontalières sud, mur sud à -1
		}
		
		for(int i = 0; i < N * N; i += N) {
			cells[N * N - i - 1].murs[EST] = -1; // situé dans les cellules est de la frontière, mur est à -1
			cells[i].murs[OUEST] = -1; // situé dans les cellules frontalières ouest, mur ouest à -1
		}
	}
	
	// Détruit les murs avec une version modifiée de l'algorithme de Kruskal
	private void detruitMurs() {
		int NumElements = N * N; 
		
		Kruskal ensembleDisjoint= new Kruskal(NumElements); // crée un ensemble disjoint pour représenter les cellules
		
		for(int k = 0; k < N * N; k++) {
			ensembleDisjoint.find(k); // ajoute chaque cellule à un seul ensemble
		}
		
		// Générateur aléatoire
		Random generateur = new Random(); 
		
		// Tant que tout les éléments de l'ensemble disjoint ne sont pas connectés
		while(ensembleDisjoint.creeEnsemble() == false) {
			int cell1 = generateur.nextInt(N * N); // Choisis une cellule aléatoire
			int mur = generateur.nextInt(4); 
			
			int cell2 = cells[cell1].murs[mur]; // Choisis une seconde cellules aléatoire
			
			// S'il existe un mur entre ces deux cellules
			if (cell2 != -1 && cell2 != N * N) {
				// Si les cellules n'appartiennent pas au même ensemble
				if(ensembleDisjoint.find(cell1) != ensembleDisjoint.find(cell2)) {
					cells[cell1].murs[mur] = N * N; // Détruit les murs entre ces deux cellules. N*N ne représentera aucun mur
					cells[N * N - 1].murs[EST] = N * N; // détruit le mur en bas à droite pour crée la sortie
					
					if(mur == NORD || mur == EST) {
						cells[cell2].murs[mur + 1] = N * N; 
					}
					
					if(mur == SUD || mur == OUEST) {
						cells[cell2].murs[mur - 1] = N * N;
					}
					
					// Fait une union de l'ensemble de ces deux cellules, par laquelle un deplacement vient d'être crée
					ensembleDisjoint.union(ensembleDisjoint.find(cell1), ensembleDisjoint.find(cell2)); 
				}
			}
		}
	}
	
	/*
	 * Permet de dessiner le labyrinthe
	 */
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(15));
		
		for(int i = 0; i < N; i++) {
			int compteur = i; 
			for(int j = 0; j < N; j++) {
				if(j != 0) {
					compteur += N; 
				}
				
				
				// S'il existe un mur au nord 
				if(cells[compteur].murs[NORD] != N * N) {
					g2.drawLine((i * CELL_WIDTH + MARGIN), (j * CELL_WIDTH + MARGIN),
							((i + 1) * CELL_WIDTH + MARGIN), (j * CELL_WIDTH + MARGIN));
				}
				
				// S'il existe un mur au sud
				if(cells[compteur].murs[SUD] != N * N) {
					g2.drawLine(i * CELL_WIDTH + MARGIN, (j + 1) * CELL_WIDTH
							+ MARGIN, (i + 1) * CELL_WIDTH + MARGIN, (j + 1) * CELL_WIDTH
							+ MARGIN);
				}
				
				// S'il existe un mur à l'est
				if(cells[compteur].murs[EST] != N * N) {
					g2.drawLine((i + 1) * CELL_WIDTH + MARGIN, j * CELL_WIDTH
							+ MARGIN, (i + 1) * CELL_WIDTH + MARGIN, (j + 1) * CELL_WIDTH
							+ MARGIN);
				}
				
				// S'il existe un mur à l'ouest
				if(cells[compteur].murs[OUEST] != N * N) {
					g2.drawLine(i * CELL_WIDTH + MARGIN, j * CELL_WIDTH + MARGIN, i
							* CELL_WIDTH + MARGIN, (j + 1) * CELL_WIDTH + MARGIN);
				}
			}
		}
		
		/*
		 * Position du héros
		 */
		g.setColor(Color.RED);
		g.fillOval(0 * CELL_WIDTH + MARGIN + DOT_MARGIN, 0 * CELL_WIDTH
			+ MARGIN + DOT_MARGIN, DOT_SIZE, DOT_SIZE); 
		
		/*
		 * Position du minotaure
		 */
		g.setColor(Color.MAGENTA);
		g.fillOval(10 * CELL_WIDTH + MARGIN + DOT_MARGIN, 5 * CELL_WIDTH
			+ MARGIN + DOT_MARGIN, DOT_SIZE, DOT_SIZE); 

	}

	public Dimension tailleFenetre() {
		return new Dimension(N * CELL_WIDTH + MARGIN * 2, N * CELL_WIDTH + MARGIN * 2);
	}
}
