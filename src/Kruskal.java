/**
 * @author Nassim
 * @author Sibory
 * ALGORITHME DE KRUSKAL 
 */
public class Kruskal {
	public int[] s;
	/*
	 * Constructeur de l'objet Kruskal
	 * @param g le nombre initial d'ensembles disjoints
	 */
	public Kruskal(int g) {
		s = new int [g]; 
		for (int i = 0; i < s.length; i++) {
			s[i] = -1;
		}
	}

	/*
	 * Union de deux ensembles disjoints en utilisant l'heuristique de hauteur. 
	 * Pour simplifier, nous supposons que racine1 et racine2 sont distinctes et représentent des noms d'ensembles
	 * @param racine1 est la racine de l'ensemble 1. 
	 * @param racine2 est la racine de l'ensemble 2. 
	 */
	public void union(int racine1, int racine2) {
		if(s[racine2] < s[racine1]) { // racine2 est plus profond
			s[racine1] = racine2; // Faire de racine2 une nouvelle racine
		} else {
			if(s[racine1] == s[racine2]) {
				s[racine1]--; // Mettre à jour la hauteur si identique
			}
			s[racine2] = racine1; // Faire de racine1 une nouvelle racine
		}
	}
	

	/*
	 * Effectue une recherche avec compression de chemin. 
	 * Les contrôles ont été omis à nouveau pour plus de simplicité
	 * @param x l'élément recherché
	 * @return l'ensemble contenant x
	 */
	public int find(int x) {
		if(s[x] < 0) {
			return x; 
		} else {
			return s[x] = find(s[x]);
		}
		
	}
	
	/*
	 * Vérifie si touts les éléments sont tous dans le même ensemble
	 * @return true si tous les éléments sont dans le même ensemble, sinon renvoie false
	 */
	public boolean creeEnsemble() {
		int compteur = 0; 
		
		for(int i = 0; i < s.length; i++) {
			if(s[i] < 0) {
				compteur++; 
			}
			if(compteur > 1) {
				return false;
			}
		}
		return true;
	}

}
