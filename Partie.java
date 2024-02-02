/**
 * la classe permet de gerer chaque partie de jeu.
 */
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.*;


import Exception.*;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.awt.Font;
import java.util.ArrayList;

public class Partie extends JPanel {

    private String name;
    private gameState state;
    private Board board;
    private Ennemi ennemi;
    


    /**
     * Constructeur de la classe Partie permettant de créer une nouvelle instance de
     * la classe
     * 
     * @param taille taille de la grille
     * @param name   nom de la grille
     */
    public Partie(int taille, String name) {

        setPreferredSize(new Dimension((taille + 2) * 50+25, (2 + taille) * 50+25)); // le Panel prend la taille du plateau
        this.board = new Board(taille);
        this.state = gameState.INIT;
    }

    

    /**
     * Methode permettant de mettre à jour la grille
     * 
     * @param la grille
     */
    public void setBoard(Board b) {
        this.board = b;
    }

    /**
     * Methode permettant de mettre à jour l'ennemi
     * 
     * @param l'ennemi
     */
    public void setEnnemi(Ennemi e) {
        this.ennemi = e;
    }

    public void setName(String s){
        this.name = s;
    }

    /**
     * Methode permettant de mettre à jour l'etat du jeu
     * 
     * @param l'etat du jeu
     */
    public void setState(gameState g) {
        this.state = g;
    }

    /**
     * methode qui retourne le nom d'une partie
     */
    public String getName(){
        return this.name;
    }
    /**
     * Methode permettant d'avoir l'ennemi du jeu
     * 
     * @return l'ennemi en cours
     */
    public Ennemi getEnnemi() {
        return this.ennemi;
    }

    /**
     * Méthode permettant d'avoir l'etat du jeu
     * @return l'etat de du jeu
     */
    public gameState getState() {
        return this.state;
    }

    /**
     * Méthode d'avoir la grille du jeu
     * @return la grille du jeu
     */
    public Board getBoard() {
        return this.board;
    }
   /**
     * Methode qui permet d'avoir la taille de la grille
     * 
     * @return la taille de la grille en cours
     */
    public int getLength() {
        return this.board.getLength();
    }
    /**
     * réinitialisation de la grille
     * 
     * @param taille de la grille
     * @param name   nom de la grille
     */
    public void init(int taille, String name, gameState state) {
        this.board.init(taille);
        this.setName(name);
        this.board.setPlayer(Player.BLACK);
        this.setState(state);
    }

    /**
     * initialisation d'une partie deja commencé
     * @param b la grille du jeu
     */    
    public void init(Board b) {
        this.board = Board.copie(b);
        this.state = gameState.INPROGRESS;

    }

