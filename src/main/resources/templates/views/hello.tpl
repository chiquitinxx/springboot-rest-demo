yieldUnescaped '<!DOCTYPE html>'
html {
  head {
    title('Hello')
  }
  body {
    p "Hello, groovy version is $groovyVersion!"

    include template: 'sign.tpl'
  }
}