package com.xxx.xcx01.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.xcx01.support.authentication.AdminDetails;
import com.xxx.xcx01.web.entity.admin.AdminEntity;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface AdminService extends IService<AdminEntity>, UserDetailsService {

    AdminDetails loadUserByUsername(String username);
}
