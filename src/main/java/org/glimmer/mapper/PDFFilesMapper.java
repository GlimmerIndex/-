package org.glimmer.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.glimmer.domain.PDFFiles;

@Mapper
public interface PDFFilesMapper extends BaseMapper<PDFFiles> {

}
