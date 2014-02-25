function Binder() {
  var gSobject = gs.inherit(gs.baseClass,'Binder');
  gSobject.clazz = { name: 'org.grooscript.web.Binder', simpleName: 'Binder'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.jQueryUtils = null;
  gSobject['bindAllProperties'] = function(target, closure) {
    if (closure === undefined) closure = null;
    return gs.mc(gs.gp(target,"properties"),"each",gs.list([function(name, value) {
      if (gs.mc(gSobject.jQueryUtils,"existsId",gs.list([name]))) {
        gs.mc(gSobject.jQueryUtils,"bind",gs.list(["#" + (name) + "", target, name, closure]));
      };
      if (gs.mc(gSobject.jQueryUtils,"existsGroup",gs.list([name]))) {
        return gs.mc(gSobject.jQueryUtils,"bind",gs.list(["input:radio[name=" + (name) + "]", target, name, closure]));
      };
    }]));
  }
  gSobject['bindAllMethods'] = function(target) {
    return gs.mc(gs.gp((target = gs.metaClass(target)),"methods"),"each",gs.list([function(method) {
      if (gs.mc(gs.gp(method,"name"),"endsWith",gs.list(["Click"]))) {
        var shortName = gs.mc(gs.gp(method,"name"),"substring",gs.list([0, gs.minus(gs.mc(gs.gp(method,"name"),"length",gs.list([])), 5)]));
        if (gs.mc(gSobject.jQueryUtils,"existsId",gs.list([shortName]))) {
          return gs.mc(gSobject.jQueryUtils,"bindEvent",gs.list([shortName, "click", target["" + (gs.gp(method,"name")) + ""]]));
        };
      };
    }]));
  }
  gSobject.Binder1 = function(map) { gs.passMapToObject(map,this); return this;};
  if (arguments.length==1) {gSobject.Binder1(arguments[0]); }
  
  return gSobject;
};
