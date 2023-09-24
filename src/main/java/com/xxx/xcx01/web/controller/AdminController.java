package com.xxx.xcx01.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxx.xcx01.convert.UserConvert;
import com.xxx.xcx01.support.aspect.SysLog;
import com.xxx.xcx01.support.authentication.XCXAdminAuthenticationToken;
import com.xxx.xcx01.support.util.PageResult;
import com.xxx.xcx01.support.util.Result;
import com.xxx.xcx01.web.entity.admin.AdminEntity;
import com.xxx.xcx01.web.entity.front.UserEntity;
import com.xxx.xcx01.web.params.AdminParam;
import com.xxx.xcx01.web.params.UpdatePasswordParam;
import com.xxx.xcx01.web.params.UserParam;
import com.xxx.xcx01.web.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@Tag(name = "admin")
@RequestMapping("admin")
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserConvert userConvert;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Operation(summary = "admin信息")
    @RequestMapping(value = "info",method = RequestMethod.GET)
    public Result<AdminEntity> info(){
        XCXAdminAuthenticationToken authentication = (XCXAdminAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        Long userId = authentication.getId();
        AdminEntity byId = adminService.getById(userId);
        return Result.ok(byId);
    }

    @Operation(summary = "admin信息")
    @RequestMapping(value = "infoById",method = RequestMethod.GET)
    public Result<AdminEntity> info(@RequestParam Long aid){

        AdminEntity byId = adminService.getById(aid);
        return Result.ok(byId);
    }

    @Operation(summary = "新增或修改用户")
    @PreAuthorize("hasAuthority('permission:admin:saveOrUpdate')")
    @RequestMapping(value = "saveOrUpdate",method = RequestMethod.POST)
    @SysLog(operation = "saveOrUpdate")
    public Result<AdminEntity> saveOrUpdate(@RequestBody UserParam user){
        AdminEntity adminEntity = userConvert.convertToEntity(user);
        if(adminEntity.getAid() == null || adminEntity.getAid() <=0){
            // 新增
            String encode = passwordEncoder.encode(adminEntity.getPassword());
            adminEntity.setPassword(encode);
            adminEntity.setCreateTime(new Date());
            adminService.save(adminEntity);
        }else{
            adminService.updateById(adminEntity);
        }
        return Result.ok(adminEntity);
    }
    @Operation(summary = "修改密码")
    @RequestMapping(value = "updatePassword",method = RequestMethod.PUT)
    public Result updatePassword(@RequestBody UpdatePasswordParam updatePasswordParam){

        AdminEntity byId = adminService.getById(updatePasswordParam.getAid());

        boolean matches = passwordEncoder.matches(updatePasswordParam.getOldPassword(), byId.getPassword());
        if(!matches){
            return Result.error("原密码错误");
        }
        String encode = passwordEncoder.encode(updatePasswordParam.getNewPassword());
        byId.setPassword(encode);
        adminService.updateById(byId);

        return Result.ok();
    }
    @Operation(summary = "修改头像")
    @RequestMapping(value = "changeAvatar",method = RequestMethod.PUT)
    public Result changeAvatar(@RequestBody Map<String,String> image){
        String imageUrl = image.get("imageUrl");
        XCXAdminAuthenticationToken authentication = (XCXAdminAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        Long userId = authentication.getId();
        AdminEntity byId = adminService.getById(userId);

        byId.setAvatarUrl(imageUrl);
        adminService.updateById(byId);
        return Result.ok();
    }

    @Operation(summary = "查询admin用户")
    @RequestMapping(value = "pageList",method = RequestMethod.GET)
    public Result<PageResult<AdminEntity>> pageList(AdminParam adminParam){
        Page<AdminEntity> userEntityPage = new Page<>(adminParam.getPageNo(), adminParam.getPageSize());
        LambdaQueryWrapper<AdminEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(!ObjectUtils.isEmpty(adminParam.getAdminName()),AdminEntity::getAdminName,adminParam.getAdminName());
        wrapper.like(!ObjectUtils.isEmpty(adminParam.getPhone()),AdminEntity::getPhone,adminParam.getPhone());
        wrapper.like(!ObjectUtils.isEmpty(adminParam.getEmail()),AdminEntity::getEmail,adminParam.getEmail());
        Page<AdminEntity> page = adminService.page(userEntityPage, wrapper);

        return Result.ok(new PageResult<>(page.getRecords(),page.getTotal()));
    }
}
