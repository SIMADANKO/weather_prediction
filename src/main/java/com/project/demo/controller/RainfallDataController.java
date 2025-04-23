package com.project.demo.controller;

import com.project.demo.entity.RainfallData;
import com.project.demo.service.RainfallDataService;
import com.project.demo.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;


/**
 * 降雨量数据：(RainfallData)表控制层
 *
 */
@RestController
@RequestMapping("/rainfall_data")
public class RainfallDataController extends BaseController<RainfallData, RainfallDataService> {

    /**
     * 降雨量数据对象
     */
    @Autowired
    public RainfallDataController(RainfallDataService service) {
        setService(service);
    }



    @PostMapping("/add")
    @Transactional
    public Map<String, Object> add(HttpServletRequest request) throws IOException {
        Map<String,Object> paramMap = service.readBody(request.getReader());
        this.addMap(paramMap);
        return success(1);
    }


}
