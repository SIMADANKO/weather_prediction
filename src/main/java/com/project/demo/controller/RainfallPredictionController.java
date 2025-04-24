package com.project.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.project.demo.service.RainfallPredictionService;
import ai.onnxruntime.OrtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rainfall")
public class RainfallPredictionController {

    @Autowired
    private RainfallPredictionService predictionService;

    /**
     * 预测降雨量
     */
    @PostMapping("/predict")
    public ResponseEntity<JSONObject> predictRainfall(@RequestBody JSONObject request) {
        JSONObject response = new JSONObject();

        try {
            // 获取并验证输入
            String districtName = request.getString("districtName");
            Integer year = request.getInteger("year");
            Integer month = request.getInteger("month");

            if (districtName == null || districtName.trim().isEmpty()) {
                response.put("error", "缺少或无效的 'districtName' 参数");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            if (year == null || year < 2000 || year > 2100) {
                response.put("error", "缺少或无效的 'year' 参数，需为2000-2100之间的整数");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            if (month == null || month < 1 || month > 12) {
                response.put("error", "缺少或无效的 'month' 参数，需为1-12之间的整数");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // 调用服务进行预测
            float rainfall = predictionService.predictRainfall(districtName, year, month);

            // 返回预测结果
            response.put("districtName", districtName);
            response.put("year", year);
            response.put("month", month);
            response.put("rainfall", rainfall);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (OrtException e) {
            response.put("error", "预测失败: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            response.put("error", "输入错误: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("error", "服务器错误: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}