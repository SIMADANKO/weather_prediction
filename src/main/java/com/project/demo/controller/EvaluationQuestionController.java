package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.EvaluationQuestion;
import com.project.demo.service.EvaluationQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 题库
 *
 */
@RestController
@RequestMapping("evaluation_question")
public class EvaluationQuestionController extends BaseController<EvaluationQuestion, EvaluationQuestionService> {
    /**
     * 服务对象
     */
    @Autowired
    public EvaluationQuestionController(EvaluationQuestionService service) {
        setService(service);
    }

}


