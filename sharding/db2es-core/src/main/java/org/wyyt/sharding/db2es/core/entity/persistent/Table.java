package org.wyyt.sharding.db2es.core.entity.persistent;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The entity for table t_table
 * <p>
 * *****************************************************************
 * Name               Action            Time          Description  *
 * Ning.Zhang       Initialize       01/01/2021       Initialize   *
 * *****************************************************************
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "`t_table`")
public final class Table extends BaseDto {
    private static final long serialVersionUID = 1L;
    /**
     * 表信息
     */
    @TableField(value = "`info`")
    private String info;
}