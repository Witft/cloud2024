package com.atguigu.cloud.controller;

import cn.hutool.core.util.IdUtil;
import com.atguigu.cloud.resp.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class PayCircuitController {
//    @RequestMapping("/pay/circuit/{id}")
//    public String myCircuit(@PathVariable("id") Integer id) {
//        if (id < 0) {
//            throw new RuntimeException("circuit id is less than 0");
//        } else if (id > 100) {
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            } catch (InterruptedException e) {
//                log.error("circuit id is more than 100, value is {}", e.getMessage(), e);
//                return "Get circuit id failed.";
//            }
//            return "Hello, circuit! input ID = " + id + ", random uuid = " + IdUtil.simpleUUID();
//        } else {
//            return "Hello, circuit! input ID = " + id + ", random uuid = " + IdUtil.simpleUUID();
//        }
//    }

    // Resilience4j bulkhead的例子
    @GetMapping("/pay/bulkhead/{id}")
    public String myBulkhead(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("circuit id is less than 0");
        } else if (id > 100) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                log.error("circuit id is more than 100, value is {}", e.getMessage(), e);
                return "Get circuit id failed.";
            }
            return "Hello, bulkhead! input ID = " + id + ", random uuid = " + IdUtil.simpleUUID();
        } else {
            return "Hello, bulkhead! input ID = " + id + ", random uuid = " + IdUtil.simpleUUID();
        }
    }
}
