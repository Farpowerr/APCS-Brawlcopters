
// Here is my missle constructor class that 
// serves to keep trakc of missle locations 
// and update their own locations per repaint
public class missle{

   private int x,y;
   private int damage = 10;
   private boolean left;

   public missle(int x, int y, boolean left){
      this.x = x;
      this.y = y;
      this.left = left;
   }
   
   public int damage(){
      return damage;
   }
   public int getX(){
      return this.x;
   }
   
   public int getY(){
      return this.y;
   }
   public void UpdateLeft(){
      this.x = this.x - 10;
   }
   public void UpdateRight(){
      this.x = this.x + 10;
   }

}