package org.wyyt.kafka.monitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.wyyt.kafka.monitor.entity.dto.SysMailConfig;

/**
 * The mapper for table 'sys_mail_config'.
 * <p>
 * *****************************************************************
 * Name               Action            Time          Description  *
 * Ning.Zhang       Initialize       01/01/2021       Initialize   *
 * *****************************************************************
 */
@Mapper
public interface SysMailConfigMapper extends BaseMapper<SysMailConfig> {
}