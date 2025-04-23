package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.Evaluation;
import com.project.demo.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 考试
 *
 */
@RestController
@RequestMapping("evaluation")
public class EvaluationController extends BaseController<Evaluation, EvaluationService> {
    /**
     * 服务对象
     */
    @Autowired
    public EvaluationController(EvaluationService service) {
        setService(service);
    }

}


