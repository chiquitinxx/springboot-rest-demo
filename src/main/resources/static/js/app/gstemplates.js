function Templates() {
  var gSobject = gs.inherit(gs.baseClass,'Templates');
  gSobject.clazz = { name: 'org.grooscript.gradle.template.Templates', simpleName: 'Templates'};
  gSobject.clazz.superclass = { name: 'java.lang.Object', simpleName: 'Object'};
  gSobject.__defineGetter__('templates', function(){ return Templates.templates; });
  gSobject.__defineSetter__('templates', function(gSval){ Templates.templates = gSval; });
  gSobject.applyTemplate = function(x0,x1) { return Templates.applyTemplate(x0,x1); }
  if (arguments.length == 1) {gs.passMapToObject(arguments[0],gSobject);};
  
  return gSobject;
};
Templates.applyTemplate = function(name, model) {
  if (model === undefined) model = gs.map();
  var cl = Templates.templates [ name];
  gs.sp(cl,"delegate",model);
  return (cl.delegate!=undefined?gs.applyDelegate(cl,cl.delegate,[model]):gs.execCall(cl, this, [model]));
}
Templates.templates = gs.map().add("counterBody.tpl",function(model) {
  if (model === undefined) model = gs.map();
  return gs.mc(HtmlBuilder,"build",[function(it) {
    gs.mc(Templates,"input",[gs.map().add("type","button").add("value","Next!").add("onclick","nextNumber(" + (gs.gp(model,"number")) + ")")]);
    return gs.mc(Templates,"div",[gs.map().add("id","numbers"), function(it) {
      return gs.mc(Templates,"h1",["" + (gs.gp(model,"number")) + ""]);
    }]);
  }]);
});
