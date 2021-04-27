package com.actstrady.maclient.controller;

import com.actstrady.maclient.service.MacServices;
import com.actstrady.maclient.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 获取mac
 *
 * @author ActStrady
 * @date 2021/4/27
 */
@RestController
@RequestMapping("/mac")
public class Maclient {
    @Autowired
    private MacServices macServices;

    @GetMapping(value = "/address")
    public Result<?> getMacAddress() {
        List<String> macList = macServices.getMacList();
        if (CollectionUtils.isEmpty(macList)) {
            return Result.error("获取mac失败！");
        }
        return Result.ok(macList);
    }
}
