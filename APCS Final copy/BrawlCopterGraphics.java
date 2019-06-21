
// BrawlCopter with Keyboard Control
// Based of the example here: https://gist.github.com/hanksudo/5626452
// Comments by Dr. Sub | Roosevelt High School, AP Computer Science, Semester 2 2017

// DESCRIPTION: 
// This class demonstrates how to:
// 1. Create a game window (JFrame window with JPanel canvas inside)
// 2. Create the pacman player (Image object)
// 3. Hook up key press to move the player (KeyListener added to the JPanel)

 
// Needed for some graphics and key listeners
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL; // Needed to load local image files
import java.awt.Toolkit;
import java.util.ArrayList;
// Needed for the JPanel components
import javax.swing.ImageIcon; 
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.concurrent.TimeUnit;


// Class to draw a BrawlCopter graphic on a canvas.
// Key presses will move the BrawlCopter up or down
// onto the canvas.
public class BrawlCopterGraphics extends JFrame {
   public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
   public int height = (int) screenSize.getHeight();
   public int width = (int) screenSize.getWidth();

   // Constructor can be accessed from anywhere
   // These calls are necessary to simply get window and frame ready to go
   public BrawlCopterGraphics() {
      this.setTitle("BrawlCopter Starter!");
      this.setDefaultCloseOperation(EXIT_ON_CLOSE); // Good window hygene
      //this.setSize(new Dimension(height, width)); 
      //this.setPreferredSize(new Dimension(height,width));
      this.setExtendedState(JFrame.MAXIMIZED_BOTH);
      //this.setPreferredSize(new Dimension(height,width));
      //this.pack();
      this.getContentPane().setLayout(new BorderLayout()); 
      this.getContentPane().add(new GamePanel()); //See below on this method call
      this.setVisible(true);
   }

   //public static void main(String[] args) {
   //   new BrawlCopterGraphics(); // Create an instance of BrawlCopter! From here, go to the Constructor.
   //}

   public class GamePanel extends JPanel {
      private Image Background;

      private Image BrawlCopterImageBlue; // The BrawlCopter Image object that gets moved by key presses
      private Image BrawlCopterImageRed;
      private Image MissleLeft;
      private Image MissleRight;
      private Image Explosion;
      
      private int xCoordinateBlue = (width - 150 ); // The initial coordinate of the Blue Copter
      private int yCoordinateBlue = 0; // The starting coordinate of the Blue Copter
      
      private int xCoordinateRed = 0; // The initial coordinate of the Red Copter
      private int yCoordinateRed = 0; // The starting coordinate of the Red Copter

      
      boolean key_left, key_down, key_up;//Blue
      boolean key_w, key_s, key_d; //red   
      
      public BrawlCopter Red= new BrawlCopter(xCoordinateRed, yCoordinateRed);//Red constantly updated brawlcopter objest red
      public BrawlCopter Blue = new BrawlCopter(xCoordinateBlue, yCoordinateBlue);//Blue constatnly updated brawlcopter ibject
      public ArrayList<missle> AmmunitionRight= new ArrayList<missle>();//here stores the Red Missle locations and relitive positions
      public ArrayList<missle> AmmunitionLeft= new ArrayList<missle>();// here stores the Bue Missle Locations and damage variables
      public int shoot = 0;
      //public int hitBlue = 0;
      //public int hitRed = 0;
      
      
      URL urlForImage;
      
      ImageIcon usFlag;
   
      public GamePanel() {
         loadBackground("background.jpg");//found in folder
         Background = Background.getScaledInstance(width, height, Image.SCALE_DEFAULT);//sets to width of screen
         loadExplosion("Explosion.jpg");//found in folder
         loadBrawlCopterFromFileBlue("BrawlCopterBlue.jpg");//found in folder
         loadBrawlCopterFromFileRed("BrawlCopterRed.jpg"); // Calls the member method loadImage below
         loadMissleRight("MissileRight.jpg");//found in folder
         loadMissleLeft("MissileLeft.jpg");//found in folder
         this.setFocusable(true); // Events only fire for a component if it has focus, so this call is necessary
         addKeyListener(new GameInput()); // You are adding a KeyListener TO the GamePanel
      }
   
