function FrameworksView() {
  var gSobject = gs.inherit(gs.baseClass,'FrameworksView');
  gSobject.clazz = { name: 'framework.view.FrameworksView', simpleName: 'FrameworksView'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.setHtml = function(selector, html) {
    $(selector).html(html);
  }
  gSobject['updateFrameworks'] = function(frameworks) {
    var html = gs.mc(Builder,"build",gs.list([function(it) {
      return gs.mc(this,"ul",gs.list([function(it) {
        return gs.mc(frameworks,"each",gs.list([function(framework) {
          return gs.mc(this,"li",gs.list([function(it) {
            gs.mc(this,"div",gs.list([gs.map().add("class","logo"), function(it) {
              return gs.mc(this,"img",gs.list([gs.map().add("src",(gs.mc(framework,"hasImage",gs.list([])) ? gs.gp(framework,"urlImage") : "img/nologo.png"))]));
            }]));
            return gs.mc(this,"a",gs.list([gs.map().add("href",gs.gp(framework,"url")), gs.gp(framework,"name")]));
          }]));
        }]));
      }]));
    }]));
    return gs.mc(gSobject,"setHtml",gs.list(["#listFrameworks", html]));
  }
  gSobject['validationError'] = function(message) {
    return gs.mc(gSobject,"setHtml",gs.list(["#validationError", message]));
  }
  gSobject.FrameworksView1 = function(map) { gs.passMapToObject(map,this); return this;};
  if (arguments.length==1) {gSobject.FrameworksView1(arguments[0]); }
  
  return gSobject;
};
