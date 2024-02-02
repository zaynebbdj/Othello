
/**
 * Classe qui s'occupe de l'arbre du jeu
 * Composé de : Le plateau , une liste chainé des possibilité , un score et les deux types des Nodes
 */
import java.util.LinkedList;


public class Node {
     private Board plateau;
     private Move coup;
     private  LinkedList<Node> children; // liste des configuration apres un coup
     private final int score ; //nombre de pion noir sur le plateau
     private Type type; //type du Node (min ou max)

   



    /**
     * Constructeur de la classe Node permet de cree une nouvelle instance de Node
     * @param b le plateu 
     * @param p le type de Node 
     */
     public Node(Board b,Type p){
        this.plateau = b;
        this.type = p;
        this.score = this.eval();
        this.children = new LinkedList<Node>();
        
     }
     /**
     * Constructeur de la classe Node permet de cree une nouvelle instance de Node
     * @param b le plateu 
     * @param p le type de Node 
     * @param m le coup associé à la configuration du plateau
     */
    public Node(Board b,Type p,Move m){
      this.plateau = b;
      this.type = p;
      this.score = this.eval();
      this.coup = m;
      this.children = new LinkedList<Node>();
      
   }


   /**
    * methode qui retourne le type d'un noeud 
    * @return le type d'un noeud
    */
     public Type getType(){
        return this.type;
     }

     /**
      * methode qui retourne la liste de fils du noeud
      * @return liste de fils du noeud
      */
     public LinkedList<Node> getChildren(){
        return this.children;
     }

     /**
      * methode qui retourne la valeur d'un noeud calculée par la fonction d'evaluation
      */
     public int getScore(){
        return this.score;
     }

     /**
      * methode qui retourne le coup lié au plateau d'un noeud
      * @return
      */
     public Move getCoup(){
        return this.coup;
     }
     /**
      * methode qui retourne le plateau d'un noeud
      */
     public Board getBoard(){
      return this.plateau;
     }
    /**
     * Methode qui evalue selon trois critère la valeur d'un noeud 
     * @return le nombre des pions noirs
     */ 
     public int eval(){  
      int eval =  30*this.plateau.nbPlayer(Player.BLACK)    //nombre de pion noir sur le plateau 
                 +50*this.plateau.nbPlayerCoin(Player.BLACK);// nombre de pion noir dans les coins du plateau
      if(this.plateau.getPlayer()==Player.BLACK){
         eval+= 10*this.plateau.nbcoupPossible();          //nombre de coup possible du joueur
      }   
      return eval;
     }


     /**
      * methode qui retourne un type de la classe TYPE MIN ou MAX
      * @return un objet de la classe TYPE
      */
     public Type typeAdverse(){
      if(this.type == Type.MIN) return Type.MAX;
      return Type.MIN;
     }

     /**
      * methode qui ajoute un fils à un noeud
      * @param n noeud fils à ajouter
      */
     public void addChild(Node n ){
      this.children.add(n);
     }

     /**
      * methode qui permet de creer les noeud d'un arbre à partir de son noeud Racine
      * @param node noeud auquel on ajoute ses enfants (les coups possibles)
      * @param b  condition d'arret (lorsque le noeud n'a pas de fils)
      * @param depth creation de l'arbre jusqu'une profondeur maximale
      */
     public static void addChildren(Node node, boolean b, int depth) {
         if (b || depth==5) {
            return; // Arrêt de la récursion si la hauteur souhaitée est atteinte ou s'il n'y a pas de coup possible
         }
         for(int i = 0 ; i<node.plateau.getLength();i++){
            for(int j =0; j<node.plateau.getLength() ; j++){
                if(node.plateau.coupPossible(i,j)){
                  
                    Board copie = Board.copie(node.plateau); 
                    copie.update(i,j);
                    
                    Board copieaux = Board.copie(copie);
                    node.addChild(new Node(copieaux,node.typeAdverse()));
                    node.children.get(node.children.size()-1).coup = new Move(i,j);

                }
            }
        }
         for (Node child : node.children) {// Appel récursif de la fonction pour chaque enfant
            addChildren(child, child.plateau.isOver(),depth+1);
         }
      }

    

}
