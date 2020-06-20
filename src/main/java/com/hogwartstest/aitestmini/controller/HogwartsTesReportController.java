package com.hogwartstest.aitestmini.controller;

import com.alibaba.fastjson.JSONObject;
import com.hogwartstest.aitestmini.common.TokenDb;
import com.hogwartstest.aitestmini.constants.UserConstants;
import com.hogwartstest.aitestmini.dto.*;
import com.hogwartstest.aitestmini.dto.task.TaskReportDto;
import com.hogwartstest.aitestmini.entity.HogwartsTestTask;
import com.hogwartstest.aitestmini.service.HogwartsTestReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @Author tlibn
 * @Date 2020/6/12 16:48
 **/
@Slf4j
@Api(tags = "霍格沃兹测试学院-报告管理")
@RestController
@RequestMapping("/report")
public class HogwartsTesReportController {

    @Autowired
    private HogwartsTestReportService hogwartsTestReportService;

    @Autowired
    private TokenDb tokenDb;

    /**
     *
     * @param taskId
     * @return
     */
    @ApiOperation(value = "获取allure报告")
    @GetMapping("/allureReport/{taskId}")
    public ResultDto<AllureReportDto> save(HttpServletRequest request, @PathVariable Integer taskId){

        log.info("报告管理-入参 taskId= "+ taskId);

        if(Objects.isNull(taskId)){
            return ResultDto.success("任务id不能为空");
        }

        TokenDto tokenDto = tokenDb.getTokenDto(request.getHeader(UserConstants.LOGIN_TOKEN));

        ResultDto<AllureReportDto> resultDto = hogwartsTestReportService.getAllureReport(tokenDto, taskId);

        return resultDto;
    }

    /**
     * 根据任务类型获取任务统计信息 - 饼状图
     * @return
     */
    @ApiOperation(value = "根据任务类型获取任务统计信息 - 饼状图")
    @GetMapping("/taskByType")
    public ResultDto<TaskReportDto> update(HttpServletRequest request){

        TokenDto tokenDto = tokenDb.getTokenDto(request.getHeader(UserConstants.LOGIN_TOKEN));
        log.info("根据任务类型获取任务统计信息-入参= " + JSONObject.toJSONString(tokenDto));

        ResultDto<TaskReportDto> resultDto = hogwartsTestReportService.getTaskByType(tokenDto);

        return resultDto;
    }

    /**
     * 根据任务状态获取任务统计信息 - 饼状图
     * @return
     */
    @ApiOperation(value = "根据任务状态获取任务统计信息 - 饼状图")
    @GetMapping("/taskByStatus")
    public ResultDto<TaskReportDto> getById(HttpServletRequest request){

        TokenDto tokenDto = tokenDb.getTokenDto(request.getHeader(UserConstants.LOGIN_TOKEN));
        log.info("根据任务类型获取任务统计信息-入参= " + JSONObject.toJSONString(tokenDto));

        ResultDto<TaskReportDto> resultDto = hogwartsTestReportService.getTaskByStatus(tokenDto);

        return resultDto;
    }

    /**
     * 任务中用例的数量统计信息 - 折线图
     * @param request
     * @param start 按时间倒叙开始序号
     * @param end 按时间倒叙结束序号
     * @return
     */
    @ApiOperation(value = "任务中用例的数量统计信息 - 折线图")
    @GetMapping("/taskByCaseCount")
    public ResultDto<List<HogwartsTestTask>> delete(HttpServletRequest request
            , @RequestParam(value = "start",required = false) Integer start
            , @RequestParam(value = "end",required = false) Integer end){

        TokenDto tokenDto = tokenDb.getTokenDto(request.getHeader(UserConstants.LOGIN_TOKEN));
        log.info("根据任务类型获取任务统计信息-入参= " + JSONObject.toJSONString(tokenDto));

        ResultDto<List<HogwartsTestTask>> resultDto = hogwartsTestReportService.getTaskByCaseCount(tokenDto, start, end);

        return resultDto;
    }


}
