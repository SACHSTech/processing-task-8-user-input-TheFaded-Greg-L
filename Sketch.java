import processing.core.PApplet;
import processing.core.PImage;

/**
 * A program, "Sketch.java", that draws a tank to test user input in regards to processing
 * @author G. Lui
 */
public class Sketch extends PApplet {
	
  // global variables
  PImage TankGun;
  PImage TankBody;

  float tankX = 50;
  float tankY = 50;

  boolean tankLeft = false;
  boolean tankUp = false;
  boolean tankDown = false;
  boolean tankRight = false;

  float bodyRotation = PI;

  float gunRotation;
  float gunDisplacementX;
  float gunDisplacementY;
  int gunDisplacemntValue = 5;
  
  float bulletX = 0;
  float bulletY = 0;
  float initialBulletX = tankX;
  float initialBulletY = tankY;
  boolean bulletTriggered = false;
  float bulletRotation;
	
  /**
   * Called once at the beginning of execution, put your size all in this method
   */
  public void settings() {
	// put your size call here
    size(1600, 900);
  }

  /** 
   * Called once at the beginning of execution.  Add initial set up
   * values here i.e background, stroke, fill etc.
   */
  public void setup() {
    background(76, 148, 58);
    imageMode(CENTER);
    TankGun = loadImage("turret_01_mk1.gif");
    TankBody = loadImage("body_tracks.png");

  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
    // refreshes background
    background(76, 148, 58);

    //draws images
    drawBody(0, 0, 64, 64);
    drawGun(0, 0, 64, 64);
    if (bulletTriggered){
      drawBullet();
    }
  }

  /**
   * 
   * detects when key is released to move tank
   * @author G. Lui
   */
  public void keyPressed(){

    if (key == 'a' || key == LEFT){

      tankLeft = true;
    }

    if (key == 'd' || key == RIGHT){

      tankRight = true;
    }

    if (key == 'w' || key == UP){

      tankUp = true;
    }

    if (key == 's' || key == DOWN){

      tankDown = true;
    }
  }

  /**
   * 
   * detects when key is released to move tank
   * @author G. Lui
   */
  public void keyReleased(){

    if (key == 'a' || key == LEFT){

      tankLeft = false;
    }

    if (key == 'd' || key == RIGHT){

      tankRight = false;
    }

    if (key == 'w' || key == UP){

      tankUp = false;
    }

    if (key == 's' || key == DOWN){

      tankDown = false;
    }
  }
  
  /**
   * 
   * detects when mouse is pressed to draw bullet
   * @author G. Lui
   */
  public void mousePressed(){

    if (mousePressed){

      bulletRotation = atan2(mouseY-tankY, mouseX-tankX);
      bulletX = 0;
      bulletY = 0;
      initialBulletX = tankX;
      initialBulletY = tankY;
    }

    bulletTriggered = true;
  }
  
  /**
   * 
   * draws the bullet on screen when mouse is pressed
   * @author G. Lui
   */
  public void drawBullet(){

    pushMatrix();
    translate(initialBulletX, initialBulletY);
    rotate(bulletRotation);
    pushMatrix();
    translate(bulletX, bulletY);
    fill(191, 39, 25);
    ellipse(0, 0, 5, 5);
    popMatrix();
    popMatrix();

    bulletX += 5;
  }

  /**
   * 
   * @param bodyX the x position that effects translations applied to the body of the tank
   * @param bodyY the y position that effects translations applied to the body of the tank
   * @param bodyWidth the width value of the body
   * @param bodyHeight the height value of the body
   * @author G. Lui
   */
  public void drawBody(float bodyX, float bodyY, float bodyWidth, float bodyHeight){

    pushMatrix();
    translate(tankX, tankY);
    rotate(bodyRotation);
    image(TankBody, bodyX, bodyY, bodyWidth, bodyHeight);
    popMatrix();
    
    // left
    if (tankLeft){

      bodyRotation = PI/2;
      tankX -= 5;
      gunDisplacementX = gunDisplacemntValue;
      gunDisplacementY = 0;
    }

    // right
    if (tankRight){

      bodyRotation = 3 * PI/2;
      tankX += 5;
      gunDisplacementX = -gunDisplacemntValue;
      gunDisplacementY = 0;
    }

    // up
    if (tankUp){

      bodyRotation = PI;
      tankY -= 5;
      gunDisplacementY = gunDisplacemntValue;
      gunDisplacementX = 0;
    }

    // down
    if (tankDown){

      bodyRotation = 0;
      tankY += 5;
      gunDisplacementY = -gunDisplacemntValue;
      gunDisplacementX = 0;
    }

    // top left
    if (tankUp && tankLeft){

      bodyRotation = 3 * PI/4;
      gunDisplacementX = gunDisplacemntValue;
      gunDisplacementY = gunDisplacemntValue;
    }

    // top right
    if (tankUp && tankRight){

      bodyRotation = 5 * PI/4;
      gunDisplacementX = -gunDisplacemntValue;
      gunDisplacementY = gunDisplacemntValue;
    }

    // bottom left
    if (tankDown && tankLeft){

      bodyRotation = PI/4;
      gunDisplacementX = gunDisplacemntValue;
      gunDisplacementY = -gunDisplacemntValue;
    }

    // bottom right
    if (tankDown && tankRight){

      bodyRotation = -PI/4;
      gunDisplacementX = -gunDisplacemntValue;
      gunDisplacementY = -gunDisplacemntValue;
    }

    // reset displacement
    if (!tankLeft && !tankRight && !tankDown && !tankUp){

      gunDisplacementX = 0;
      gunDisplacementY = 0;
    }
  }

  /**
   * 
   * @param gunX the x position that effects translations applied to the gun of the tank
   * @param gunY the y position that effects translations applied to the gun of the tank
   * @param gunWidth the width value of the gun
   * @param gunHeight the height value of the gun
   * @author G. Lui
   */
  public void drawGun(float gunX, float gunY, float gunWidth, float gunHeight){

    pushMatrix();
    translate(tankX + gunDisplacementX, tankY + gunDisplacementY);
    rotate(gunRotation);
    image(TankGun, gunX, gunY, gunWidth, gunHeight);
    popMatrix();

    gunRotation = atan2(mouseY-tankY, mouseX-tankX) + PI/2;
  }
}