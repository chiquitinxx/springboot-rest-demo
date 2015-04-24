//Grooscript converted file
function MyIf() {
  var gSobject = gs.inherit(gs.baseClass,'MyIf');
  gSobject.clazz = { name: 'framework.presenter.MyIf', simpleName: 'MyIf'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.and = function(eval, previousResult) {
    var result = (gs.bool(previousResult)) && (gs.mc(gSobject,"evaluation",[eval]));
    return gs.map().add("and",gs.mc(gSobject.and,"rcurry",[result])).add("then",gs.mc(gSobject.then,"rcurry",[result]));
  };
  gSobject.then = function(closure, result) {
    if (gs.bool(result)) {
      (closure.delegate!=undefined?gs.applyDelegate(closure,closure.delegate,[]):gs.execCall(closure, this, []));
    };
    return gs.map().add("otherwise",gs.mc(gSobject.otherwise,"rcurry",[result]));
  };
  gSobject.otherwise = function(closure, result) {
    if (!gs.bool(result)) {
      return (closure.delegate!=undefined?gs.applyDelegate(closure,closure.delegate,[]):gs.execCall(closure, this, []));
    };
  };
  gSobject.evaluation = function(value) {
    return (gs.instanceOf(value, "Closure") ? (value.delegate!=undefined?gs.applyDelegate(value,value.delegate,[]):gs.execCall(value, this, [])) : value);
  };
  gSobject['_if'] = function(eval) {
    var result = gs.mc(gSobject,"evaluation",[eval]);
    return gs.map().add("and",gs.mc(gSobject.and,"rcurry",[result])).add("then",gs.mc(gSobject.then,"rcurry",[result]));
  }
  if (arguments.length == 1) {gs.passMapToObject(arguments[0],gSobject);};
  
  return gSobject;
};
