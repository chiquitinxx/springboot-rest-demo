import org.grooscript.templates.Templates

/**
 * User: jorgefrancoleza
 * Date: 13/10/14
 */

def nextNumber = { number ->
    $('body').html(Templates.applyTemplate('counterBody.tpl', [number: number + 1]))
}
