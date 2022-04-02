package opencv;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class OpencvDemo {
    static {
        System.load("D:\\IdeaProjects\\demoset\\Deeplearning4j\\src\\main\\resources\\lib\\opencv\\opencv_java454.dll");
    }

    public static void main(String[] args) {
        Mat src = Imgcodecs.imread("C:\\Users\\zcx\\Desktop\\mnist\\3.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        Mat dst = src.clone();

        turnImage(src, dst);
    }


    public static void turnImage2(Mat src, Mat dst) {
        Imgproc.resize(src, dst, new Size(28, 28));
        HighGui.imshow("28*28", dst);
        HighGui.waitKey(0);

        //二值化 反色
        int height = dst.rows();
        int width = dst.cols();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                double gray = dst.get(row, col)[0];
                dst.put(row, col, gray > 100 ? 0d : 255d);
            }
        }

        HighGui.imshow("threshold", dst);
        HighGui.waitKey(500);
    }

    public static void turnImage(Mat src, Mat dst) {
        HighGui.imshow("src", src);
        HighGui.waitKey(500);

        Imgproc.resize(src, dst, new Size(28, 28));
        HighGui.imshow("28*28", dst);
        HighGui.waitKey(500);
        //转为灰度
//        Imgproc.cvtColor(dst, dst, Imgproc.COLOR_RGB2GRAY);
//        HighGui.imshow("gray", dst);
//        HighGui.waitKey(0);

        //二值化 反色
        Imgproc.threshold(dst, dst, 100, 255, Imgproc.THRESH_BINARY_INV);
        HighGui.imshow("threshold", dst);
        HighGui.waitKey(500);
        //反色
//        Core.bitwise_not(dst, dst);
//        HighGui.imshow("not", dst);
//        HighGui.waitKey(0);

    }

    public static Mat getKernel1(){
        Mat kernel = new Mat(new Size(3, 3), CvType.CV_8U);
        kernel.put(0, 0, 1);kernel.put(0, 1, 1);kernel.put(0, 2, 1);
        kernel.put(1, 0, 1);kernel.put(1, 1, 1);kernel.put(1, 2, 1);
        kernel.put(2, 0, 1);kernel.put(2, 1, 1);kernel.put(2, 2, 1);
        return kernel;
    }
}
