package org.wyyt.springcloud.entity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.wyyt.springcloud.entity.entity.Gray;

/**
 * The mapper of table `t_gray`
 * <p>
 *
 * @author Ning.Zhang(Pegasus)
 * *****************************************************************
 * Name               Action            Time          Description  *
 * Ning.Zhang       Initialize        10/1/2020        Initialize  *
 * *****************************************************************
 */
@Mapper
public interface GrayMapper extends BaseMapper<Gray> {

}