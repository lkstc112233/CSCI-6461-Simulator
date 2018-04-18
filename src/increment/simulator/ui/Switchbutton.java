package increment.simulator.ui;


import javafx.scene.image.*;
import javafx.scene.image.Image;

import java.awt.*;

public class Switchbutton extends Frame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 319478816115107399L;
	Image image1 = new Image("file:res/close2.png");
    Image image2 = new Image("file:res/open.png");
    public ImageView imageView =new ImageView(image1);
    public boolean IsOpen = false;


    public Switchbutton(int Height, int Width) {

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
