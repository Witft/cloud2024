package com.atguigu.cloud.apis;

import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import lombok.Getter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("cloud-gateway")
public interface PayFeignApi {

    /**
     * 新增一条支付流水记录
     * @param payDTO
     * @return
     */
    @PostMapping("/pay/add")
    ResultData addPayment(@RequestBody PayDTO payDTO);

    /**
     * 获取一条支付流水记录
     * @param id 主键id
     * @return
     */
    @GetMapping("/pay/get/{id}")
    ResultData getPayInfo(@PathVariable("id") Integer id);

    /**
     * OpenFeign负载均衡演示
     * @return
     */
    @GetMapping("/pay/get/consul/config")
    String myLB();

    /**
     * Resilience4j CircuitBreaker 例子
     * @param id
     * @return
     */
    @GetMapping("/pay/circuit/{id}")
    String myCircuit(@PathVariable("id") Integer id);

    /**
     * Resilience4j Bulkhead 的例子
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/bulkhead/{id}")
    String myBulkhead(@PathVariable("id") Integer id);

    /**
     * Resilience4j Ratelimit 的例子
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/ratelimit/{id}")
    String myRatelimit(@PathVariable("id") Integer id);

    /**
     * Micrometer 进行链路监控的例子
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/micrometer/{id}")
    String myMicrometer(@PathVariable("id") Integer id);

    /**
     * GateWay进行网关测试案例01
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/gateway/get/{id}")
    ResultData getById(@PathVariable("id") Integer id);

    /**
     * GateWay进行网关测试案例02
     * @return
     */
    @GetMapping(value = "/pay/gateway/info")
    ResultData<String> getGatewayInfo();
}
