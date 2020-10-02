import java.awt.*;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * @author Nassim
 * @author Sibory
 * Objet labyrinthe qui cr�e un labyrinthe � l'aide d'un ensemble disjoint
 * repr�sentant des cellules et ex�cutant une version modifi�e de Kruskal
 * Algorithme pour supprimer les murs. A la fin, les murs du labyrinthe sont dessin�s ainsi 
 * qu'un deplacement unique en points rouge
 */

public class Labyrinthe {
	public static final int CELL_WIDTH = 30; // format carr� du labyrinthe
	public static final int MARGIN = 50; // tampon entre le bord de la fen�tre et le labyrinthe
	public static final int DOT_SIZE = 10; // taille du carr� de la solution du labyrinthe
	public static final int DOT_MARGIN = 10; // espace entre les murs et la solution
	private int N;
	private Cell[] cells; // tableau contenant toutes les cellules du labyrinthe

	/*
	 * Constructeur de la classe labyrinthe
	 */
	public Labyrinthe(int n) {    
		N = n; 
		cells = new Cell[N * N]; // cr�e un tableau de cellules
		
		// Boucle for qui va initialis� le tableau avec les objets Cell
		for(int i = 0; i < N * N; i++) {
			cells[i] = new Cell(); 
		}
		
		if(N > 0) {
			faireMurs(); // met � jours les informations sur le mur � l'int�rieur de chaque objet Cell
			detruitMurs(); // d�truit le mur jusqu'� la formation d'un labyrinthe
		}
		
	}

	/**
	 * Classe repr�sentant une cellule dans un labyrinthe
	 */
	public class Cell {
		int[] murs; // tableau repr�sentant les murs nord, sud, est et ouest

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
		// d�finit les murs nord, sud, est et ouest
		for(int i = 0; i < N * N; i++) {
			cells[i].murs[NORD] = i - N;
			cells[i].murs[SUD] = i + N; 
			cells[i].murs[EST] = i + 1; 
			cells[i].murs[OUEST] = i - 1; 
		}
		
		for(int i = 0; i < N; i++) {
			cells[i].murs[NORD] = -1; // situ� dans les cellules nord de la fronti�re, mur nord � -1
			cells[N * N - i - 1].murs[SUD] = -1; // situ� dans les cellules frontali�res sud, mur sud � -1
		}
		
		for(int i = 0; i < N * N; i += N) {
			cells[N * N - i - 1].murs[EST] = -1; // situ� dans les cellules est de la fronti�re, mur est � -1
			cells[i].murs[OUEST] = -1; // situ� dans les cellules frontali�res ouest, mur ouest � -1
		}
	}
	
	// D�truit les murs avec une version modifi�e de l'algorithme de Kruskal
	private void detruitMurs() {
		int NumElements = N * N; 
		
		Kruskal ensembleDisjoint= new Kruskal(NumElements); // cr�e un ensemble disjoint pour repr�senter les cellules
		
		for(int k = 0; k < N * N; k++) {
			ensembleDisjoint.find(k); // ajoute chaque cellule � un seul ensemble
		}
		
		// G�n�rateur al�atoire
		Random generateur = new Random(); 
		
		// Tant que tout les �l�ments de l'ensemble disjoint ne sont pas connect�s
		while(ensembleDisjoint.creeEnsemble() == false) {
			int cell1 = generateur.nextInt(N * N); // Choisis une cellule al�atoire
			int mur = generateur.nextInt(4); 
			
			int cell2 = cells[cell1].murs[mur]; // Choisis une seconde cellules al�atoire
			
			// S'il existe un mur entre ces deux cellules
			if (cell2 != -1 && cell2 != N * N) {
				// Si les cellules n'appartiennent pas au m�me ensemble
				if(ensembleDisjoint.find(cell1) != ensembleDisjoint.find(cell2)) {
					cells[cell1].murs[mur] = N * N; // D�truit les murs entre ces deux cellules. N*N ne repr�sentera aucun mur
					cells[N * N - 1].murs[EST] = N * N; // d�truit le mur en bas � droite pour cr�e la sortie
					
					if(mur == NORD || mur == EST) {
						cells[cell2].murs[mur + 1] = N * N; 
					}
					
					if(mur == SUD || mur == OUEST) {
						cells[cell2].murs[mur - 1] = N * N;
					}
					
					// Fait une union de l'ensemble de ces deux cellules, par laquelle un deplacement vient d'�tre cr�e
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
				
				// S'il existe un mur � l'est
				if(cells[compteur].murs[EST] != N * N) {
					g2.drawLine((i + 1) * CELL_WIDTH + MARGIN, j * CELL_WIDTH
							+ MARGIN, (i + 1) * CELL_WIDTH + MARGIN, (j + 1) * CELL_WIDTH
							+ MARGIN);
				}
				
				// S'il existe un mur � l'ouest
				if(cells[compteur].murs[OUEST] != N * N) {
					g2.drawLine(i * CELL_WIDTH + MARGIN, j * CELL_WIDTH + MARGIN, i
							* CELL_WIDTH + MARGIN, (j + 1) * CELL_WIDTH + MARGIN);
				}
			}
		}
		
		/*
		 * Position du h�ros
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
