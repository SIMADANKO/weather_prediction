package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.EvaluationUserAnswer;
import com.project.demo.service.EvaluationUserAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户答题
 *
 */
@RestController
@RequestMapping("evaluation_user_answer")
public class EvaluationUserAnswerController extends BaseController<EvaluationUserAnswer, EvaluationUserAnswerService> {
    /**
     * 服务对象
     */
    @Autowired
    public EvaluationUserAnswerController(EvaluationUserAnswerService service) {
        setService(service);
    }

}


