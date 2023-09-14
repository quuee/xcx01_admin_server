package com.xxx.xcx01.web.controller;

import com.xxx.xcx01.support.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@RestController
@Tag(name = "上传")
public class CommonController {

    @Value("${file.localDir:/home/x/Pictures/ProjectUploadFile/xcx01-images}")
    private String localDir;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    @Operation(summary = "上传图片")
    @Parameters({
            @Parameter(name = "multipartFile",description = "文件",required = true,in= ParameterIn.QUERY),
    })
    @RequestMapping(value = "/upload/image",method = RequestMethod.POST)
    public Result uploadImage(MultipartFile multipartFile) throws IOException {

        try {
            BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
            bufferedImage.getHeight();
            bufferedImage.getWidth();
        }catch (IOException e){
            //如果是不是图片文件不能读取
            return Result.error("无法读取图片");
        }


        String originalFilename = multipartFile.getOriginalFilename();
        originalFilename = Objects.requireNonNull(originalFilename).toLowerCase();



        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String format = dateTimeFormatter.format(LocalDateTime.now());

        File dir = new File(localDir);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File file = new File(localDir+"/"+format+suffix);
        multipartFile.transferTo(file);

        return Result.ok(file.getPath());
    }
}
