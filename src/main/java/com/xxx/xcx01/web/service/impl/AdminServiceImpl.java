package com.xxx.xcx01.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.xcx01.support.authentication.AdminDetails;
import com.xxx.xcx01.web.entity.admin.AdminEntity;
import com.xxx.xcx01.web.entity.admin.MenuEntity;
import com.xxx.xcx01.web.mapper.admin.MenuMapper;
import com.xxx.xcx01.web.mapper.admin.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.xxx.xcx01.web.service.AdminService;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service("userServiceImpl")
public class AdminServiceImpl extends ServiceImpl<AdminMapper, AdminEntity> implements AdminService {

    @Autowired
    private MenuMapper menuMapper;


    @Override
    public AdminDetails loadUserByUsername(String adminName) throws UsernameNotFoundException {

        LambdaQueryWrapper<AdminEntity> eq = new LambdaQueryWrapper<AdminEntity>().eq(AdminEntity::getAdminName, adminName);
        AdminEntity userEntity = baseMapper.selectOne(eq);

        if(ObjectUtils.isEmpty(userEntity)){
            throw new UsernameNotFoundException("can not found adminName: "+adminName);
        }
        //如果用户被限制被禁用，可以抛出异常

        // 查询权限
        List<MenuEntity> menuEntities = menuMapper.selectMenuByAdminId(userEntity.getAid());
        // 连表查询时，查询的结果都是null，但是list.size()=1，然后出现空指针。
        // 因为对于查询者来说这条数据是空的，仅仅是因为要查询的字段是空的，但是其他字段不为空，所以mybatis没有把这条记录当做空数据，而是映射给我们的对象，导致list中多了一个空对象
        List<MenuEntity> collect = menuEntities.stream().filter(Objects::nonNull).collect(Collectors.toList());

        return new AdminDetails(userEntity.getAid(),userEntity.getAdminName(),userEntity.getPassword(),userEntity.getAvatarUrl(),collect);
    }
}
