import opencv.OpencvDemo;
import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.io.IOException;

public class UseModel {

    static {
        System.load("D:\\IdeaProjects\\demoset\\Deeplearning4j\\src\\main\\resources\\lib\\opencv\\opencv_java454.dll");
    }

    public static void main(String[] args) throws IOException {
        Mat src = Imgcodecs.imread("C:\\Users\\zcx\\Desktop\\mnist\\3.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        Mat dst = src.clone();
        OpencvDemo.turnImage(src, dst);

        MultiLayerNetwork model2 = ModelSerializer.restoreMultiLayerNetwork(new File("C:\\Users\\zcx\\Desktop\\mnist\\model"));
        NativeImageLoader imageLoader = new NativeImageLoader();
        INDArray image = imageLoader.asMatrix(dst);

        INDArray imageNum = model2.output(image);
        System.out.println(imageNum);
        double max = 0d;
        int num = -1;
        for (int i = 0; i < 10; i++) {
            double d = imageNum.getDouble(0, i);
            if (d > max) {
                max = d;
                num = i;
            }
        }
        System.out.println("num is " + num);

    }
}
