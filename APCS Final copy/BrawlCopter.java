// This is the helecopter object class that gives a
// graphic the proprties and the ablity to have values 
// ascociated with thir locations and key strokes

public class BrawlCopter {
   private int x,y;
   private int Health = 100;

   public BrawlCopter(int x, int y){
      this.x = x;
      this.y = y;
   }
   public String Health(){
      if(Health < 0){
         return "" + 0;
      }
   
      String Healthx = "" + Health;
      return Healthx;
   }
   public void up(){
      this.y = y + 3;
   }
   
   public void down(){
      this.y = y - 3;
   }
   
   public void hitFor(int damage){
      Health = Health - damage;
   }
   
   public boolean isAlive(){
      boolean alive = true;
      if (Health < 1){
         alive = false;
      }
      
      return alive;
   } 
   
}