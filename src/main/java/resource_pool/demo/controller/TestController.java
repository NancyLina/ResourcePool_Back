package resource_pool.demo.controller;

import com.zaxxer.hikari.util.ConcurrentBag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.*;

@RestController
@RequestMapping
public class TestController {
    @GetMapping("")
    public Object Find_by_id() {
//        ThreadLocal
//        CountDownLatch
//        ReentrantReadWriteLock
//        ReentrantLock lock=new ReentrantLock();
//        ThreadPoolExecutor
        return "hhh";
    }
}
