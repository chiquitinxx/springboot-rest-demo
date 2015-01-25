package robot

import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SecureASTCustomizer

/**
 * JFL 28/10/12
 */
class CheckRobotActions {

    static notAllowedProperties = []
    static notAllowedMethods = ['println','print']
    static notAllowedThis = ['setProperty','getProperty','methodMissing','setMetaClass','getMetaClass']

    static class Es implements SecureASTCustomizer.ExpressionChecker {

        @Override
        boolean isAuthorized(Expression expression) {
            def result = true
            if (expression instanceof VariableExpression) {
                if (expression.name=='metaClass' || expression.name in notAllowedProperties) {
                    result = false
                }
            }
            if (expression instanceof MethodCallExpression) {
                if (expression.objectExpression instanceof ClassExpression) {
                    if (expression.text && expression.text.startsWith('java.lang.System')) {
                        result = false;
                    }
                }
                else if (expression.objectExpression.name == 'super' || expression.methodAsString in notAllowedMethods) {
                    result = false
                }
                else if (expression.objectExpression.name == 'this' && expression.methodAsString in notAllowedThis) {
                    return false
                }
            }
            if (expression instanceof PropertyExpression) {
                if (expression.property.value == 'metaClass' ||
                        (!(expression.objectExpression instanceof MethodCallExpression) && expression.objectExpression.name == 'this')
                        || expression.property.value in notAllowedProperties) {
                    result = false
                }
            }
            if (expression instanceof ConstructorCallExpression) {
                if (expression.type.name.startsWith('java.io')) {
                    result = false
                }
            }

            return result
        }
    }

    static es = new Es()

    static messageError = ''

    static checkActions(String action) {

        def configuration = new CompilerConfiguration()

        def secure = new SecureASTCustomizer()
        secure.with {
            methodDefinitionAllowed = false
            importsWhitelist = []
            starImportsWhitelist = []
            staticStarImportsBlacklist = []
            //tokensBlacklist = ['rController']
        }

        secure.addExpressionCheckers(es)
        //secure.addStatementCheckers(st)

        configuration.addCompilationCustomizers(secure)

        def robot = new Robot('test')
        robot.data = [:]
        robot.rXRadar = [:]

        def binding = new Binding([

                *: Direction.values().collectEntries {
                    [(it.name()):it]
                },
                move:robot.&move,
                getRadar:robot.&getRadar,
                getLife:robot.&getLife,
                getTemperature:robot.&getTemperature,
                getPosition:robot.&getPosition,
                getMaxPosition:robot.&getMaxPosition,
                shoot:robot.&shoot,
                cancelShoot:robot.&cancelShoot,
                canShoot:robot.&canShoot,
                data:robot.data,
                getData:robot.&getData
                /**: notAllowedProperties.collect { it->
                 println('-'+it)
                 [(it):robot."$it"]
                 },
                 *: notAllowedMethods.collect {
                 [(it):robot.&"$it"]
                 },*/
                //rXController:robot.rXController,
                //setrXController:robot.&setrXController
        ])

        robot.properties.each { key,value ->
            if (key.startsWith('rX')) {
                notAllowedProperties << key
                notAllowedMethods << "set&{key}"
                binding.putAt(key,robot."$key")
            }
        }

        def shell = new GroovyShell(binding,configuration)
        def result
        try{
            shell.evaluate(action)
            result = true
        } catch (e) {
            result = false
            messageError = e.message
            //println 'Evaluate error:'+e.message
        }
        return result
    }
}
