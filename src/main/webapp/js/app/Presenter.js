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
    return (gs.bool(data)) && (gs.mc(data,"indexOf",gs.list(["<"])) >= 0);
  };
  gSobject.updateFrameworksList = function(newFrameworks) {
    gSobject.frameworks = newFrameworks;
    return gs.mc(gSobject.view,"updateFrameworks",gs.list([newFrameworks]));
  };
  gSobject.addNewFrameworkToList = function(framework) {
    gs.mc(gSobject.frameworks,'leftShift', gs.list([framework]));
    return gs.mc(gSobject.view,"updateFrameworks",gs.list([gSobject.frameworks]));
  };
  gSobject.and = function(eval, previousResult) {
    var result = (gs.bool(previousResult)) && (gs.mc(gSobject,"evaluation",gs.list([eval])));
    return gs.map().add("and",gs.mc(gSobject.and,"rcurry",gs.list([result]))).add("then",gs.mc(gSobject.then,"rcurry",gs.list([result])));
  };
  gSobject.then = function(closure, result) {
    if (gs.bool(result)) {
      (closure.delegate!=undefined?gs.applyDelegate(closure,closure.delegate,[]):gs.executeCall(closure, gs.list([])));
    };
    return gs.map().add("otherwise",gs.mc(gSobject.otherwise,"rcurry",gs.list([result])));
  };
  gSobject.otherwise = function(closure, result) {
    if (!gs.bool(result)) {
      return (closure.delegate!=undefined?gs.applyDelegate(closure,closure.delegate,[]):gs.executeCall(closure, gs.list([])));
    };
  };
  gSobject.evaluation = function(value) {
    return (gs.instanceOf(value, "Closure") ? (value.delegate!=undefined?gs.applyDelegate(value,value.delegate,[]):gs.executeCall(value, gs.list([]))) : value);
  };
  gSobject['onLoad'] = function(it) {
    gSobject.frameworks = gs.list([]);
    gSobject.nameFramework = "";
    gSobject.urlFramework = "";
    gSobject.urlImageFramework = "";
    return gs.mc(gSobject.model,"loadFrameworks",gs.list([gSobject.updateFrameworksList]));
  }
  gSobject['validUrl'] = function(url) {
    return gs.mc(gs.list(["http://" , "https://"]),"any",gs.list([function(it) {
      return (gs.bool(url)) && (gs.mc(url,"startsWith",gs.list([it])));
    }]));
  }
  gSobject['buttonAddFrameworkClick'] = function(it) {
    var validationErrors = gs.list([]);
    var insertError = function(errorMessage) {
      return function(it) {
        return gs.mc(validationErrors,'leftShift', gs.list([errorMessage]));
      };
    };
    gs.mc(gs.mc(gSobject,"_if",gs.list([!gSobject.nameFramework])),"then",gs.list([(insertError.delegate!=undefined?gs.applyDelegate(insertError,insertError.delegate,["Missing name framework"]):gs.executeCall(insertError, gs.list(["Missing name framework"])))]));
    gs.mc(gs.mc(gSobject,"_if",gs.list([!gSobject.urlFramework])),"then",gs.list([(insertError.delegate!=undefined?gs.applyDelegate(insertError,insertError.delegate,["Missing url framework"]):gs.executeCall(insertError, gs.list(["Missing url framework"])))]));
    gs.mc(gs.mc(gSobject,"_if",gs.list([(gs.bool(gSobject.urlFramework)) && (!gs.mc(gSobject,"validUrl",gs.list([gSobject.urlFramework])))])),"then",gs.list([(insertError.delegate!=undefined?gs.applyDelegate(insertError,insertError.delegate,["Wrong url framework"]):gs.executeCall(insertError, gs.list(["Wrong url framework"])))]));
    gs.mc(gs.mc(gSobject,"_if",gs.list([(gs.bool(gSobject.urlImageFramework)) && (!gs.mc(gSobject,"validUrl",gs.list([gSobject.urlImageFramework])))])),"then",gs.list([(insertError.delegate!=undefined?gs.applyDelegate(insertError,insertError.delegate,["Wrong url image"]):gs.executeCall(insertError, gs.list(["Wrong url image"])))]));
    gs.mc(gs.mc(gSobject,"_if",gs.list([gs.mc(gs.list([gSobject.nameFramework , gSobject.urlFramework , gSobject.urlImageFramework]),"any",gs.list([gSobject.hasEvilChars]))])),"then",gs.list([(insertError.delegate!=undefined?gs.applyDelegate(insertError,insertError.delegate,["Wrong chars"]):gs.executeCall(insertError, gs.list(["Wrong chars"])))]));
    gs.mc(gs.mc(gSobject,"_if",gs.list([!gs.mc(validationErrors,"size",gs.list([]))])),"then",gs.list([function(it) {
      return gs.mc(gSobject.model,"addFramework",gs.list([gSobject.nameFramework, gSobject.urlFramework, gSobject.urlImageFramework, gSobject.addNewFrameworkToList]));
    }]));
    return gs.mc(gSobject.view,"validationError",gs.list([validationErrors]));
  }
  gSobject['_if'] = function(eval) {
    var result = gs.mc(gSobject,"evaluation",gs.list([eval]));
    return gs.map().add("and",gs.mc(gSobject.and,"rcurry",gs.list([result]))).add("then",gs.mc(gSobject.then,"rcurry",gs.list([result])));
  }
  if (arguments.length == 1) {gs.passMapToObject(arguments[0],gSobject);};
  
  return gSobject;
};
