/**
 * Classe gère l'exception NotEnnemiException déclenché quand le parametre rentré
 * n'est pas un ennemi
 */
package Exception;
public class NotEnnemiException extends Exception {
    
    /**
     * Constructeur de la classe NotEnnemiException permettant de cree une nouvelle instance de cette classe
     * @param message message d'erreur à afficher en cas d'une exception 
     */
    public NotEnnemiException(String message) {
        super(message);
    }
}
