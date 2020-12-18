package org.wyyt.gateway.admin.business.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.wyyt.gateway.admin.business.entity.base.BaseDto;

/**
 * The entity of table `t_route`
 * <p>
 *
 * @author Ning.Zhang(Pegasus)
 * *****************************************************************
 * Name               Action            Time          Description  *
 * Ning.Zhang       Initialize        10/1/2020        Initialize  *
 * *****************************************************************
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "`t_route`")
public class Route extends BaseDto {
    @TableField(value = "`route_id`")
    private String routeId;

    @TableField(value = "`route_name`")
    private String routeName;

    @TableField(value = "`uri`")
    private String uri;

    @TableField(value = "`predicates`")
    private String predicates;

    @TableField(value = "`filters`")
    private String filters;

    @TableField(value = "`order_num`")
    private Integer orderNum;

    @TableField(value = "`enabled`")
    private Boolean enabled;
}