package org.glimmer.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.glimmer.domain.PDFFiles;

import java.util.List;

@Mapper
public interface PDFFilesMapper extends BaseMapper<PDFFiles> {

    @Select("select * from sys_files")
    List<PDFFiles> findAll();
}
