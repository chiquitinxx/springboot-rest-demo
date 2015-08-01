//Grooscript converted file
function FrameworksView() {
  var gSobject = gs.inherit(gs.baseClass,'FrameworksView');
  gSobject.clazz = { name: 'framework.view.FrameworksView', simpleName: 'FrameworksView'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject['updateFrameworks'] = function(frameworks) {
    var html = gs.execStatic(HtmlBuilder,'build', this,[function(it) {
      return gs.mc(this,"ul",[function(it) {
        return gs.mc(frameworks,"each",[function(framework) {
          return gs.mc(this,"a",[gs.map().add("href",gs.gp(framework,"url")), function(it) {
            return gs.mc(this,"li",[gs.map().add("alt",gs.gp(framework,"description")), function(it) {
              gs.mc(this,"div",[gs.map().add("class","logo").add("data-anijs","if: mouseenter, do: flip animated"), function(it) {
                if ((!gs.bool(gs.mc(framework,"hasImage",[]))) && (gs.mc(framework,"isGithub",[]))) {
                  return gs.mc(this,"img",[gs.map().add("src","img/github.png")], gSobject);
                } else {
                  return gs.mc(this,"img",[gs.map().add("src",(gs.mc(framework,"hasImage",[]) ? gs.gp(framework,"urlImage") : "img/nologo.png"))], gSobject);
                };
              }], gSobject);
              return gs.mc(this,"p",[gs.gp(framework,"name")], gSobject);
            }], gSobject);
          }], gSobject);
        }]);
      }], gSobject);
    }]);
    return gs.mc(gSobject,"putHtml",["#listFrameworks", html]);
  }
  gSobject.putHtml = function(selector, html) {
    $(selector).html(html);
        AniJS.run();
  }
  if (arguments.length == 1) {gs.passMapToObject(arguments[0],gSobject);};
  
  return gSobject;
};
function FrameworksModel() {
  var gSobject = gs.inherit(gs.baseClass,'FrameworksModel');
  gSobject.clazz = { name: 'framework.model.FrameworksModel', simpleName: 'FrameworksModel'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.gQuery = GQueryImpl();
  gSobject['loadFrameworks'] = function(onLoaded) {
    return gs.mc(gSobject.gQuery,"doRemoteCall",["/frameworks", "GET", null, onLoaded, null, Framework]);
  }
  if (arguments.length == 1) {gs.passMapToObject(arguments[0],gSobject);};
  
  return gSobject;
};
function Framework() {
  var gSobject = gs.inherit(gs.baseClass,'Framework');
  gSobject.clazz = { name: 'framework.model.Framework', simpleName: 'Framework'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.id = null;
  gSobject.name = null;
  gSobject.url = null;
  gSobject.urlImage = null;
  gSobject.description = null;
  gSobject['hasImage'] = function(it) {
    return (gs.bool(gSobject.urlImage)) && (gs.mc(gs.list([".GIF" , ".PNG" , ".JPG"]),"any",[function(it) {
      return gs.mc(gs.mc(gSobject.urlImage,"toUpperCase",[]),"endsWith",[it]);
    }]));
  }
  gSobject['equals'] = function(other) {
    return (gs.instanceOf(other, "Framework")) && (gs.equals(gs.gp(other,"name"), gs.gp(gs.thisOrObject(this,gSobject),"name")));
  }
  gSobject['isGithub'] = function(it) {
    return gs.mc(gSobject.url,"contains",["github.com"]);
  }
  if (arguments.length == 1) {gs.passMapToObject(arguments[0],gSobject);};
  
  return gSobject;
};
function Presenter() {
  var gSobject = gs.inherit(gs.baseClass,'Presenter');
  gSobject.clazz = { name: 'framework.presenter.Presenter', simpleName: 'Presenter'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.view = null;
  gSobject.model = null;
  gSobject.frameworks = gs.list([]);
  gSobject.updateFrameworksList = function(newFrameworks) {
    gSobject.frameworks = newFrameworks;
    return gs.mc(gSobject.view,"updateFrameworks",[newFrameworks]);
  };
  gSobject['onLoad'] = function(it) {
    gSobject.frameworks = gs.list([]);
    return gs.mc(gSobject.model,"loadFrameworks",[gSobject.updateFrameworksList]);
  }
  gSobject['start'] = function(it) {
    gSobject.view = FrameworksView();
    return gSobject.model = FrameworksModel();
  }
  if (arguments.length == 1) {gs.passMapToObject(arguments[0],gSobject);};
  
  return gSobject;
};
