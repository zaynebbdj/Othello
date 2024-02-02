package Exception;
/*
 * Classe manipulant l'exception NotAPawn
 * Hérite de Exception
 */

public class NotAPlayer extends Exception {
    
    /**
     * Constructeur de la classe NotAPawn 
     * @param s message d'erreur à afficher
     */
    public NotAPlayer(String s){
        super(s);
    }
}
