package com.project.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.project.demo.service.RainfallPredictionService;
import ai.onnxruntime.OrtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
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
            // 检查是否包含 "features"
            if (!request.containsKey("features")) {
                response.put("error", "缺少 'features' 参数");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // 获取并验证特征数组
            com.alibaba.fastjson.JSONArray featuresArray = request.getJSONArray("features");
            if (featuresArray == null || featuresArray.isEmpty()) {
                response.put("error", "'features' 数组为空");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // 安全转换为 float[]
            float[] input = new float[featuresArray.size()];
            for (int i = 0; i < featuresArray.size(); i++) {
                Object val = featuresArray.get(i);
                if (val instanceof Number) {
                    input[i] = ((Number) val).floatValue();
                } else {
                    response.put("error", "第 " + i + " 个特征无法转换为浮点数: " + val);
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
            }

            // 调用服务进行预测
            float rainfall = predictionService.predictRainfall(input);

            // 返回预测结果
            response.put("rainfall", rainfall);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (OrtException e) {
            response.put("error", "预测失败: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            response.put("error", "输入处理错误: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}