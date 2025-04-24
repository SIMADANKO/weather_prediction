package com.project.demo.service;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import ai.onnxruntime.OrtException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.FloatBuffer;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class RainfallPredictionService {

    private static final Logger logger = Logger.getLogger(RainfallPredictionService.class.getName());

    // ONNX Runtime 环境和会话
    private OrtEnvironment env;
    private OrtSession session;

    // 模型文件路径
    @Value("${onnx.model.path:src/main/resources/models/rainfall_model.onnx}")
    private String modelPath;

    // JSON文件路径
    @Value("${data.district.info.path:src/main/resources/data/district_info.json}")
    private String districtInfoPath;

    @Value("${data.rainfall.history.path:src/main/resources/data/recent_rainfall.json}")
    private String rainfallHistoryPath;

    // 存储区县信息和降水历史数据
    private Map<String, DistrictInfo> districtInfo;
    private Map<String, RainfallData> rainfallHistory;

    // JSON解析器
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 初始化方法，加载ONNX模型和JSON数据
    @PostConstruct
    public void init() {
        try {
            // 加载ONNX模型
            logger.info("Loading ONNX model from: " + modelPath);
            env = OrtEnvironment.getEnvironment();
            session = env.createSession(Paths.get(modelPath).toString(), new OrtSession.SessionOptions());
            logger.info("ONNX model loaded successfully.");

            // 加载JSON数据
            logger.info("Loading district info from: " + districtInfoPath);
            districtInfo = objectMapper.readValue(
                    new File(districtInfoPath),
                    objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, DistrictInfo.class)
            );

            logger.info("Loading rainfall history from: " + rainfallHistoryPath);
            rainfallHistory = objectMapper.readValue(
                    new File(rainfallHistoryPath),
                    objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, RainfallData.class)
            );

            logger.info("JSON data loaded successfully.");
        } catch (OrtException e) {
            logger.severe("Failed to load ONNX model: " + e.getMessage());
            throw new RuntimeException("Failed to load ONNX model", e);
        } catch (Exception e) {
            logger.severe("Failed to load JSON data: " + e.getMessage());
            throw new RuntimeException("Failed to load JSON data", e);
        }
    }

    /**
     * 根据区县名称、年份和月份预测降雨量
     *
     * @param districtName 区县名称
     * @param year 预测年份
     * @param month 预测月份 (1-12)
     * @return 预测的降雨量
     * @throws OrtException 如果推理失败
     * @throws IllegalArgumentException 如果输入无效
     */
    public float predictRainfall(String districtName, int year, int month) throws OrtException {
        // 验证输入
        if (!districtInfo.containsKey(districtName)) {
            throw new IllegalArgumentException("找不到区县: " + districtName);
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("月份必须在1-12之间");
        }

        // 获取区县信息
        DistrictInfo district = districtInfo.get(districtName);
        RainfallData rainfall = rainfallHistory.getOrDefault(districtName, new RainfallData(50.0f, 50.0f, 50.0f, 50.0f));

        // 构建特征
        float season = getSeason(month);
        float monthSin = (float) Math.sin(2 * Math.PI * month / 12);
        float monthCos = (float) Math.cos(2 * Math.PI * month / 12);

        float[] input = new float[] {
                month, year, season, district.districtCode, district.cluster,
                rainfall.lag1, rainfall.lag3, rainfall.ma3,
                monthSin, monthCos
        };

        // 进行预测
        long[] inputShape = new long[]{1, input.length};
        try (OnnxTensor inputTensor = OnnxTensor.createTensor(env, FloatBuffer.wrap(input), inputShape)) {
            OrtSession.Result result = session.run(Collections.singletonMap("float_input", inputTensor));
            float[][] output = (float[][]) result.get(0).getValue();
            return output[0][0];
        } catch (OrtException e) {
            logger.severe("Failed to run inference: " + e.getMessage());
            throw e;
        }
    }

    // 季节映射
    private float getSeason(int month) {
        int[] seasonMap = {1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4};
        return seasonMap[month - 1];
    }

    // 区县信息类
    static class DistrictInfo {
        @JsonProperty("district_code")
        public int districtCode;

        @JsonProperty("cluster")
        public int cluster;

        public DistrictInfo() {}
        public DistrictInfo(int districtCode, int cluster) {
            this.districtCode = districtCode;
            this.cluster = cluster;
        }
    }

    // 降水数据类
    static class RainfallData {
        @JsonProperty("latest")
        public float latest;

        @JsonProperty("lag_1")
        public float lag1;

        @JsonProperty("lag_3")
        public float lag3;

        @JsonProperty("ma_3")
        public float ma3;

        public RainfallData() {}
        public RainfallData(float latest, float lag1, float lag3, float ma3) {
            this.latest = latest;
            this.lag1 = lag1;
            this.lag3 = lag3;
            this.ma3 = ma3;
        }
    }
}