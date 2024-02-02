import java.awt.event.*;
import javax.swing.JOptionPane;



/**
 * La classe qui s'intéresse au traitement d'un événement de souris implémente
 * l'interface MouseListener constitué d'une partie de type partie.
 */

public class Listener implements MouseListener {

    private Partie partie ;

    /**
     * Constructeur permet de produire une nouvelle instance de la classe Listener
     * @param p la partie 
     */
    public Listener(Partie p) {
        this.partie = p;
    }
    
    
/**
 * Methode permettant de detecter le clique de la souris du joueur 
 * @param e l'evenement de la souris
 * @exception ArrayIndexOutOfBoundsException declenché si l'on clique en dehors des zones de coup possible durant une partie en cours
 */
    @Override
	public void mouseClicked(MouseEvent e) {
        try{
        if(this.partie.getState()==gameState.INPROGRESS){
            System.out.println("x : "+(e.getX())/50+" y : "+(e.getY())/50);
                
            
            if(this.partie.getEnnemi().name()=="HUMAN"){
                    partie.repaint();

                    if  ( (e.getX())/50-1>0 || (e.getX())/50-1<this.partie.getBoard().getLength() || (e.getY())/50-1>0  || (e.getY())/50-1 <this.partie.getBoard().getLength()){
                        
                        this.partie.update((e.getX())/50-1,(e.getY())/50-1);
                        this.partie.repaint();
                    }
                    
                    
                   
            }else if(this.partie.getEnnemi().name()== "IABEGINNER"){
                
                if  ( (e.getX())/50-1>0 || (e.getX())/50-1<this.partie.getBoard().getLength() || (e.getY())/50-1>0  || (e.getY())/50-1 <this.partie.getBoard().getLength()){
                    if(this.partie.getBoard().getPlayer()== Player.BLACK){
                        this.partie.update((e.getX())/50-1,(e.getY())/50-1);
                        
                        this.partie.repaint();
                    }if(this.partie.getBoard().getPlayer() == Player.WHITE){
                        if(!(this.partie.isOver())){
                            this.partie.getAmouv();
                        }
                    }
                    
                }
                
            }else if(this.partie.getEnnemi().name()== "IAEXPERT"){
                
                if  ( (e.getX())/50-1>0 || (e.getX())/50-1<this.partie.getBoard().getLength() || (e.getY())/50-1>0  || (e.getY())/50-1 <this.partie.getBoard().getLength()){
                    if(this.partie.getBoard().getPlayer()== Player.BLACK){
                        this.partie.update((e.getX())/50-1,(e.getY())/50-1);
                        this.partie.repaint();
                    }if(this.partie.getBoard().getPlayer() == Player.WHITE){
                        
                        if(!(this.partie.isOver())){
                            this.partie.getAmouvExpert();
                        }
                    }
                    
                }
                

            }
                    
                    

            if(this.partie.isOver() ){ 
                if(this.partie.winner().name() == "EMPTY"){
                    JOptionPane.showMessageDialog(null, "IL Y A EGALITE ");
                }else {
                    JOptionPane.showMessageDialog(null, "Le joueur "+this.partie.winner().name()+" a gagné");
                }

                this.partie.init(this.partie.getBoard().getLength(),"init", gameState.INIT);

                this.partie.repaint();
                        
            } 
        }
            }catch ( ArrayIndexOutOfBoundsException o){
                System.out.println("Vous clicker en dehors du plateau");
            }  
        
        
        
                    
                    
    }
        
        
		            
                    
    
    /**
     * Methode appelé lorsqu'un bouton de la souris a été pressé sur un composant.
     */
	@Override
	public void mousePressed(MouseEvent e) {}

    /**
     * Methode appelé lorsqu'un bouton de la souris a été relâché sur un composant.
     */
	@Override
	public void mouseReleased(MouseEvent e) {}

    /**
     * Methode appelé lorsque la souris entre dans un composant.
     */
	@Override
	public void mouseEntered(MouseEvent e) {}

    /**
     * Methode appelé lorsque la souris quitte un composant.
     */
	@Override
	public void mouseExited(MouseEvent e) {}

                

}
