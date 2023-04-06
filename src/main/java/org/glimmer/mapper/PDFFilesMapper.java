package org.glimmer.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.glimmer.domain.PDFFiles;

import java.util.List;

@Mapper
public interface PDFFilesMapper extends BaseMapper<PDFFiles> {

    @Select("select * from sys_files")
    List<PDFFiles> findAll();


    @Select("SELECT id FROM sys_files")
    List<Long> getFilesId();


    @Update("UPDATE sys_files SET like_number = #{likeNumber} WHERE id = #{fileId}")
    Integer updateFileLikedCount(@Param("likeNumber") Long likeNumber,@Param("fileId") Long fileId);


    @Update("UPDATE sys_files SET dislike_number = #{dislikeNumber} WHERE id = #{fileId}")
    Integer updateFileDislikeCount(@Param("disNumber") Long dislikeNumber,@Param("fileId") Long fileId);

}
