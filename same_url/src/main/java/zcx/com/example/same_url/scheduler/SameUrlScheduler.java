package zcx.com.example.same_url.scheduler;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@EnableScheduling
@EnableAsync
@Component
public class SameUrlScheduler {

    public static Map<String, Date> requestMap = new ConcurrentHashMap<>();

    //请求最短间隔
    public static Long interval = 2000L;
    //定时清除超过2秒的请求
    @Async
    @Scheduled(cron = "0/2 * * * * ?")
    public void cleanMap() {
        //设置基准时间   获取 n毫秒 前的时间
        Date date = new Date();
        date.setTime(new Date().getTime() - interval);
        requestMap.forEach((key, value) -> {
            //如果该条记录时间在基准时间之前，清除该条
            if (value.before(date)) {
                requestMap.remove(key);
            }
        });
    }

    /**
     * 验证同一个url 此处使用 会话唯一标识+请求类型+uri 作为请求唯一标识
     */
    public static boolean sameUrl(HttpServletRequest request) {
        //此处根据旧系统获取会话唯一标识
        String token = request.getHeader("token");
        String method = request.getMethod();
        String url = request.getRequestURI();
        synchronized (SameUrlScheduler.class) {
            Date date = SameUrlScheduler.requestMap.get(token + method + url);
            Date now = new Date();
            if (date == null || now.getTime() - date.getTime() > SameUrlScheduler.interval) {//不是相同请求,或者是相同请求但间隔时间长
                SameUrlScheduler.requestMap.put(token + method + url, now);
                return false;
            } else {//是相同请求,且间隔较短
                return true;
            }
        }
    }
}
