package com.imooc.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
public class AsyncController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //异步处理业务逻辑
    @RequestMapping("/order")
    public Callable<String> order(){
        logger.info("主线程开始");
        Callable<String> result = new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("子线程开始");
                Thread.sleep(1000);
                logger.info("子线程结束");
                return "success";
            }
        };
        logger.info("主线程返回");
        return result;
    }


    // 同步处理业务逻辑
    /*@RequestMapping("/order")
    public String order() throws InterruptedException {
        logger.info("主线程开始");
        Thread.sleep(1000);
        logger.info("主线程返回");
        return "success";
    }*/
}
