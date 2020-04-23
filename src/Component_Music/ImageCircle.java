/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author HCARACH
 */
public class ImageCircle {

    Circle myCircle;

    public ImageCircle(double radius, Image image) {
        myCircle = new Circle(radius);
        myCircle.setFill(Color.SNOW);
        myCircle.setEffect(new DropShadow(BlurType.GAUSSIAN ,Color.WHITE,+25d, +5d, 0d, +2d));

        Image newimage = null;
        try {
            newimage = this.setImage(image);
            myCircle.setFill(new ImagePattern(newimage));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public ImageCircle(double centerx, double centery, double radius, Image image) {
        myCircle = new Circle(centerx, centery, radius);
        myCircle.setFill(Color.SNOW);
        myCircle.setEffect(new DropShadow(+25d, 0d, +2d, Color.BLACK));

//        if(image.getHeight() > image.getWidth())
//            image.
        Image newimage = null;
        try {
            newimage = this.setImage(image);
            myCircle.setFill(new ImagePattern(newimage));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public Circle getMyCircle() {
        return myCircle;
    }

    public Image setImage(Image image) {

        int width, height;
        int[][] data;

        width = ((int) image.getWidth());
        height = ((int) image.getHeight());
        data = new int[width][height];

        PixelReader r = image.getPixelReader();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                data[i][j] = r.getArgb(i, j);
            }
        }

        int size;

        if (width <= height) {
            size = width;
        } else {
            size = height;
        }

        WritableImage img = new WritableImage(size, size);
//
//        System.out.println("width " + width + " height " + height + " size " + size);
//        System.out.println((width - size) / 2 + " " + (width - ((width - size) / 2)));
//        System.out.println((height - size) / 2 + " " + (height - ((height - size) / 2)));

        PixelWriter w = img.getPixelWriter();
        for (int i = ((width - size) / 2) + ((width - size) % 2), row = 0; i < width - ((width - size) / 2) - ((width - size) % 2); i++, row++) {
            for (int j = ((height - size) / 2) + ((height - size) % 2), column = 0; j < height - ((height - size) / 2) - ((height - size) % 2); j++, column++) {
                w.setArgb(row, column, data[i][j]);
            }
        }
        return img;
    }

}
