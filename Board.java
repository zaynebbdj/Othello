

import Exception.*;


import java.awt.Graphics;

import java.awt.Color;
/**
 * Cette classe permet de manipuler la grille du jeu, constitué d'un nom , 
 * d'un pion d'un joueur et d'une taille
 */

public class Board {
    private  Player [] [] board;
    
    private Player player =Player.BLACK;  // joueur en cours
    
   
    
    /**
     * Ce constructeur produit une nouvelle grille sans prendre des arguments
     * en parametre
     */
    public Board() {
    }
    
    

    /**
     * Ce constructeur produit une nouvelle grille associant à chaque indice du
     * tableau
     * un element : EMPTY, BLACK, WHITE.
     * 
     * @param taille la taille de la grille
     * @param nom le nom de la grille
     */
    public Board(int taille) {
        this.board = new Player[taille][taille];
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                this.board[i][j] = Player.EMPTY;
            }
        }
        board[this.board.length / 2 -1][this.board.length / 2] = board[this.board.length/ 2 ][this.board.length / 2 -1] = Player.BLACK;
		board[this.board.length / 2 -1][this.board.length / 2 - 1] = board[this.board.length / 2][this.board.length / 2] = Player.WHITE;
        
        
    }
    
    /**
     * Ce constructeur par copie produit une nouvelle grille associant 
     * à chaque indice du tableau.
     * un element : EMPTY, BLACK, WHITE.
     * @param taille la taille de la grille
     * @return la grille avec une taille et un nom
     */
    public static Board copie(Board b){
        Board res = new Board(b.getLength());
        for(int i=0; i<b.getLength();i++){
            for(int j=0; j<b.getLength();j++){
                res.setPlayer(i,j,b.getPlayer(i,j));
            }
        }
        res.player = b.player;
        return res;
    }


    /**
     * Méthode qui permet de initialiser la grille (retour à son état de début)
     * @param taille la taille de la grille
     */
    public void init(int taille){
        this.board= new Player[taille] [taille];
        for(int i=0; i<taille;i++){
            for(int j =0 ; j<taille; j++){
                this.board[i][j]=Player.EMPTY;
            }
        }
        board[this.board.length/2 -1][this.board.length/2] = board[this.board.length/2][this.board.length/2 -1] = Player.BLACK;
		board[this.board.length/2 -1][this.board.length/2 -1] = board[this.board.length/2][this.board.length/2] = Player.WHITE;
        
    }
    /**
     * Méthode qui retourne le plateau courant de Pion
     * @return le plateau de Pion
     */
    public Player[][] getBoard(){
        return this.board;
    }
    
    
    

    /**
     * Méthode qui permet d'avoir la couleur d'une case de la grille courante
     * @param x les lignes
     * @param j les colonnes
     * @return la couleur d'une case 
     */
    public Player getPlayer(int x, int j) {
        return this.board[x][j];
    }
    
    /**
     * Méthode qui permet de modifier la couleur d'un pion à une case précise
     * @param i les lignes
     * @param j les colonnes
     * @param player le pion du joueur 
     */
    public void setPlayer(int i, int j, Player player) {
        this.board[i][j] = player;
    }
    
    
    
    /**
     * Méthode qui permet de modifier le joueur courant
     * @param p pion du joueur 
     */
    public void setPlayer(Player p) {
        this.player = p;
    }
    
    /**
     * Méthode qui permet d'avoir le joueur courant
     * @return le joueur courant 
     */
    public Player getPlayer() {
        return this.player;
    }
    
    /**
     * Méthode qui permet d'avoir la taille de la grille
     * 
     * @return la taille de la grille
     */
    public int getLength() {
        return this.board.length;
    }

    /**
     * methode qui retourne la couleur adverse du joueur courant
     * @return la couleur adverse du joueur courant
     */
    public Player getAdversaire() {
        if (this.player == Player.BLACK) {
            return Player.WHITE;
        } else {
            return Player.BLACK;
        }
    }
    
    /**
     * Méthode qui permet d'afficher le plateau dans le terminale
     */
    public void showterminal(){                   
        for (int i=0 ; i<this.board.length;i++){
            for(int j=0; j<this.board.length; j++){
                int color =this.board[i][j].ordinal()-1 ;
                if(this.coupPossible(i, j)){
                    System.out.printf("|\033[43m   \033[0m");
                }
                else if(color==-1) System.out.printf("|\033[47m   \033[0m");
                else if(color == 1)System.out.printf("|\033[40m   \033[0m");
                else System.out.printf("|\033[42m   \033[0m");
                
            }
            
            System.out.println();System.out.println("---------------------------------");
        }
    }

    //methode qui dessine un plateau sur un objet graphics
    /**
     * Méthode qui permet de de dessiner une grille sur un objet graphics
     * @param g un objet graohics
     */
    public void show(Graphics g){

        g.setColor((new Color(139, 69, 19).darker()));
        g.fillRect(25,25,50*(this.board.length+1),50*(this.board.length+1));

        g.setColor(Color.BLACK);
        g.drawRect(25,25,50*(this.board.length+1),50*(this.board.length+1));

        g.setColor(new Color(0,100,0));
        g.fillRect(50,50,50*(this.board.length),50*(this.board.length));
        
        for(int i=0; i<this.board.length ; i++){
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf((char) (65+i)), 50*i+75, 42);
            g.drawString(String.valueOf(i+1), 32, 50*i+80);
            g.drawString(String.valueOf((char) (65+i)), 50*i+75, 67+(this.board.length)*50);
            g.drawString(String.valueOf(i+1), 57 + (this.board.length)*50, 50*i+80);


            g.setColor(Color.BLACK);
            g.drawLine(i * 50+50, 50,i * 50+50, 50*(this.board.length+1));
            g.drawLine(50, i * 50+50, 50*(this.board.length+1), i * 50+50) ;
            for(int j=0 ; j<this.board.length ; j++){
                if(this.board[i][j]==Player.BLACK){
                    g.setColor(Color.BLACK);
                    g.fillOval(55+50*i, 55+50*j, 40, 40);
                }else if(this.board[i][j]==Player.WHITE){
                    g.setColor(Color.WHITE);
                    g.fillOval(55+50*i, 55+50*j, 40, 40);
                }
            }
            g.setColor(Color.BLACK);
        }g.drawLine(this.board.length * 50+50, 50,this.board.length * 50+50, this.board.length*50+50);
        g.drawLine(50,this.board.length * 50+50, this.board.length * 50+50,this.board.length * 50+50);
    }
    
    /**
     * Méthode qui permet de changer le joueur courant
     *
     */
    public void changePlayer() { 
        if (this.player == Player.BLACK) {
            this.player = Player.WHITE;
        } else if (this.player == Player.WHITE) {
            this.player = Player.BLACK;
        }
    }
    
    /**
     * Méthode qui permet de convertir un string en un pion
     * @param s chiane de caracter à convertir 
     * @exception NotAPlayer déclenché si vous passé une chaine de 
     * caractere qui ne correspond pas à un pion
     * @return le pion correspondant
     */
    public static Player Player_of_String(String s) throws NotAPlayer{
        if(s=="B") return  Player.BLACK;
        if(s=="W") return  Player.WHITE ;
        if(s=="E") return  Player.EMPTY;
        else{
            throw new NotAPlayer("ce n'est pas un pion");
        }
    }


    
    
    /**
     * Methode qui verifie si une case a cote de la case [x][y] est un ennemi
     * @param x les lignes du plateau
     * @param y les colonnes du plateau
     * @param d direction
     * @return vrai si un pion voisin est ennemi dans une certaine direction d
     */
    public boolean isEnnemi(int x, int y, Direction d) {


        int i,j=0;
        switch (d){
            case NORDOUEST :
				i = x-1;
				j = y-1;
				break;
			case NORD : 
                i = x-1;

                j = y;
                break;
            case NORDEST:
                i = x - 1;
                j = y + 1;
                break;
            case OUEST:
                i = x;
                j = y - 1;
                break;
            case EST:
                i = x;
                j = y + 1;
                break;
            case SUDOUEST:
                i = x + 1;
                j = y - 1;
                break;
            case SUD:
                i = x + 1;
                j = y;
                break;
            case SUDEST:
                i = x + 1;
                j = y + 1;
                break;
            default:
                i = 0;
                j = 0;
        }

        if(i<this.board.length && j<this.board.length && i>=0 && j>=0){
            if(  (this.board[i][j]==Player.WHITE && this.player==Player.BLACK )|| (this.board[i][j]==Player.BLACK && this.player==Player.WHITE)) return true;

        }

        return false;
    }
    
    /**
     * Méthode qui permet de verifier si une case est vide
     * @param i les lignes du plateau
     * @param j les colonnes du plateau
     * @return la case vide
     */
    public boolean isEmpty(int i, int j){  
        return this.board[i][j]== Player.EMPTY;
    }

    
    /**
     * Méthode qui permet de mettre a jour la grille du jeu avec le nouveau coup
     * @param x les lignes du plateau
     * @param y les colonnes du plateau
     */
    public void update(int x, int y ){
        if(this.coupPossible(x,y)){
            this.board[x][y]=this.player;
            for(Direction d : Direction.values()){
                if(this.coupPossible_aux(x,y,d)) changeLigne(x,y,d);
            }
            this.changePlayer();
        }
    }

    /**
     * Méthode qui actualise un plateau à partir d'un nouveau coup
     * @param m coup à jouer sur le plateau
     */
    public void update(Move m){
        this.update(m.getX(),m.getY());
    }

    
    /**
     * Méthode qui permet deactualise la grille suite a un coup possible selon une
     * seule direction
     * 
     * @param x         ligne du plateau
     * @param y         lcolonne du plateau
     * @param direction la direction
     * @exception ArrayIndexOutOfBoundsException se declanche si on est en dehors du plateau
     */
    // 
    public void changeLigne(int x, int y, Direction direction) {

        int incrX;
        int incrY;
        int ligne, colonne;
        Player p;

        switch (direction) {
            case NORDOUEST:
                incrX = -1;
                incrY = -1;
                break;
            case NORD:
                incrX = -1;
                incrY = 0;
                break;
            case NORDEST:
                incrX = -1;
                incrY = +1;
                break;
            case OUEST:
                incrX = 0;
                incrY = -1;
                break;
            case EST:
                incrX = 0;
                incrY = +1;
                break;
            case SUDOUEST:
                incrX = +1;
                incrY = -1;
                break;
            case SUD:
                incrX = +1;
                incrY = 0;
                break;
            case SUDEST:
                incrX = +1;
                incrY = +1;
                break;
            default:
                incrX = 0;
                incrY = 0;
        }
        ligne = x + incrX;
        colonne = y + incrY;

        try {

            p = board[ligne][colonne];
            while ((p != this.player) && (p != Player.EMPTY)) {

                this.board[ligne][colonne] = this.player;
                ligne += incrX;
                colonne += incrY;

                p = board[ligne][colonne];
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            //on se trouve en dehors du plateau

        }

    }
    
    /**
     * Methode qui verifie si une case est pleine
     * @return false si la case est vide
     * @return true si la case est pleine
     */
    public boolean isFull(){  
        for(int i=0; i<this.board.length ; i++){
            for(int j =0 ; j<this.board.length ; j++){
                if(this.board[i][j]==Player.EMPTY) return false;
            }
        }
        return true;
    }

    /**
     * Methode qui verifie si une partie est finie
     * 
     * @return true si il n'ya plus de coup possible 
     * @return true si la grille est pleine
     */
    public boolean isOver(){ // verifie si une partie est finie retourne vrai si il n'y a aucun coup possible pour les deux joueurs
                                            // ou si le plateau est plein
        
        boolean b = false;
        if(this.nbcoupPossible()==0 ){
            
            this.changePlayer();
            if(this.nbcoupPossible()==0) b=true;
            
        }                                    
        return (b || this.isFull());
    }


    /**
     * Methode qui compte le nombre de point d'un joueur , correspond au nombre de pion du joueur sur le plateau
     * 
     * @return true si il n'y a plus de coup possible
     * @return true si la grille est pleine
     */
    public int nbPlayer(Player p){ // compte le nombre de point du joueur p
        int nb=0;
        for(int i=0; i<this.board.length;i++){
            for(int j=0;j<this.board.length;j++){
                if(this.board[i][j]==p) nb++;
            }
        }return nb;
    }

    /**
     * methode qui retourne le nombre de pion placé dans les coins du joueur en paramètre
     */
    public int nbPlayerCoin(Player p){
        int res=0;
        if(this.board[0][0]==p ) res++;
        if(this.board[0][this.board.length-1]==p) res++;
        if(this.board[this.board.length-1][0]==p) res++;
        if(this.board[this.board.length-1][this.board.length-1]==p) res++;
        return res;
    }
   
    
    
    /**
     * Methode qui compte le nombre de coup possible du joueur courant
     * @return le nombre des coups possible du joueur courant
     */

    public int nbcoupPossible() {
        
        int nb=0;
        for(int i= 0 ; i <this.board.length ; i++){
            for(int j =0 ; j<this.board.length;j++){          
                if(coupPossible(i,j)){
                    nb=nb+1;;
                } 
            } 
        }
        return nb;
    }

    /**
     * Methode qui permet d'afficher les coups possible dans le terminal
     */
    public void showCoupPossible(String s){ 
        System.out.println("Voici les coups possibles pour : "+ s);
        for (int i=0 ; i<this.board.length;i++){
            for(int j=0; j<this.board.length; j++){
                if (coupPossible(i, j)) {
                    System.out.println((i) + "," + (j));
                }
            }
        }
    }
    /**
     * methode qui permet de traduire un Objet de la classe Player en francais, pour l'affichage de la fenetre
     */
    public String French_of_Player() throws NotAPlayer{
        if (this.player== Player.WHITE) return " BLANC ";
        if(this.player == Player.BLACK) return " NOIR ";
        else throw new NotAPlayer("ce n'est pas un joueur");
     }

    /**
     * Methode qui verifie un coup est possible pour le joueur courant
     * @param x ligne du coup à verifier
     * @param y colonne du coup à verifier
     * @return true si le coup est possible
     * @return false si le coups n'est pas possible
     */
    public boolean coupPossible(int x, int y) {

        if (isEmpty(x, y)) {
            for (Direction d : Direction.values()) {
                if (this.isEnnemi(x, y, d)) { //si il y a un ennemi a coté selon la direction d
                    if (this.coupPossible_aux(x, y, d)) { //on verifie que que le coup est possible selon cette direction
                        return true;
                    }
                }
            }
        }
        return false;
    }

	
    
    /**
    * fonction auxiliaire pour la veriication d'un coup possible selon une direction
    * @param x les lignes du plateau 
     * @param y les colonnes du plateau
     * @param d la direction 
     * @return true si il y a un coup possible
     * @return false si le coup n'est pas possible
     */
    public boolean coupPossible_aux2(int x, int y, Direction d) {
        if (this.board[x][y] == Player.EMPTY)
            return false;
        if (this.board[x][y] == this.player)
            return true;
        int incrX, incrY;
        switch (d) {


            case NORDOUEST:
                incrX = x - 1;
                incrY = y - 1;
                break;
            case NORD:
                incrX = x - 1;
                incrY = y;
                break;
            case NORDEST:
                incrX = x - 1;
                incrY = y + 1;
                break;
            case OUEST:
                incrX = x;
                incrY = y - 1;
                break;
            case EST:
                incrX = x;
                incrY = y + 1;
                break;
            case SUDOUEST:
                incrX = x + 1;
                incrY = y - 1;
                break;
            case SUD:
                incrX = x + 1;
                incrY = y;
                break;
            case SUDEST:
                incrX = x + 1;
                incrY = y + 1;
                break;
            default:
                incrX = 0;
                incrY = 0;
        }
        if (incrX >= 0 && incrX < this.board.length && incrY < this.board.length && incrY >= 0) {
            return coupPossible_aux2(incrX, incrY, d);
        }
        return false;
    }

 
    /**
     * fonction auxiliaire pour la veriication d'un coup possible selon une direction
     * @param x les lignes du plateau 
     * @param y les colonnes du plateau
     * @return true si le coup est possible
     * @return false si le coups n'est pas possible
     */
    public boolean coupPossible_aux(int x, int y, Direction d) {
        int incrX, incrY;
        switch (d) {
            case NORDOUEST:
                incrX = x - 2;
                incrY = y - 2;
                break;
            case NORD:
                incrX = x - 2;
                incrY = y;
                break;
            case NORDEST:
                incrX = x - 2;
                incrY = y + 2;
                break;
            case OUEST:
                incrX = x;
                incrY = y - 2;
                break;
            case EST:
                incrX = x;
                incrY = y + 2;
                break;
            case SUDOUEST:
                incrX = x + 2;
                incrY = y - 2;
                break;
            case SUD:
                incrX = x + 2;
                incrY = y;
                break;
            case SUDEST:
                incrX = x + 2;
                incrY = y + 2;
                break;
            default:
                incrX = 0;
                incrY = 0;
        }
        if (incrX >= 0 && incrX < this.board.length && incrY < this.board.length && incrY >= 0) {
            return coupPossible_aux2(incrX, incrY, d);
        }
        return false;

    }
    
    /**
     * Methode qui renvoie le joueur qui a le plus de point
     * @return le pion du joueur gagnant
     * @return le pion vide en cas d'égalité
     */
    public Player winner() { // renvoie le joueur qui a le plus de point aucun des deux si execo
        if (nbPlayer(Player.WHITE) > nbPlayer(Player.BLACK))
            return Player.WHITE;
        if (nbPlayer(Player.WHITE) < nbPlayer(Player.BLACK))
            return Player.BLACK;
        return Player.EMPTY;

    }

    
    
    

    

        
    

  
}
