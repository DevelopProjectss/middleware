package org.wyyt.gateway.admin.business.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.wyyt.gateway.admin.business.anno.TranRead;
import org.wyyt.gateway.admin.business.entity.Gray;
import org.wyyt.gateway.admin.business.mapper.GrayMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The service of `t_gray` table
 * <p>
 *
 * @author Ning.Zhang(Pegasus)
 * *****************************************************************
 * Name               Action            Time          Description  *
 * Ning.Zhang       Initialize        10/1/2020        Initialize  *
 * *****************************************************************
 */
@Service
public class GrayService extends ServiceImpl<GrayMapper, Gray> {

    @TranRead
    public Map<String, Gray> listMap() {
        final Map<String, Gray> result = new HashMap<>();
        final List<Gray> list = this.list();
        for (final Gray gray : list) {
            result.put(gray.getGrayId(), gray);
        }
        return result;
    }

    @TranRead
    public Gray getByGrayId(final String grayId) {
        final QueryWrapper<Gray> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Gray::getGrayId, grayId);
        return this.getOne(queryWrapper);
    }
}