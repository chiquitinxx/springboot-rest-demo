yieldUnescaped '<!DOCTYPE html>'
html {
  head {
    title('Counter')
    script(type: 'text/javascript', src: 'js/lib/jquery.js') {}
    script(type: 'text/javascript', src: 'js/lib/grooscript.min.js') {}
    script(type: 'text/javascript', src: 'js/lib/grooscript-tools.js') {}
    script(type: 'text/javascript', src: 'js/app/gstemplates.js') {}
    script(type: 'text/javascript', src: 'js/app/nextNumber.js') {}
  }
  body {
    include template: 'counterBody.tpl'
  }
}