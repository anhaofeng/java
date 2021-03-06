
layui.define(['jquery', 'nprogress'], function(exports) {
    var $ = layui.jquery,
        _modName = 'loader';

    var loader = {
        version: '1.0.0',
        load: function(options) {
            NProgress.start();
            var url = options.url,
                name = options.name,
                id = options.id,
                _elem = options.elem !== undefined ? $(options.elem) : $('#container');
            _elem.load(url, function(res, status, xhr) {
                if (status === "error" && options.onError) {
                    options.onError();
                }
                if (status === 'success') {
                    _elem.html(res);
                    options.onSuccess && options.onSuccess({ name, id });
                }
                options.onComplate && options.onComplate();
                NProgress.done();
            });
        },
        //动态加载script
        getScript: function(url, callback) {
            $.getScript(url, callback);
        }
    };
    exports('loader', loader);
});