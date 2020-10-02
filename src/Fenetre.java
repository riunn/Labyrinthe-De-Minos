import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * @author Nassim
 * @author Sibory
 * Cette classe permet de créer la fenêtre à l'aide de JFrame
 */
public class Fenetre extends JFrame{
	Labyrinthe labyrinthe = new Labyrinthe(20); // Construit l'objet labyrinthe
	/**
	 * @param args
	 * Constructeur de la classe Fenetre
	 */
	public Fenetre(){
	      
		// Définit un titre pour notre fenêtre
		setTitle("Le labyrinthe de Minos");
		// Définit sa taille : 1000 pixels de large et 1000 pixels de haut
		setSize(800, 800); 
		// Nous demandons maintenant à notre objet de se positionner au centre
		setLocationRelativeTo(null);
		// Termine le processus lorsqu'on clique sur la croix rouge
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LabyrinthePanel panel = new LabyrinthePanel(labyrinthe); // Construit le panel pour contenir le labyrinthe
		JScrollPane scrollPane = new JScrollPane(panel);
		add(scrollPane, BorderLayout.CENTER);
		// Et enfin, la rendre visible
		setVisible(true);
		
	}
	
}