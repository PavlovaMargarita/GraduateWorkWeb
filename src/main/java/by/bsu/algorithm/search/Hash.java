package by.bsu.algorithm.search;



import by.bsu.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

//        http://habrahabr.ru/post/120562/
public class Hash {

	public static int [] getHash(BufferedImage image) throws IOException, InterruptedException {
		//-----resize image
        BufferedImage resizedImage = Utils.resizeImage(image, 8, 8);

        //-----convert to gray image
        resizedImage = Utils.convertToGray(resizedImage);

        //-----get medium pixels value
        PixelGrabber pg = new PixelGrabber(resizedImage, 0, 0, -1, -1, false);
        pg.grabPixels();
        int[] pixels = (int[])pg.getPixels();

        double medium = getMedium(pixels);

        //-----generate hash
        int [] hashArray = getHashArray(pixels, medium);

        return hashArray;
	}
	
    public static int [] getHash(File imageFile) throws IOException, InterruptedException {
        BufferedImage image = ImageIO.read(imageFile);
		return getHash(image);
    }

    public static boolean isSameImage(int countOfDifferentPosition){
        if(countOfDifferentPosition <= 10){
            return true;
        }
        return false;
    }

    private static int [] getHashArray(int[] baseArray, double medium) {
        int [] result = new int[baseArray.length];
        for(int i = 0; i < baseArray.length; i++){
            if((double)baseArray[i] > medium){
                result[i] = 1;
            } else {
                result[i] = 0;
            }
        }
        return result;
    }

    private static double getMedium(int [] array){
        int sum = 0;
        for(int i : array){
            sum += i;
        }
        return (double)sum / array.length;
    }

}