    /**
     * Methode qui dessine le tableau sur le Panel
     * @param g objet de Graphics
     */
    public void show(Graphics g) {
        this.board.show(g);
        try{ 
        if (this.state.name() == "INIT") {

            Font font = new Font("Arial", Font.BOLD, this.getLength()*2+2);
            g.setFont(font);
            g.setColor(Color.RED);
            g.drawString("Pour commencer une partie allez dans Fichier ^^", 25, (this.board.getLength() + 2) * 50);
        }
        if (this.state.name() == "INPROGRESS") {
            g.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 18));
            g.drawString("joueur courant: " + this.French_of_Player() ,25, (this.board.getLength() + 2) * 50  );
            g.drawString("nombre de pion blanc : "+ this.board.nbPlayer(Player.WHITE),25, (this.board.getLength() + 2) * 50 +20);            
            g.drawString("nombre de pion noir : " + this.board.nbPlayer(Player.BLACK),25,(this.board.getLength() + 2) * 50+40);
        }
    }catch (NotAPlayer n){
        System.out.println("ce n'est pas un joueur");
    }
    }

    /**
     * methode qui permet de traduire un Objet de la classe Player en francais, pour l'affichage de la fenetre
     */
    public String French_of_Player() throws NotAPlayer{
        return this.board.French_of_Player();
     }

    /**
     * methode qui actualise un plateau à partir d'un coup joué
     * 
     * @param x ligne du coup à jouer
     * @param y colonne du coup à jouer
     */
    public void update(int x, int y) {
        this.board.update(x, y);
    }

    /**
     * methode qui permet d'actualiser le plateau à partir d'un objet Mouv passé en paramètre
     * @param move coordonnés de la case à jouer
     */
    public void update(Move move){
        this.board.update(move);
    }

    /**
     * methode qui fait jouer l'IA DEBUTANTE
     * on stocke les coups possibles dans une arraylist puis on en joue un au hasard
     */
    public void getAmouv() {
         
        ArrayList<Integer> possibletab = new ArrayList<Integer>();
        for (int i = 0; i < this.board.getLength(); i++) {
            for (int j = 0; j < this.board.getLength(); j++) {
                if (this.board.coupPossible(i, j)) { // on place les coups possible dans un tableau 
                    possibletab.add(i);
                    possibletab.add(j);
                    
                }
            }
        }
        
        double coup = Math.random() * (this.board.nbcoupPossible()*2);//on choisi un  coup  au hasard dans le tableau de coups possible
        if (((int) coup )% 2 != 0) {
            coup = ((int) coup )-1;
        }
        this.board.showCoupPossible("L'IA DEBUTANTE");;
        System.out.println("coup joué par l'IA DEBUTANTE: " + possibletab.get((int) coup) + "   " + (possibletab.get(((int) coup) + 1))  );
        System.out.println();
        this.update(possibletab.get((int) coup), possibletab.get(((int) coup) + 1));

    }

    /**
     * methode qui fait jouer l'IA EXPERT
     */
    public void getAmouvExpert(){
        Arbre arbre = new Arbre(this.getBoard()) ; /// on cree l'arbre à partir de l'etat du plateau
        Node.addChildren(arbre.getRacine(),arbre.getRacine().getBoard().isOver(),0);  // on lui ajoute tous ses enfants jusqu'une certaine profondeur
        
        Move coup = Arbre.meilleurCoup(arbre);
        this.board.showCoupPossible(" L'IA EXPERT");
        this.update(coup);
        
        System.out.println(" L'IA EXPERT a joué ce coup  :  "   + coup.getX() + "  " + coup.getY() );
        System.out.println();

    }

    /**
     * methode appelée automatiquement afin de redessiner le plateau de la partie
     * 
     * @param g Objet de Graphics
     */
    @Override 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.show(g);
        for (int i = 0; i < this.board.getLength(); i++) {
            for (int j = 0; j < this.board.getLength(); j++) {
                if (this.state == gameState.INPROGRESS) {
                    if (this.board.coupPossible(i, j)) {
                        if(this.board.getPlayer()==Player.BLACK){  
                        g.setColor(Color.YELLOW);}
                        else g.setColor(Color.BLUE);
                        g.drawOval(69 + 50 * i, 69 + 50 * j, 15, 15);
                        g.fillOval(69 + 50 * i, 69 + 50 * j, 15, 15);
                    }
                }

            }
        }

    }

    /**
     * Mehtode qui vérifie si une partie est finie ; 
     * aucune case vide sur le plateau ou plus de coup possible pour le joueur courant
     * @return true si une partie est fini false sinon
     */
    public boolean isOver() {
        return this.board.isOver();
    }

    /**
     * Methode qui retourne le gagnant d'une partie
     * @return le pion du joueur gagnant
     */
    public Player winner() {
        return this.board.winner();
    }
    

    /**
     * Methode qui enregistre les information d'une partie dans un fichier texte  
     * @exception IOException générée si la partie spécifiée du fichier est
     * verrouillée.
     */

    public void save() {

        try {
            
            FileWriter input = new FileWriter(this.name + ".txt");
            input.write(this.board.getPlayer().ordinal() + "\n" + this.name + "\n" + this.board.getLength() //ordinal du joueur courant , nomm de la partie , taille du plateau
                    + "\n" + this.ennemi.ordinal() + "\n"); // joueur en cours et nom de la partie en cour
            for (int i = 0; i < this.board.getLength(); i++) {
                for (int j = 0; j < this.board.getLength(); j++) {
                    input.write(this.board.getBoard()[i][j].ordinal() + "\n"); // ordinal de chaque pion du tableau
                                                                               // [i][j] du plateau
                }
            }

            input.close();
        } catch (IOException e) { //exception lancée lors d'un probleme d'entrée ou sortie 
            e.printStackTrace(); //trace de la pile d'éxecution
        }
    }

    /**
     * methode qui charge une partie sauvegardée
     * 
     * @param Chaine de caractere qui va permettre de recharger une partie d'apres
     *               son nom
     * @exception FileNotFoundException declenché si partie n'existe pas
     * @exception IOException           declenché impossible de lire le fichier
     * @exception NotEnnemiException    declenché si un objet n'est pas un ennemi
     * @exception NotAPlayer declenché si un objet n'est pas un pion
     */
    public void restore(String nom){
        this.setState(gameState.INPROGRESS);
        try {
            String namee = nom + ".txt";
            FileReader fread = new FileReader(namee);
            BufferedReader bread = new BufferedReader(fread);

            int joueur = Integer.valueOf(bread.readLine()); // joueur en cours (pion)
            
            String name = bread.readLine(); // nom de la partie
            
            int length = Integer.valueOf(bread.readLine()); // taille du plateau
            
            int ennemi = Integer.valueOf(bread.readLine()); // ennemi de la partie a charger
            
            this.setEnnemi(Ennemi_of_int(ennemi)); // on set l'ennemi de la partie
            Board b = new Board(length);
            this.setName(name);
            b.setPlayer(Player_of_int(joueur)); // on set le joueur courant du plateau
            
            for (int i = 0; i < b.getLength(); i++) {
                for (int j = 0; j < b.getLength(); j++) {
                    b.setPlayer(i, j, Player_of_int(Integer.valueOf(bread.readLine()))); // on remplie le tableau
                }
            }
            b.showterminal();
            this.setBoard(b);
            repaint();
            bread.close();

        } catch (FileNotFoundException e) {
            System.err.println("cette partie n'existe pas");
            this.setState(gameState.INIT);
            JOptionPane.showMessageDialog(null, "Aucune partie avec un tel nom");
        } catch (IOException e) {
            System.err.println("impossible de lire le fichier");
        } catch (NotEnnemiException n) {
            System.err.println("ceci n'est pas un ennemi");
        } catch (NotAPlayer n) {
            System.err.println("ceci n'est pas un pion");
        }

    }

    /**
     * Methode qui permet de convertir un entier en un objet Ennemi
     * @param t entier à convertir
     * @return l'ennemi
     * @throws NotEnnemiException déclenché si l'entire t ne correspond à aucun objet de la classe Ennemi
     */
    public Ennemi Ennemi_of_int(int t) throws NotEnnemiException {
        if (t == Ennemi.HUMAN.ordinal()) {
            return Ennemi.HUMAN;
        } else if (t == Ennemi.IABEGINNER.ordinal()) {
            return Ennemi.IABEGINNER;
        } else if (t == Ennemi.IAEXPERT.ordinal()) {
            return Ennemi.IAEXPERT;
        } else {
            

            throw new NotEnnemiException("ce n'est pas un ennemi");

        }
    }


    /**
     * Methode qui permet de convertir un entier en un objet Player
     * @param t l'entier à convertir
     * @return un pion
     * @throws NotAPlayer declenché si l'entier en paramètre ne correspond à aucun attribut de l'enumeration Player
     */
    public Player Player_of_int(int t) throws NotAPlayer {
        if (t == Player.BLACK.ordinal()) {
            return Player.BLACK;
        } else if (t == Player.EMPTY.ordinal()) {
             return Player.EMPTY;
        } else if (t == Player.WHITE.ordinal()) {
            return Player.WHITE;
        } else {
            

            throw new NotAPlayer("ce n'est pas un ennemi");

        }
    }


    /**
     * Methode qui initialise une nouvelle partie
     * 
     * @exception NumberFormatException declenché si on entre pas un nombre pour la taille du plateau
     */
    public void setNewGame() {

        String result = (String) JOptionPane.showInputDialog(
                null, // Pas de composant parent
                "Contre qui voulez-vous jouer :", // Message à afficher
                "Sélection du joueur 2", // Titre de la fenêtre
                JOptionPane.QUESTION_MESSAGE, // Type de message
                null, // Icône (null pour aucune icône)
                new String[] { "HUMAN", "IADEBUTANT", "IAEXPERT" }, // Valeurs possibles
                "HUMAN" // Valeur par défaut
        );

        try{
            // Traitement du résultat
        if (result != null) {
            // L'utilisateur a sélectionné une couleur
            switch (result) {
                case "HUMAN":
                    this.setEnnemi(Ennemi.HUMAN);
                    break;
                case "IADEBUTANT":
                    this.setEnnemi(Ennemi.IABEGINNER);
                    break;
                case "IAEXPERT":
                    this.setEnnemi(Ennemi.IAEXPERT);
                    break;
            }
            String name = (String) JOptionPane.showInputDialog("saisissez un nom pour la partie");
            while(name == ""){
                name = (String) JOptionPane.showInputDialog("saisissez un nom avec au moins un caractère ");
            }
            int taille = Integer.parseInt(JOptionPane.showInputDialog("saisissez une taille pour le plateau"));
            
            while ( taille <4 || taille > 12 ) {
                taille = Integer.parseInt(JOptionPane.showInputDialog("saisissez une taille possible pour le plateau"));
            }

            this.init(taille, name, gameState.INPROGRESS); // on initialise le plateau
            this.repaint();
            Listener click = new Listener(this);
            this.addMouseListener(click);
        } else {
            System.out.println("Vous venez d'annuler la selection ");
        }
        }catch( NumberFormatException n ){
            System.out.println("ce n'est pas un nombre");
        }
        
    }

}
