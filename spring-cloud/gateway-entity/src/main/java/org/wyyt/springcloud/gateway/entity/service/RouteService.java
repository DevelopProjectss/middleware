package org.wyyt.springcloud.gateway.entity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.wyyt.springcloud.gateway.entity.anno.TranRead;
import org.wyyt.springcloud.gateway.entity.anno.TranSave;
import org.wyyt.springcloud.gateway.entity.entity.Route;
import org.wyyt.springcloud.gateway.entity.exception.GatewayException;
import org.wyyt.springcloud.gateway.entity.mapper.RouteMapper;

import java.util.List;

/**
 * The service of `t_route` table
 * <p>
 *
 * @author Ning.Zhang(Pegasus)
 * *****************************************************************
 * Name               Action            Time          Description  *
 * Ning.Zhang       Initialize        10/1/2020        Initialize  *
 * *****************************************************************
 */
@Service
public class RouteService extends ServiceImpl<RouteMapper, Route> {
    @TranRead
    public IPage<Route> page(final String routeId,
                             final Integer pageNum,
                             final Integer pageSize) {
        final QueryWrapper<Route> queryWrapper = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(routeId)) {
            queryWrapper.lambda().like(Route::getRouteId, routeId);
        }
        return this.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    @TranRead
    public List<Route> listEnableRoutes() {
        final QueryWrapper<Route> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Route::getEnabled, true).orderByAsc(Route::getRowCreateTime);
        return this.list(queryWrapper);
    }

    @TranRead
    public Route getByRouteId(final String routeId) {
        final QueryWrapper<Route> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Route::getRouteId, routeId);
        return this.getOne(queryWrapper);
    }

    @TranSave
    public void add(final String routeId,
                    final String description,
                    final String uri,
                    final String predicate,
                    final String filter,
                    final Integer orderNum,
                    final Boolean enabled) {
        Route route = this.getByRouteId(routeId);
        if (null != route) {
            throw new GatewayException(String.format("RouteId=%s的路由已存在", routeId));
        }
        route = new Route();
        route.setRouteId(routeId);
        route.setDescription(description);
        route.setUri(uri);
        route.setPredicates(predicate);
        route.setFilters(filter);
        route.setOrderNum(orderNum);
        route.setEnabled(enabled);
        this.save(route);
    }

    @TranSave
    public void edit(final long id,
                     final String routeId,
                     final String description,
                     final String uri,
                     final String predicate,
                     final String filter,
                     final Integer orderNum,
                     final Boolean enabled) {
        final Route route = this.getById(id);
        if (null == route) {
            throw new GatewayException(String.format("路由(id=%s)不存在", id));
        }
        route.setRouteId(routeId);
        route.setDescription(description);
        route.setUri(uri);
        route.setPredicates(predicate);
        route.setFilters(filter);
        route.setOrderNum(orderNum);
        route.setEnabled(enabled);
        this.updateById(route);
    }

    @TranSave
    public void delete(final long id) {
        final Route route = this.getById(id);
        if (null == route) {
            throw new GatewayException(String.format("路由(id=%s)不存在", id));
        }
        this.removeById(id);
    }

    @TranSave
    public void enable(final long id,
                       final boolean enable) {
        final Route route = this.getById(id);
        if (null == route) {
            throw new GatewayException(String.format("路由(id=%s)不存在", id));
        }
        route.setEnabled(enable);
        this.updateById(route);
    }
}