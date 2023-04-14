package org.glimmer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.glimmer.domain.Docs;

@Mapper
public interface DocsMapper extends BaseMapper<Docs> {
}
