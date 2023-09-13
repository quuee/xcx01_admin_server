package com.xxx.xcx01.web.controller;

import com.xxx.xcx01.convert.UserConvert;
import com.xxx.xcx01.support.aspect.SysLog;
import com.xxx.xcx01.support.authentication.XCXAdminAuthenticationToken;
import com.xxx.xcx01.support.util.Result;
import com.xxx.xcx01.web.entity.admin.UserEntity;
import com.xxx.xcx01.web.params.UpdatePasswordParam;
import com.xxx.xcx01.web.params.UserParam;
import com.xxx.xcx01.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserConvert userConvert;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "info",method = RequestMethod.GET)
    public Result<UserEntity> info(){
        XCXAdminAuthenticationToken authentication = (XCXAdminAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        Long userId = authentication.getId();
        UserEntity byId = userService.getById(userId);
        return Result.ok(byId);
    }

    @PreAuthorize("hasAuthority('permission:admin:saveOrUpdate')")
    @RequestMapping(value = "saveOrUpdate",method = RequestMethod.POST)
    @SysLog(operation = "saveOrUpdate")
    public Result<UserEntity> saveOrUpdate(@RequestBody UserParam user){
        UserEntity userEntity = userConvert.convertToEntity(user);
        if(userEntity.getId() == null || userEntity.getId() <=0){
            // 新增
            userService.save(userEntity);
        }else{
            userService.updateById(userEntity);
        }
        return Result.ok(userEntity);
    }

    @RequestMapping(value = "updatePassword",method = RequestMethod.PUT)
    public Result updatePassword(@RequestBody UpdatePasswordParam updatePasswordParam){

        UserEntity byId = userService.getById(updatePasswordParam.getUserId());

        boolean matches = passwordEncoder.matches(updatePasswordParam.getOldPassword(), byId.getPassword());
        if(!matches){
            return Result.error("原密码错误");
        }
        String encode = passwordEncoder.encode(updatePasswordParam.getNewPassword());
        byId.setPassword(encode);
        userService.updateById(byId);

        return Result.ok();
    }
}
