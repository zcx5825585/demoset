import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        long starttime = System.currentTimeMillis();
        List<FutureTask<String>> taskList = new ArrayList<>();
        //获取疫苗信息
        for (int i = 0; i < 10; i++) {
            FutureTask<String> futuretask = new FutureTask<>(() -> {
                Thread.sleep(5000);
                //todo 向对应的接种台发送打开抽屉的消息
                //todo 阻塞监听是否有消息返回
                return "1";
            });
            new Thread(futuretask).start();
            taskList.add(futuretask);
        }
        for (FutureTask<String> futureTask : taskList) {
            String result = futureTask.get();
        }
        long endtime = System.currentTimeMillis();
        System.out.println("用时：" + String.valueOf(endtime - starttime));
    }

    //这是我们要执行的算法
    public static int algorithm(int input, int input2) {
        return input + input2;
    }

}