      // This is a custom method in which to load an image onto an icon
      // This method takes a string filename and tries to load it from the
      // same folder as this class.
      // all of the Load Methods are nessisary for th files to be initiated
      // They could be more efficient but I was lazy here
      public void loadExplosion(String filename) {
         urlForImage = getClass().getResource(filename);
         usFlag = new ImageIcon(urlForImage);
         Explosion = usFlag.getImage();
      }
      
      public void loadBackground(String filename) {
         urlForImage = getClass().getResource(filename);
         usFlag = new ImageIcon(urlForImage);
         Background = usFlag.getImage();
      }
      public void loadBrawlCopterFromFileBlue(String filename) {
         urlForImage = getClass().getResource(filename);
         usFlag = new ImageIcon(urlForImage);
         BrawlCopterImageBlue = usFlag.getImage();
      }
      
      public void loadBrawlCopterFromFileRed(String filename) {
         urlForImage = getClass().getResource(filename);
         usFlag = new ImageIcon(urlForImage);
         BrawlCopterImageRed = usFlag.getImage();
      }
      public void loadMissleLeft(String filename) {
         urlForImage = getClass().getResource(filename);
         usFlag = new ImageIcon(urlForImage);
         MissleLeft = usFlag.getImage();
      }
      public void loadMissleRight(String filename) {
         urlForImage = getClass().getResource(filename);
         usFlag = new ImageIcon(urlForImage);
         MissleRight = usFlag.getImage();
      }
     // Repaint the canvas and move BrawlCopter players based on which key press
     // Here is the main Run of the code, both provoking Object updates
     // and providing a repaint for every action and key press.
     // This method also uses the simplicity of the code to check for mathcing cooranants 
     // in order to provide the graphic representation for hit/misses
      public void paintComponent(Graphics g) {
         super.paintComponent(g); // Calls the parent class' method to repaint
         //setBackground(Color.white); // Set the background to white
                  
		   //super.paintComponent(g); 
         //Background = Background.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        
         g.drawImage(Background, 0,0, this); 
         g.drawString(Blue.Health(), xCoordinateBlue + 75,yCoordinateBlue);
         g.drawString(Red.Health(), xCoordinateRed + 75, yCoordinateRed);

         //g.drawImage();
         if(Blue.isAlive()){
            g.drawImage(BrawlCopterImageBlue, xCoordinateBlue, yCoordinateBlue, this);
         }
         else{
            g.drawImage(Explosion,xCoordinateBlue,yCoordinateBlue,this);
         }
         if(Red.isAlive()){
            g.drawImage(BrawlCopterImageRed, xCoordinateRed, yCoordinateRed, this);
         }
         else{
            g.drawImage(Explosion,xCoordinateRed,yCoordinateRed,this);
         }
         
         for(int i = 0; AmmunitionRight.size() > i; i++){   
            g.drawImage(MissleRight, AmmunitionRight.get(i).getX(), AmmunitionRight.get(i).getY(), this);
            AmmunitionRight.get(i).UpdateRight();
            
            //if(hitBlue != 0){
            //   g.drawImage(Explosion,yCoordinateBlue,xCoordinateBlue,this);
            //}
            if((AmmunitionRight.get(i).getY() < yCoordinateBlue + 60) && (AmmunitionRight.get(i).getY() > yCoordinateBlue - 10) 
            &&(AmmunitionRight.get(i).getX() < xCoordinateBlue + 20)&&(AmmunitionRight.get(i).getX() > xCoordinateBlue - 20 )){
               g.drawImage(Explosion,yCoordinateBlue,xCoordinateBlue,this);
               Blue.hitFor(10);
               AmmunitionRight.remove(i);
               //hitBlue++;
            }
            else if(AmmunitionRight.get(i).getX() > width){
                AmmunitionRight.remove(i);
            }
            
         }
         
         for(int i = 0; AmmunitionLeft.size() > i; i++){
            g.drawImage(MissleLeft, AmmunitionLeft.get(i).getX(), AmmunitionLeft.get(i).getY(), this);
            AmmunitionLeft.get(i).UpdateLeft();
            
            if((AmmunitionLeft.get(i).getY() < yCoordinateRed + 60 ) && (AmmunitionLeft.get(i).getY() > yCoordinateRed - 10)
             && (AmmunitionLeft.get(i).getX() > xCoordinateRed - 20) && (AmmunitionLeft.get(i).getX() < xCoordinateRed + 20)){
               g.drawImage(Explosion,xCoordinateRed,yCoordinateRed,this);
               Red.hitFor(10);
               AmmunitionLeft.remove(i);
            }
            else if(AmmunitionLeft.get(i).getX() < 0 ){
                AmmunitionLeft.remove(i);
            }
         }
         shoot++;
//Here is my costom Key listener metohod with spesific method calls for active meovement
         if (key_down) { 
            if(Blue.isAlive()){
               if (yCoordinateBlue < height - 200){
                  yCoordinateBlue++;
                  yCoordinateBlue++;
                  yCoordinateBlue++;
                  Blue.up(); 
               }
            }
         }
      
         if (key_up) {
            if(Blue.isAlive()){
               if ( -25 < yCoordinateBlue){
                  yCoordinateBlue--;
                  yCoordinateBlue--;
                  yCoordinateBlue--;
                  Blue.down(); 
               }
            }
         }
 
         if (key_left) { 
            if(Blue.isAlive()){
               if(shoot % 10 == 0){
                  if(AmmunitionLeft.size() < 3){
                     AmmunitionLeft.add(new missle(xCoordinateBlue,yCoordinateBlue + 50,true));
                  }
               }
            }
         }//xCoordinateBlue--; }
      
         if (key_s) { 
            if(Red.isAlive()){
               if (height - 200 > yCoordinateRed){
                  yCoordinateRed++; 
                  yCoordinateRed++;
                  yCoordinateRed++;
                  Red.up();
               }
            } 
         }
      
         if (key_w) { 
            if(Red.isAlive()){
               if (-25 < yCoordinateRed){
                  yCoordinateRed--; 
                  yCoordinateRed--;
                  yCoordinateRed--;
                  Red.down();
               }
            }         
         }
      
         if (key_d) { 
            if(Red.isAlive()){
               if(shoot % 10 == 0){
                  if(AmmunitionRight.size() < 3){
                     AmmunitionRight.add(new missle(xCoordinateRed,yCoordinateRed + 50,false));
                  }
               }
            }
         }//xCoordinateRed--; }
         
         for (int index = 0; index < 1000000; index++) {}
         /*
         if(!Blue.isAlive() || !Red.isAlive()){
            //TimeUnit.SECONDS.sleep(10);
            //this.setVisible(false);
            //g.setVisible(false);
            //this.dispose();
            //Game.playGame();
            //return;
         }
      */
         repaint();
      }
      
      
      // Private class to intantiate a KeyListener object
      // Called from the GamePanel contructor
         private class GameInput implements KeyListener {
         
         public void keyTyped(KeyEvent e) {}
      
         public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == e.VK_DOWN) key_down = false;
            if (e.getKeyCode() == e.VK_UP) key_up = false;
            if (e.getKeyCode() == e.VK_LEFT) key_left = false;
            if (e.getKeyCode() == e.VK_S) key_s = false;
            if (e.getKeyCode() == e.VK_W) key_w = false;
            if (e.getKeyCode() == e.VK_D) key_d = false;
         }
         
      
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == e.VK_DOWN) key_down = true;
            if (e.getKeyCode() == e.VK_UP) key_up = true;
            if (e.getKeyCode() == e.VK_LEFT) key_left = true;
            if (e.getKeyCode() == e.VK_S) key_s = true;
            if (e.getKeyCode() == e.VK_W) key_w = true;
            if (e.getKeyCode() == e.VK_D) key_d = true;

         }
         
      }
   }
}
