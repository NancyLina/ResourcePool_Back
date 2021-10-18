package resource_pool.demo.controller;


import org.springframework.stereotype.Component;

@Component     //1.主要用于标记配置类，兼备Component的效果。
public class Scheduled {
    //3.添加定时任务
//    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
   @org.springframework.scheduling.annotation.Scheduled(cron = "0/5 * * * * ?")
    public void cron() {
        System.out.println("执行静态定时任务时间: ");
    }
}