package com.project.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.project.demo.service.RainfallPredictionService;
import ai.onnxruntime.OrtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RainfallPredictionController {

    @Autowired
    private RainfallPredictionService predictionService;

    /**
     * 预测降雨量
     * @param request JSON 请求体，包含特征数组，例如 {"features": [25.0, 80.0, 1013.0]}
     * @return JSON 响应，包含预测的降雨量或错误信息
     */
    @PostMapping("/predict")
    public ResponseEntity<JSONObject> predictRainfall(@RequestBody JSONObject request) {
        JSONObject response = new JSONObject();

        try {
            // 解析输入特征数组
            if (!request.containsKey("features")) {
                response.put("error", "Missing 'features' in request body");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // 将 JSON 数组转换为 float[]
            com.alibaba.fastjson.JSONArray featuresArray = request.getJSONArray("features");
            if (featuresArray == null || featuresArray.isEmpty()) {
                response.put("error", "'features' array is empty");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            float[] input = new float[featuresArray.size()];
            for (int i = 0; i < featuresArray.size(); i++) {
                input[i] = featuresArray.getFloat(i);
            }

            // 调用服务进行预测
            float rainfall = predictionService.predictRainfall(input);

            // 返回预测结果
            response.put("rainfall", rainfall);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (OrtException e) {
            response.put("error", "Prediction failed: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            response.put("error", "Invalid input: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}