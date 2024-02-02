
import Exception.*;

/**
 * Cette classe représente un arbre de jeu , les noeaud correspondent 
 * à une configuration du plateau
 */
public class Arbre {
    Node racine ;


    
    /**
     * Constructeur d'un arbre
     * @param b le plateau associé à a racine de l'arbre
     */
    public Arbre(Board b){
        this.racine = new Node(b, Type.MAX);
    }

    /**
     * methode qui retourne la racine d'un arbre
     * @return la racine d'un arbre
     */
    public Node getRacine(){
        return this.racine;
    }
    /**
     * methode qui affiche un arbre dans le terminal
     */
    public void afficher_arbo()
    {
        afficher_arbo_aux(this.racine, 0);
    }
    
    /**
     * fonction auxiliaire qui permet d'afficher un arbre de jeu dans le terminal
     * @param a noaud à afficher
     * @param n profondeur du noeud
     */
    public void afficher_arbo_aux(Node a, int n)
    {
        if(n==0){
            for(Node fils : a.getChildren()){
                afficher_arbo_aux(fils,n+1);
            }
        }
        else if ( a != null)
        {
            for ( int i = 0; i<n; i++)
            {
                if ( n!=0)
                {
                    System.out.print(" ");
                }   
            }
            System.out.println("|-  ("+a.getCoup().getX()+","+a.getCoup().getY()+")");     
                
            for(Node fils : a.getChildren()){
                afficher_arbo_aux(fils,n+1);
            }
            
        }
    }

    // fonction minmax appliquée sur un arbre qui le meilleur
    /**
     * methode qui permet de trouver le meilleur coup à jouer pour l'IA EXPERT
     * @param n noeud qu'on evalue et compare aux autres noeud de l'arbre
     * @param depth profondeur de l'arbre de recherche
     * @return
     */
    public static  int minmax(Node n, int depth){
    
        if(n.getChildren().isEmpty() || depth == 4){ //profondeur choisi à quatre car en dessous l'algorithme devient trop long
            return n.getScore();
        }
        else{
            if(n.getType() == Type.MAX){
                int max = Integer.MIN_VALUE;
                for(Node fils : n.getChildren()){
                    int val = minmax(fils, depth +1);
                    
                    if(val > max){
                        max = val;
                    }
                }
                return max;
            }
            else{
                int min = Integer.MAX_VALUE;
                for(Node fils : n.getChildren()){
                    int val = minmax(fils , depth +1);
                    
                    if(val < min){
                        min = val;
                    }
                }
                return min;
            }
        }
    }
    /**
     * methode qui utilise la fonction minmax et qui renvooi le meilleur coup à jouer pour L'IA
     * @param a Arbre de jeu
     * @return le meilleur coup à jouer
     */
        public static  Move meilleurCoup( Arbre a){
            int max = Integer.MAX_VALUE;
            Move coup = null;
            for(Node fils : a.racine.getChildren()){
                int val = minmax(fils,0);
                System.out.println( "  score : " + val);
                if(val < max){
                    max = val;
                    coup = fils.getCoup();
                } 
            }System.out.println("coup :  " + coup.getX() + coup.getY() );
            return coup;
        }


    
}
