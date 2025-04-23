package com.project.demo.service;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import ai.onnxruntime.OrtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.nio.FloatBuffer;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.logging.Logger;

@Service
public class RainfallPredictionService {

    private static final Logger logger = Logger.getLogger(RainfallPredictionService.class.getName());

    // ONNX Runtime 环境和会话
    private OrtEnvironment env;
    private OrtSession session;

    // 模型文件路径，从 application.properties 或默认值加载
    @Value("${onnx.model.path:src/main/resources/models/rainfall_model.onnx}")
    private String modelPath;

    // 初始化方法，在 Spring Bean 创建后加载 ONNX 模型
    @PostConstruct
    public void init() {
        try {
            logger.info("Loading ONNX model from: " + modelPath);
            env = OrtEnvironment.getEnvironment();
            session = env.createSession(Paths.get(modelPath).toString(), new OrtSession.SessionOptions());
            logger.info("ONNX model loaded successfully.");
        } catch (OrtException e) {
            logger.severe("Failed to load ONNX model: " + e.getMessage());
            throw new RuntimeException("Failed to load ONNX model", e);
        }
    }

    /**
     * 预测降雨量
     * @param input 模型输入特征数组，例如 [temperature, humidity, pressure]
     * @return 预测的降雨量（浮点数）
     * @throws OrtException 如果推理过程中发生错误
     */
    public float predictRainfall(float[] input) throws OrtException {
        // 假设模型输入为 1 维浮点数数组，形状为 [1, input.length]
        long[] inputShape = new long[]{1, input.length};

        // 创建 ONNX Tensor
        try (OnnxTensor inputTensor = OnnxTensor.createTensor(env, FloatBuffer.wrap(input), inputShape)) {
            // 运行推理
            OrtSession.Result result = session.run(Collections.singletonMap("input", inputTensor));

            // 假设模型输出为单个浮点数（降雨量），根据你的模型调整输出解析
            float[] output = (float[]) result.get(0).getValue();
            return output[0];
        } catch (OrtException e) {
            logger.severe("Failed to run inference: " + e.getMessage());
            throw e;
        }
    }
}