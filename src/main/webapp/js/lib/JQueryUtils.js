function JQueryUtils() {
  var gSobject = gs.inherit(gs.baseClass,'JQueryUtils');
  gSobject.clazz = { name: 'org.grooscript.web.JQueryUtils', simpleName: 'JQueryUtils'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.bind = function(selector, target, nameProperty, closure) {
    if (closure === undefined) closure = null;
    var sourceDom = $(selector);
        //Create set method
        var nameSetMethod = 'set'+nameProperty.capitalize();

        if (sourceDom.is(":text")) {
            target[nameSetMethod] = function(newValue) {
                this[nameProperty] = newValue;
                sourceDom.val(newValue);
                if (closure) { closure(newValue); };
            };
            sourceDom.bind('input', function() {
                var currentVal = $(this).val();
                target[nameProperty] = currentVal;
                if (closure) { closure(currentVal); };
            });
        } else if (sourceDom.is('textarea')) {
            target[nameSetMethod] = function(newValue) {
                this[nameProperty] = newValue;
                sourceDom.val(newValue);
                if (closure) { closure(newValue); };
            };
            sourceDom.bind('input propertychange', function() {
                var currentVal = $(this).val();
                target[nameProperty] = currentVal;
                if (closure) { closure(currentVal); };
            });
        } else if (sourceDom.is(":checkbox")) {
            target[nameSetMethod] = function(newValue) {
                this[nameProperty] = newValue;
                sourceDom.prop('checked', newValue);
                if (closure) { closure(newValue); };
            };
            sourceDom.change(function() {
                var currentVal = $(this).is(':checked');
                target[nameProperty] = currentVal;
                if (closure) { closure(currentVal); };
            });
        } else if (sourceDom.is(":radio")) {
            target[nameSetMethod] = function(newValue) {
                this[nameProperty] = newValue;
                $(selector +'[value="' + newValue + '"]').prop('checked', true);
                if (closure) { closure(newValue); };
            };
            sourceDom.change(function() {
                var currentVal = $(this).val();
                target[nameProperty] = currentVal;
                if (closure) { closure(currentVal); };
            });
        } else if (sourceDom.is("select")) {
            target[nameSetMethod] = function(newValue) {
                this[nameProperty] = newValue;
                sourceDom.val(newValue);
                if (closure) { closure(newValue); };
            };
            sourceDom.bind('change', function() {
                var currentVal = $(this).val();
                target[nameProperty] = currentVal;
                if (closure) { closure(currentVal); };
            });
        } else {
            console.log('Not supporting bind for selector ' + selector);
        }
  }
  gSobject.existsId = function(name) {
    return $("#" + name).length > 0
  }
  gSobject.existsGroup = function(name) {
    return $('input:radio[name=' + name + ']').length > 0
  }
  gSobject.bindEvent = function(id, name, func) {
    $('#'+id).on(name, func);
  }
  gSobject.JQueryUtils1 = function(map) { gs.passMapToObject(map,this); return this;};
  if (arguments.length==1) {gSobject.JQueryUtils1(arguments[0]); }
  
  return gSobject;
};
