;layui.define(function (e) {
    const i = (layui.$, layui.layer, layui.laytpl, layui.setter, layui.view, layui.admin);
    const $ = layui.$, admin = layui.admin;
    i.events.logout = function () {
        admin.quit();
    }, e("common", {});

    admin.SYSTEM_PROMPT = '系统提示';
    admin.OPT_SUCCESS = '操作成功';
    admin.OPT_FAILURE = '操作失败';
    admin.BUTTONS = ['确定', '取消'];

    admin.postQuiet = function (url, data, success, error) {
        $.ajax({
            url: url,
            type: 'POST',
            data: data,
            cache: false,
            complete: function (xhr) {
                if (xhr.responseText.indexOf("<div class=\"layadmin-user-login-main\">") > -1) {
                    admin.toLogin();
                } else {
                    const result = xhr.responseJSON;
                    if (result.success) {
                        if (success) success(result);
                    } else {
                        if (error) {
                            error(result);
                        } else {
                            admin.error('系统错误', result.error);
                        }
                    }
                }
            }
        });
    };

    admin.post = function (url, data, success, error) {
        layer.load();
        $.ajax({
            url: url,
            type: 'POST',
            data: data,
            cache: false,
            complete: function (xhr) {
                if (xhr.responseText.indexOf("<div class=\"layadmin-user-login-main\">") > -1) {
                    admin.toLogin();
                } else {
                    const result = xhr.responseJSON;
                    if (result.success) {
                        if (success) success(result);
                    } else {
                        if (error) {
                            error(result);
                        } else {
                            admin.error('系统错误', result.error);
                        }
                    }
                }
                layer.closeAll('loading');
            }
        });
    };

    admin.success = function (title, content, callback) {
        layer.open({
            title: title,
            content: content,
            icon: 1,
            end: function () {
                if (callback) callback();
            }
        });
    };

    admin.error = function (title, content, callback) {
        layer.open({
            title: title,
            content: content,
            icon: 2,
            end: function () {
                if (callback) callback();
            }
        });
    };

    admin.toJson = function (obj) {
        return JSON.parse(JSON.stringify(obj));
    };

    admin.quit = function () {
        admin.post("quit", {}, function () {
            admin.toLogin();
        });
    };

    admin.initPage = function () {
        if (window.top !== window.self) {
            top.location.href = location.href;
        }
    };

    admin.toLogin = function () {
        admin.initPage();
        location.href = "/"
    }

});