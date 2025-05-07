package com.atguigu.cloud.controller;

import com.atguigu.cloud.apis.PayFeignApi;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class OrderCircuitController {
    @Resource
    private PayFeignApi payFeignApi;

    @GetMapping("/feign/pay/circuit/{id}")
    @CircuitBreaker(name = "cloud-payment-service", fallbackMethod = "myCircuitFallback")
    public String myCircuitBreaker(@PathVariable("id") Integer id) {
        return payFeignApi.myCircuit(id);
    }

    // 服务降级后的兜底处理方法
    public String myCircuitFallback(Integer id, Throwable t) {
        return "系统繁忙，请稍后再试";
    }

//    @GetMapping("/feign/pay/bulkhead/{id}")
//    @Bulkhead(name = "cloud-payment-service", fallbackMethod = "myBulkheadFallback", type = Bulkhead.Type.SEMAPHORE)
//    public String myBulkhead(@PathVariable("id") Integer id) {
//        log.info("Bulkhead请求被调用，id：{}", id);
//        return payFeignApi.myBulkhead(id);
//    }
//
//    public String myBulkheadFallback(Integer id, Throwable t) {
//        return "myBulkheadFallback，隔板超出最大数量限制，系统繁忙，请稍后再试-----/(ㄒoㄒ)/~~";
//    }

    /**
     * 舱壁（隔离），threadPool
     * @param id ID
     * @return 一个字符串，包含生成的UUID
     */
    @GetMapping("/feign/pay/bulkhead/{id}")
    @Bulkhead(name = "cloud-payment-service", fallbackMethod = "myBulkheadPoolFallback", type = Bulkhead.Type.THREADPOOL)
    public CompletableFuture<String> myBulkheadThreadPool(@PathVariable("id") Integer id) {
        log.info("Bulkhead请求被调用，id：{}，\t线程：{}", id, Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        return CompletableFuture.supplyAsync(() -> payFeignApi.myBulkhead(id) + " BulkheadThreadPool");
    }

    public CompletableFuture<String> myBulkheadPoolFallback(Integer id, Throwable t) {
        return CompletableFuture.supplyAsync(() -> "myBulkheadPoolFallback，隔板超出最大数量限制，系统繁忙，请稍后再试-----/(ㄒoㄒ)/~~");
    }
}
