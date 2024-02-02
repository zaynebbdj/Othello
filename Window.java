
/**
 * Classe gerant la fenetre du jeu 
 */

import javax.swing.*;


public class Window extends JFrame{
    private Partie gamePanel ;
    
    /**
     * Methode permettant d'actualiser le panel
     * @param p partie du jeu
     */
    public void setPanel(Partie p){
        this.gamePanel = p;
    }

    /**
     * Constructeur de la classe Window
     */
    public Window(){
    	
        setTitle("OTHELLO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        
    /**
     * Création du menu
     */ 
    JMenuBar menuBar = new JMenuBar();

    /**
     * Création des éléments de menu
     */ 
    JMenu fileMenu = new JMenu("Fichier");
    JMenuItem newGame = new JMenuItem("Nouvelle partie");
    JMenuItem saveItem = new JMenuItem("sauvegarder");
    JMenuItem chargeItem = new JMenuItem("charger une partie");
    JMenuItem exitItem = new JMenuItem("Quitter");

    JMenu infos = new JMenu("partie sauvegardée");

    /**
     * Ajout des éléments de menu au menu
    */ 
    fileMenu.add(newGame);
    fileMenu.add(saveItem);
    fileMenu.add(chargeItem);
    fileMenu.addSeparator(); // barre de séparation
    fileMenu.add(exitItem);

    
    /**
    * Ajout du menu à la barre de menu
    */
    
    menuBar.add(fileMenu);
    
    menuBar.add(infos);

    /** Ajout de la barre de menu à la fenêtre */ 
    setJMenuBar(menuBar);
    
   
    /** Ajout du Panel à la fenêtre  */ 
    this.gamePanel = new Partie(8,"first");
   
    this.add(gamePanel);

    
    /**
     * ajout des actions aux menu
     */
    saveItem.addActionListener( event -> {  //sauvegarde la partie en cours
        if(this.gamePanel.getState()==gameState.INPROGRESS){
            JMenuItem partie = new JMenuItem(gamePanel.getName());
            
            this.gamePanel.save();     //sauvegarde de la partie

            
            infos.add(partie);    // on l'ajoute dans la liste "partie sauvegardée" pour quelle soit directement visible par l'utilisateur
            this.startwindow();       //on reinitialise la fenetre à son "etat d'ouverture"
            this.setNewSize(); 
            
        }
        
        
    });
    newGame.addActionListener( event -> {
        /** on commence une nouvelle partie */
          
        gamePanel.setNewGame();
        this.setNewSize();  // on actualise la taille de la fenêtre selon la taille du plateau de la partie 
        

    });

    chargeItem.addActionListener(event -> {
        String name = (String)JOptionPane.showInputDialog("saisissez le nom de la partie à charger");
        gamePanel.restore(name);
        this.setNewSize(); // on actualise la taille de la fenêtre selon la taille du plateau de la partie chargée
    });
    
    exitItem.addActionListener(event -> {
        this.dispose();
    });
    
    
        
    
    /** Affichage de la fenêtre  */ 
    this.pack();
    SwingUtilities.updateComponentTreeUI(this);
    setLocationRelativeTo(null);
    setVisible(true);
    }

    /**
     * Methode permettant de reinitialiser la fenetre à son etat d'ouverture
     */
    public void startwindow(){
        gamePanel.init(8,"init",gameState.INIT);    
        this.setNewSize();  
    }
    /**
     * Methode permettant de changer la taille de la fenetre en fonction de la taille du plateau
     */
    public void setNewSize(){
        if(gamePanel.getState()== gameState.INIT){
            this.setSize(50*(this.gamePanel.getLength()+3),50*(this.gamePanel.getLength()+4));
        }else {
            this.setSize(50*(this.gamePanel.getLength()+2),50*(this.gamePanel.getLength()+5));

        }
        
    }

    
}
