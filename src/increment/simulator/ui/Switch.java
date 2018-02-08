
package increment.simulator.ui;

import javafx.scene.image.*;
import javafx.scene.image.Image;
/** 
 * This Button class is intended to create a better UI . 
 * @author Bowen Shi
 *
 */
public class Switch {

  Image image1 = new Image("increment/simulator/close2.png");
  Image image2 = new Image("increment/simulator/open.png");
 public ImageView imageView =new ImageView(image1);
 public boolean IsOpen = false;


    public Switch(int Height, int Width) {

        imageView.setFitHeight(Height);
        imageView.setFitWidth(Width);

         imageView.setOnMousePressed(event -> {

             if(!IsOpen)
        {
            imageView.setImage(image2);
            IsOpen=true;
        }
        else {
            imageView.setImage(image1);
             IsOpen=false;
        }

         });

    }

    public Image getImage(){

        return imageView.getImage();
    }

}




