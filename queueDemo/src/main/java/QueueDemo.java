import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class QueueDemo {
    //请求队列 存放待处理的消息
    public static Queue<String> inQueue = new ArrayBlockingQueue<String>(100);
    //结果队列 存放处理完成后的结果
    public static Queue<String> backQueue = new ArrayBlockingQueue<String>(100);

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep((int) (Math.random() * 200));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                in("" + i);
            }
        }).start();

        new Thread(() -> {
            while (true) {
                String s = inQueue.poll();
                if (s != null) {
                    execute(s);
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                String s = backQueue.poll();
                if (s != null) {
                    callBack(s);
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
    public static void execute(String s) {
        try {
            //添加延迟 模拟处理耗时
            Thread.sleep((int) (Math.random() * 500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //将处理结果发送至结果队列
        backQueue.offer(s);
    }
}
