import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.math.MathContext;

/**
 * Created by pierre on 2016-11-23.
 */
public class PointOp {

    private BufferedImage image;

    public PointOp(BufferedImage image) {

        this.image = image;

    }

    public BufferedImage newImage(double contrast, double brightness) {



        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage outputImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster inraster = this.image.getRaster();
        WritableRaster outRaster = outputImg.getRaster();

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                for(int r = 0; r < 3; r++) {
                    int value = inraster.getSample(x, y, r);

                    value = (int)((contrast * value) + brightness);

                    if(value > 255)
                        value = 255;
                    else if(value < 0)
                        value = 0;

                    outRaster.setSample(x, y, r, value);
                }
            }
        }

        return outputImg;

    }





    public static void main(String[] args) {

        try{

            String file = args[0];
            System.out.println(args[0]);
            double contrast = Double.parseDouble(args[1]);
            double brightness = Double.parseDouble(args[2]);

            BufferedImage img = ImageIO.read(new File(file));
            PointOp point = new PointOp(img);
            ImageIO.write(point.newImage(contrast, brightness), "PNG", new File("pointOp_" + file + ".png"));

        }catch(Exception e) {
            System.out.println("Shit went south!");
            e.printStackTrace();
        }

    }

}
