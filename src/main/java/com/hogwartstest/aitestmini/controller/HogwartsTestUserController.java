package com.hogwartstest.aitestmini.controller;

import com.hogwartstest.aitestmini.common.Token;
import com.hogwartstest.aitestmini.common.TokenDb;
import com.hogwartstest.aitestmini.dto.AddUserDto;
import com.hogwartstest.aitestmini.dto.LoginUserDto;
import com.hogwartstest.aitestmini.dto.ResultDto;
import com.hogwartstest.aitestmini.entity.HogwartsTestUser;
import com.hogwartstest.aitestmini.service.HogwartsTestUserService;
import com.hogwartstest.aitestmini.util.CopyUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @Author tlibn
 * @Date 2020/6/12 16:48
 **/
@Slf4j
@RestController
@RequestMapping("/user")
public class HogwartsTestUserController {

    @Autowired
    private HogwartsTestUserService hogwartsTestUserService;

    @Autowired
    private TokenDb tokenDb;

    /**
     *
     * @param addUserDto
     * @return
     */
    @ApiOperation(value = "用户注册", notes="仅用于测试用户")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResultDto<HogwartsTestUser> save(@RequestBody AddUserDto addUserDto){

        log.info("用户注册-入参= "+ addUserDto);

        if(Objects.isNull(addUserDto)){
            return ResultDto.success("用户信息不能为空");
        }

        String userName = addUserDto.getUserName();

        if(StringUtils.isEmpty(userName)){
            return ResultDto.success("用户名不能为空");
        }

        String pwd = addUserDto.getPassword();

        if(StringUtils.isEmpty(pwd)){
            return ResultDto.success("密码不能为空");
        }

        HogwartsTestUser hogwartsTestUser = new HogwartsTestUser();
        CopyUtil.copyPropertiesCglib(addUserDto,hogwartsTestUser);
        ResultDto<HogwartsTestUser> resultDto = hogwartsTestUserService.save(hogwartsTestUser);
        return resultDto;
    }

    @ApiOperation(value = "登录接口")
    @PostMapping("/login")
    public ResultDto<Token> login(@RequestBody LoginUserDto loginUserDto) {
        String userName = loginUserDto.getUserName();
        String password = loginUserDto.getPassword();
        log.info("userName= "+userName);
        if(StringUtils.isEmpty(userName)||StringUtils.isEmpty(password)){
            return ResultDto.fail("用户名或密码不能为空");
        }

        ResultDto<Token> resultDto = hogwartsTestUserService.login(userName, password);

        return resultDto;
    }

    @ApiOperation(value = "Restful方式登陆,前后端分离时登录接口")
    @GetMapping("/{token}")
    public ResultDto isLogin(@PathVariable String token) {

        boolean loginFlag = tokenDb.isLogin(token);

        tokenDb.getTokenDto(token);

        return ResultDto.success("成功",tokenDb.getTokenDto(token));
    }



    public static void main(String[] args) {


        String pwd2 = DigestUtils.md5DigestAsHex("2343".getBytes());

        System.out.println(pwd2);
    }


}