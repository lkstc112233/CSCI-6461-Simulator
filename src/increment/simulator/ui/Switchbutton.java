package increment.simulator.ui;


import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.Node;
import javafx.scene.image.*;
import javafx.scene.image.Image;

import java.awt.*;

public class Switchbutton extends Frame {

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
