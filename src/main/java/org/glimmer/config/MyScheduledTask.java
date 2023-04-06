package org.glimmer.config;

import org.glimmer.mapper.PDFFilesMapper;
import org.glimmer.service.UpdateLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.List;


@Configuration
public class MyScheduledTask implements SchedulingConfigurer {



    @Autowired
    PDFFilesMapper pdfFilesMapper;

    @Autowired
    UpdateLikeService updateLikeService;


    /**
     * 定时存储userlikefile
     * @param taskRegistrar
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar){
        taskRegistrar.addFixedRateTask(() ->
                {
                    List<Long> filesId =  pdfFilesMapper.getFilesId();
                    for (Long fileId : filesId){
                        updateLikeService.updateLike(fileId);
                        updateLikeService.updateDislike(fileId);
                        updateLikeService.updateLikeCount(fileId);
                        updateLikeService.updateDislikeCount(fileId);
                    }
                },1000*50*60);
    }
}
