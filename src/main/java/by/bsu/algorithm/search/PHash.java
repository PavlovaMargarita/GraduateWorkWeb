package by.bsu.algorithm.search;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;

public class PHash {

	private static int size = 32;
    private static int smallerSize = 8;
    private static double[] c;
	
	static{
		initCoefficients();
	}
	
    private static void initCoefficients() {
        c = new double[size];

        for (int i=1;i<size;i++) {
            c[i]=1;
        }
        c[0]=1/Math.sqrt(2.0);
    }

    public static int [] getHash(File imageFile) throws Exception {
        BufferedImage image = ImageIO.read(imageFile);
        return getHash(image);
    }

    public static int [] getHash(BufferedImage img) throws Exception {

                /* 1. Reduce size.
                 * Like Average Hash, pHash starts with a small image.
                 * However, the image is larger than 8x8; 32x32 is a good size.
                 * This is really done to simplify the DCT computation and not
                 * because it is needed to reduce the high frequencies.
                 */
        img = resize(img, size, size);

                /* 2. Reduce color.
                 * The image is reduced to a grayscale just to further simplify
                 * the number of computations.
                 */
        img = grayscale(img);

        double[][] vals = new double[size][size];

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                vals[x][y] = getBlue(img, x, y);
            }
        }

                /* 3. Compute the DCT.
                 * The DCT separates the image into a collection of frequencies
                 * and scalars. While JPEG uses an 8x8 DCT, this algorithm uses
                 * a 32x32 DCT.
                 */
        double[][] dctVals = applyDCT(vals);

                /* 4. Reduce the DCT.
                 * This is the magic step. While the DCT is 32x32, just keep the
                 * top-left 8x8. Those represent the lowest frequencies in the
                 * picture.
                 */
                /* 5. Compute the average value.
                 * Like the Average Hash, compute the mean DCT value (using only
                 * the 8x8 DCT low-frequency values and excluding the first term
                 * since the DC coefficient can be significantly different from
                 * the other values and will throw off the average).
                 */
        double total = 0;

        for (int x = 0; x < smallerSize; x++) {
            for (int y = 0; y < smallerSize; y++) {
                total += dctVals[x][y];
            }
        }
        total -= dctVals[0][0];

        double avg = total / (double) ((smallerSize * smallerSize) - 1);

                /* 6. Further reduce the DCT.
                 * This is the magic step. Set the 64 hash bits to 0 or 1
                 * depending on whether each of the 64 DCT values is above or
                 * below the average value. The result doesn't tell us the
                 * actual low frequencies; it just tells us the very-rough
                 * relative scale of the frequencies to the mean. The result
                 * will not vary as long as the overall structure of the image
                 * remains the same; this can survive gamma and color histogram
                 * adjustments without a problem.
                 */
        int [] hash = new int [smallerSize * smallerSize];

        for (int x = 0; x < smallerSize; x++) {
            for (int y = 0; y < smallerSize; y++) {
//                if (x != 0 && y != 0) {
                    hash[x * smallerSize + y] = (dctVals[x][y] > avg? 1 : 0);
//                }
            }
        }

        return hash;
    }

    private static BufferedImage resize(BufferedImage image, int width,    int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    private static ColorConvertOp colorConvert = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);

    private static BufferedImage grayscale(BufferedImage img) {
        colorConvert.filter(img, img);
        return img;
    }

    private static int getBlue(BufferedImage img, int x, int y) {
        return (img.getRGB(x, y)) & 0xff;
    }

    // DCT function stolen from http://stackoverflow.com/questions/4240490/problems-with-dct-and-idct-algorithm-in-java

    private static double[][] applyDCT(double[][] f) {
        int N = size;

        double[][] F = new double[N][N];
        for (int u=0;u<N;u++) {
            for (int v=0;v<N;v++) {
                double sum = 0.0;
                for (int i=0;i<N;i++) {
                    for (int j=0;j<N;j++) {
                        sum+=Math.cos(((2*i+1)/(2.0*N))*u*Math.PI)*Math.cos(((2*j+1)/(2.0*N))*v*Math.PI)*(f[i][j]);
                    }
                }
                sum*=((c[u]*c[v])/(Math.sqrt(2*N)));
                F[u][v] = sum;
            }
        }
        return F;
    }

    public static boolean isSameImage(int countOfDifferentPosition){
        if(countOfDifferentPosition <= 22){
            return true;
        }
        return false;
    }


}
