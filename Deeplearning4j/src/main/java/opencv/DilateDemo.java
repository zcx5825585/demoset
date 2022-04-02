package opencv;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class DilateDemo {
    static {
        System.load("D:\\IdeaProjects\\demoset\\Deeplearning4j\\src\\main\\resources\\lib\\opencv\\opencv_java454.dll");
    }

    public static void main(String[] args) {
        Mat src = Imgcodecs.imread("C:\\Users\\zcx\\Desktop\\mnist\\lenna.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        HighGui.imshow("lena", src);
        HighGui.waitKey(500);
        Imgcodecs.imwrite("C:\\Users\\zcx\\Desktop\\mnist\\gray.jpg",src);

        Mat dst = src.clone();

        Imgproc.dilate(src,dst,getKernel1());
        HighGui.imshow("filter1", dst);
        HighGui.waitKey(500);
        Imgcodecs.imwrite("C:\\Users\\zcx\\Desktop\\mnist\\dilate.jpg",dst);
        Imgproc.erode(dst,dst,getKernel1());
        HighGui.imshow("filter12", dst);
        HighGui.waitKey(500);
        Imgcodecs.imwrite("C:\\Users\\zcx\\Desktop\\mnist\\close.jpg",dst);

        Imgproc.erode(src,dst,getKernel1());
        HighGui.imshow("filter2", dst);
        HighGui.waitKey(500);
        Imgcodecs.imwrite("C:\\Users\\zcx\\Desktop\\mnist\\erode.jpg",dst);
        Imgproc.dilate(dst,dst,getKernel1());
        HighGui.imshow("filter22", dst);
        HighGui.waitKey(500);
        Imgcodecs.imwrite("C:\\Users\\zcx\\Desktop\\mnist\\open.jpg",dst);

        System.exit(0);
    }

    public static Mat getKernel1(){
        Mat kernel = new Mat(new Size(3, 3), CvType.CV_8U);
        kernel.put(0, 0, 1);kernel.put(0, 1, 1);kernel.put(0, 2, 1);
        kernel.put(1, 0, 1);kernel.put(1, 1, 1);kernel.put(1, 2, 1);
        kernel.put(2, 0, 1);kernel.put(2, 1, 1);kernel.put(2, 2, 1);
        return kernel;
    }

}
