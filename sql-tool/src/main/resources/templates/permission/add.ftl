﻿<!DOCTYPE html>
<html>
<head>
    <#include "../common/layui.ftl">
</head>
<body>
<div class="layui-form" lay-filter="layuiadmin-form-admin" id="layuiadmin-form-admin" style="padding: 20px 30px 0 0;">
    <div class="layui-form-item">
        <label class="layui-form-label">角色名称</label>
        <div class="layui-input-inline">
            <select name="sysRoleId" lay-verify="required">
                <option value="">请选择角色</option>
                <#list roles as role>
                    <option value="${role.id}">${role.name}</option>
                </#list>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">逻辑表名称</label>
        <div class="layui-input-inline">
            <select name="tableName" lay-verify="required">
                <option value="">请选择表名</option>
                <#list tables as table>
                    <option value="${table.name}">${table.name}</option>
                </#list>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">新增权限</label>
        <div class="layui-input-inline">
            <input checked="checked" type="checkbox" name="insert" lay-verify="required" class="layui-input"
                   title="新增权限">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">删除权限</label>
        <div class="layui-input-inline">
            <input checked="checked" type="checkbox" name="delete" lay-verify="required" class="layui-input"
                   title="删除权限">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">修改权限</label>
        <div class="layui-input-inline">
            <input checked="checked" type="checkbox" name="update" lay-verify="required" class="layui-input"
                   title="修改权限">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">查询权限</label>
        <div class="layui-input-inline">
            <input checked="checked" type="checkbox" name="select" lay-verify="required" class="layui-input"
                   title="查询权限">
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="btn_confirm" id="btn_confirm" value="确认">
    </div>
</div>
<script type="text/javascript">
    layui.config({base: '../../..${ctx}/layuiadmin/'}).extend({index: 'lib/index'}).use(['index', 'form'], function () {
        parent.layer.iframeAuto(parent.layer.getFrameIndex(window.name));
    });
</script>
</body>
</html>
