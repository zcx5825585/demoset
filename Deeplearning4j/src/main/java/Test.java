import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.*;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main2(String[] args) throws IOException {
        int nChannels = 1;      //black & white picture, 3 if color image
        int outputNum = 10;     //number of classification
        int batchSize = 64;     //mini batch size for sgd
        int nEpochs = 10;       //total rounds of training
        int iterations = 1;     //number of iteration in each traning round
        int seed = 123;         //random seed for initialize weights
        DataSetIterator mnistTrain = null;
        DataSetIterator mnistTest = null;

        mnistTrain = new MnistDataSetIterator(batchSize, true, 12345);
        mnistTest = new MnistDataSetIterator(batchSize, false, 12345);


        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .l2(0.0005)
                .weightInit(WeightInit.XAVIER)
                .updater(new Adam(1e-3))
                .list()
                .layer(new ConvolutionLayer.Builder(5, 5)
                        .stride(1, 1)
                        .nOut(20)
                        .activation(Activation.IDENTITY)
                        .build())
                .layer(new SubsamplingLayer.Builder(PoolingType.MAX)
                        .kernelSize(2, 2)
                        .stride(2, 2)
                        .build())
                .layer(new ConvolutionLayer.Builder(5, 5)
                        .stride(1, 1)
                        .nOut(50)
                        .activation(Activation.IDENTITY)
                        .build())
                .layer(new SubsamplingLayer.Builder(PoolingType.MAX)
                        .kernelSize(2, 2)
                        .stride(2, 2)
                        .build())
                .layer(new DenseLayer.Builder().activation(Activation.RELU)
                        .nOut(500).build())
                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nOut(outputNum)
                        .activation(Activation.SOFTMAX)
                        .build())
                .setInputType(InputType.convolutionalFlat(28, 28, 1))
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        model.setListeners(new ScoreIterationListener(1));

        for (int i = 0; i < nEpochs; ++i) {
            model.fit(mnistTrain);
            System.out.println("*** Completed epoch " + i + "***");

            System.out.println("Evaluate model....");
            Evaluation eval = new Evaluation(outputNum);
            while (mnistTest.hasNext()) {
                DataSet ds = mnistTest.next();
                INDArray output = model.output(ds.getFeatures(), false);
                eval.eval(ds.getLabels(), output);
            }
            System.out.println(eval.stats());
            mnistTest.reset();
        }
        ModelSerializer.writeModel(model, new File("C:\\Users\\zcx\\Desktop\\mnist\\model"), true);
    }

    public static void main(String[] args) throws IOException {
        MultiLayerNetwork model2 = ModelSerializer.restoreMultiLayerNetwork(new File("C:\\Users\\zcx\\Desktop\\mnist\\model"));
        NativeImageLoader imageLoader = new NativeImageLoader();
        INDArray image = imageLoader.asMatrix(new File("C:\\Users\\zcx\\Desktop\\mnist\\num.jpg"));
        INDArray imageNum = model2.output(image);
        System.out.println(imageNum);

    }
}
