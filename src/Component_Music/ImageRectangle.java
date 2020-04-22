/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author HCARACH
 */
public class ImageRectangle {

    Rectangle myRectangle;

    public ImageRectangle(double width, double height, Image image) {

        myRectangle = new Rectangle(width, height);
        myRectangle.setFill(Color.SNOW);
        myRectangle.setEffect(new DropShadow(+2d, 0d, +2d, Color.BLACK));

        Image newimage = null;
        try {
            newimage = this.setImage(image);
            myRectangle.setFill(new ImagePattern(newimage));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public ImageRectangle(double x, double y, double width, double height, Image image) {

        myRectangle = new Rectangle(width, height);
        myRectangle.setLayoutX(x);
        myRectangle.setLayoutY(y);
        myRectangle.setFill(Color.SNOW);
        myRectangle.setEffect(new DropShadow(+25d, 0d, +2d, Color.BLACK));

        Image newimage = null;
        try {
            newimage = this.setImage(image);
            myRectangle.setFill(new ImagePattern(newimage));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Rectangle getMyRectangle() {
        return myRectangle;
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
