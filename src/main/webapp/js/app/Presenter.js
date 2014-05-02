//Grooscript converted file
function Presenter() {
  var gSobject = gs.inherit(gs.baseClass,'Presenter');
  gSobject.clazz = { name: 'framework.presenter.Presenter', simpleName: 'Presenter'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.view = null;
  gSobject.model = null;
  gSobject.frameworks = gs.list([]);
  gSobject.nameFramework = null;
  gSobject.urlFramework = null;
  gSobject.urlImageFramework = null;
  gSobject.hasEvilChars = function(data) {
    return (gs.bool(data)) && (gs.mc(data,"indexOf",["<"]) >= 0);
  };
  gSobject.updateFrameworksList = function(newFrameworks) {
    gSobject.frameworks = newFrameworks;
    return gs.mc(gSobject.view,"updateFrameworks",[newFrameworks]);
  };
  gSobject.addNewFrameworkToList = function(framework) {
    if (!gs.mc(gSobject.frameworks,"contains",[framework])) {
      gs.mc(gSobject.frameworks,'leftShift', gs.list([framework]));
      return gs.mc(gSobject.view,"updateFrameworks",[gSobject.frameworks]);
    } else {
      return gs.println("Repeated framework!");
    };
  };
  gSobject.and = function(eval, previousResult) {
    var result = (gs.bool(previousResult)) && (gs.mc(gSobject,"evaluation",[eval]));
    return gs.map().add("and",gs.mc(gSobject.and,"rcurry",[result])).add("then",gs.mc(gSobject.then,"rcurry",[result]));
  };
  gSobject.then = function(closure, result) {
    if (gs.bool(result)) {
      (closure.delegate!=undefined?gs.applyDelegate(closure,closure.delegate,[]):gs.executeCall(closure, []));
    };
    return gs.map().add("otherwise",gs.mc(gSobject.otherwise,"rcurry",[result]));
  };
  gSobject.otherwise = function(closure, result) {
    if (!gs.bool(result)) {
      return (closure.delegate!=undefined?gs.applyDelegate(closure,closure.delegate,[]):gs.executeCall(closure, []));
    };
  };
  gSobject.evaluation = function(value) {
    return (gs.instanceOf(value, "Closure") ? (value.delegate!=undefined?gs.applyDelegate(value,value.delegate,[]):gs.executeCall(value, [])) : value);
  };
  gSobject['onLoad'] = function(it) {
    gSobject.frameworks = gs.list([]);
    gSobject.nameFramework = "";
    gSobject.urlFramework = "";
    gSobject.urlImageFramework = "";
    return gs.mc(gSobject.model,"loadFrameworks",[gSobject.updateFrameworksList]);
  }
  gSobject['validUrl'] = function(url) {
    return gs.mc(gs.list(["http://" , "https://"]),"any",[function(it) {
      return (gs.bool(url)) && (gs.mc(url,"startsWith",[it]));
    }]);
  }
  gSobject['buttonAddFrameworkClick'] = function(it) {
    var validationErrors = gs.list([]);
    var insertError = function(errorMessage) {
      return function(it) {
        return gs.mc(validationErrors,'leftShift', gs.list([errorMessage]));
      };
    };
    gs.mc(gs.mc(gSobject,"_if",[!gSobject.nameFramework]),"then",[(insertError.delegate!=undefined?gs.applyDelegate(insertError,insertError.delegate,["Missing name framework"]):gs.executeCall(insertError, ["Missing name framework"]))]);
    gs.mc(gs.mc(gSobject,"_if",[!gSobject.urlFramework]),"then",[(insertError.delegate!=undefined?gs.applyDelegate(insertError,insertError.delegate,["Missing url framework"]):gs.executeCall(insertError, ["Missing url framework"]))]);
    gs.mc(gs.mc(gSobject,"_if",[(gs.bool(gSobject.urlFramework)) && (!gs.mc(gSobject,"validUrl",[gSobject.urlFramework]))]),"then",[(insertError.delegate!=undefined?gs.applyDelegate(insertError,insertError.delegate,["Wrong url framework"]):gs.executeCall(insertError, ["Wrong url framework"]))]);
    gs.mc(gs.mc(gSobject,"_if",[(gs.bool(gSobject.urlImageFramework)) && (!gs.mc(gSobject,"validUrl",[gSobject.urlImageFramework]))]),"then",[(insertError.delegate!=undefined?gs.applyDelegate(insertError,insertError.delegate,["Wrong url image"]):gs.executeCall(insertError, ["Wrong url image"]))]);
    gs.mc(gs.mc(gSobject,"_if",[gs.mc(gs.list([gSobject.nameFramework , gSobject.urlFramework , gSobject.urlImageFramework]),"any",[gSobject.hasEvilChars])]),"then",[(insertError.delegate!=undefined?gs.applyDelegate(insertError,insertError.delegate,["Wrong chars"]):gs.executeCall(insertError, ["Wrong chars"]))]);
    gs.mc(gs.mc(gSobject,"_if",[!gs.mc(validationErrors,"size",[])]),"then",[function(it) {
      return gs.mc(gSobject.model,"addFramework",[gSobject.nameFramework, gSobject.urlFramework, gSobject.urlImageFramework, gSobject.addNewFrameworkToList]);
    }]);
    return gs.mc(gSobject.view,"validationError",[validationErrors]);
  }
  gSobject['_if'] = function(eval) {
    var result = gs.mc(gSobject,"evaluation",[eval]);
    return gs.map().add("and",gs.mc(gSobject.and,"rcurry",[result])).add("then",gs.mc(gSobject.then,"rcurry",[result]));
  }
  if (arguments.length == 1) {gs.passMapToObject(arguments[0],gSobject);};
  
  return gSobject;
};
