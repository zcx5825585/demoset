import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class QueueDemo {
    //请求队列 存放待处理的消息
    public static Queue<String> inQueue = new ArrayBlockingQueue<String>(100);
    //结果队列 存放处理完成后的结果
    public static Queue<String> backQueue = new ArrayBlockingQueue<String>(100);

    public static void main(String[] args) {
        new Thread(() -> {//消费者发送消息的线程
            //间隔调用in函数 模拟发送消息
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep((int) (Math.random() * 200));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String message = "" + i;
                in(message);
            }
        }).start();

        new Thread(() -> {//服务提供者接收待处理消息并返回结果的线程
            while (true) {
                String message = inQueue.poll();
                if (message != null) {
                    String result = execute(message);
                    //将处理结果发送至结果队列
                    backQueue.offer(result);
                }
            }
        }).start();

        new Thread(() -> {//消费者接受返回结果的线程
            while (true) {
                String result = backQueue.poll();
                if (result != null) {
                    callBack(result);
                }
            }
        }).start();
    }

    //发送消息
    public static void in(String s) {
        System.out.println("in " + s);
        //将消息发送至请求队列
        inQueue.offer(s);
    }

    //接收返回的结果
    public static void callBack(String s) {
        System.out.println("back " + s);
    }

    //处理消息
    public static String execute(String s) {
        try {
            //添加延迟 模拟处理耗时
            Thread.sleep((int) (Math.random() * 500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return s;

    }
}
