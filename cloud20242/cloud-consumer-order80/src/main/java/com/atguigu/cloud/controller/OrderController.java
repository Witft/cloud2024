package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class OrderController {

//    private static final String PAYMENT_SERVER_URL = "http://localhost:8001";
    private static final String PAYMENT_SERVER_URL = "http://cloud-payment-service";

    @Resource
    private RestTemplate restTemplate;

    /**
     * 新增订单
     * @param payDTO
     * @return
     */
    @GetMapping("/consumer/pay/add")
    public ResultData addOrder(PayDTO payDTO) {
        return restTemplate.postForObject(PAYMENT_SERVER_URL + "/pay/add", payDTO, ResultData.class);
    }

    /**
     * 查询订单
     * @param id
     * @return
     */
    @GetMapping("/consumer/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id) {
        log.info("开始查询订单处理");
        return restTemplate.getForObject(PAYMENT_SERVER_URL + "/pay/get/" + id, ResultData.class, id);
    }

    /**
     * 删除订单
     */
    @GetMapping("/consumer/pay/del/{id}")
    public ResultData deletePay(@PathVariable("id") Integer id) {
        restTemplate.delete(PAYMENT_SERVER_URL + "/pay/del/" + id, id);
        return ResultData.success("删除成功");
    }

    /**
     * 修改订单
     */
    @PutMapping("/consumer/pay/update")
    public ResultData updatePay(@RequestBody PayDTO payDTO) {
        restTemplate.put(PAYMENT_SERVER_URL + "/pay/update", payDTO);
        return ResultData.success("修改成功");
    }

    @GetMapping("/consumer/pay/get/info")
    public String getInfoByConsul() {
        return restTemplate.getForObject(PAYMENT_SERVER_URL + "/pay/get/consul/config", String.class);
    }
}
