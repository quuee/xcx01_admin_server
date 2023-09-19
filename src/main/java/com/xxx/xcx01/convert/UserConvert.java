package com.xxx.xcx01.convert;

import com.xxx.xcx01.web.entity.admin.AdminEntity;
import com.xxx.xcx01.web.params.UserParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserConvert {


    @Mappings({
            @Mapping(source = "userId",target = "id"),
            @Mapping(source = "username",target = "username"),
            @Mapping(source = "password",target = "password"),
            @Mapping(source = "avatarUrl",target = "avatarUrl"),
            @Mapping(source = "phone",target = "phone"),
            @Mapping(source = "email",target = "email"),
    })
    AdminEntity convertToEntity(UserParam param);
}
