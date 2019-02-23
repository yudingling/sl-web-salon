
var Notice = {
    error: function(title, content){
        Notification.create(
            title,
            content,
            'fa fa-exclamation-triangle fa-lg',
            'fail',
            'fadeInDown',
            2,
            4
        );
    },
    
    ok: function(title, content){
        Notification.create(
            title,
            content,
            'fa fa-check fa-lg',
            'success',
            'fadeInDown',
            2,
            2
        );
    },

    info: function(title, content){
        Notification.create(
            title,
            content,
            'fa fa-info-circle fa-lg',
            'info',
            'fadeInDown',
            2,
            2
        );
    },
    
};

var evalJson = function(value) {
    if (Object.prototype.toString.call(value) === "[object String]")
        return eval('(' + value + ')');
    else
        return value;
};