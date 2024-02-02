/**
 * Classe quireprésente un coup à partir de ses coordonnées sur le plateau
 */
public class Move {
    private int x;
    private int y;
   


    /**
     * Constructeur de la classe Move 
     * @param x ligne du plateau
     * @param y colonne du plateau
     */
    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Methode qui permet d'avoir la ligne 
     * @return la ligne d'une case
     */
    public int getX(){
        return  this.x;
    }

    /**
     * Methode qui permet d'avoir la colonne 
     * @return la colonne d'une case
     */
    public int getY(){
        return this.y;
    }

    

    


}
