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
