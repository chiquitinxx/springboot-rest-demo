package hello

import framework.model.Framework
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/frameworks")
public class FrameworksController {

    @Autowired
    FrameworkRepository repository

    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody List<Framework> allFrameworks() {
        initializeListIfEmpty()
        repository.findAll().sort { it.name }
    }

    private initializeListIfEmpty() {
        if (repository.count() < initialFrameworks.size()) {
            repository.deleteAll()
            initialFrameworks.each { props ->
                def framework = new Framework()
                props.each { key, value ->
                    framework."$key" = value
                }
                repository.save(framework)
            }
        }
    }
    private initialFrameworks = [
            [
                    name: 'GVM',
                    description: 'Groovy environment manager',
                    url: 'http://gvmtool.net',
                    urlImage: 'img/gvm.png'
            ],
            [
                    name: 'Grails',
                    description: 'Web application framework',
                    url: 'http://grails.org',
                    urlImage: 'img/grails.png'
            ],
            [
                    name: 'Spring',
                    description: 'Spring framework',
                    url: 'http://spring.io',
                    urlImage: 'img/spring.png'
            ],
            [
                    name: 'Griffon',
                    description: 'Desktop application framework',
                    url: 'http://new.griffon-framework.org',
                    urlImage: 'img/griffon.png'
            ],
            [
                    name: 'Spock',
                    description: 'Testing framework',
                    url: 'http://spockframework.org',
            ],
            [
                    name: 'Groovy-Stream',
                    description: 'Lazy groovy generator',
                    url: 'https://github.com/timyates/groovy-stream',
            ],
            [
                    name: 'Gaelyk',
                    description: 'Lightweight toolkit for Google App Engine',
                    url: 'http://gaelyk.appspot.com',
                    urlImage: 'img/gaelyk.jpg'
            ],
            [
                    name: 'Codenarc',
                    description: 'Code Static Analysis',
                    url: 'http://codenarc.sourceforge.net',
                    urlImage: 'img/codenarc.png'
            ],
            [
                    name: 'GPars',
                    description: 'Groovy Parallel Systems',
                    url: 'http://gpars.codehaus.org',
                    urlImage: 'img/gpars.png'
            ],
            [
                    name: 'Ratpack',
                    description: 'Netty based web framework',
                    url: 'http://www.ratpack.io',
                    urlImage: 'img/ratpack.png'
            ],
            [
                    name: 'GBench',
                    description: 'Groovy benchmarking module',
                    url: 'https://code.google.com/p/gbench',
                    urlImage: 'img/gperfutils.png'
            ],
            [
                    name: 'GroovyServ',
                    description: 'Quick start groovy scripts',
                    url: 'http://kobo.github.io/groovyserv',
                    urlImage: 'img/groovyserv.png'
            ],
            [
                    name: 'Geb',
                    description: 'Functional testing',
                    url: 'http://www.gebish.org',
            ],
            [
                    name: 'Lazybones',
                    description: 'Starting project templates',
                    url: 'https://github.com/pledbrook/lazybones',
                    urlImage: 'img/lazybones.png'
            ],
            [
                    name: 'Gradle',
                    description: 'Building tool',
                    url: 'https://github.com/pledbrook/lazybones',
                    urlImage: 'img/gradle.png'
            ],
            [
                    name: 'Asgard',
                    description: 'Deploy in AWS',
                    url: 'http://netflix.github.io/asgard',
                    urlImage: 'img/asgard.png'
            ],
            [
                    name: 'Grain',
                    description: 'Static website building',
                    url: 'http://sysgears.com/grain',
                    urlImage: 'img/grain.png'
            ],
            [
                    name: 'Grooscript',
                    description: 'Groovy to javascript transpiler',
                    url: 'http://grooscript.org',
                    urlImage: 'img/grooscript.png'
            ],
            [
                    name: 'Gaiden',
                    description: 'Easy to create documentation with Markdown',
                    url: 'https://github.com/kobo/gaiden',
                    urlImage: 'img/gaiden.png'
            ],
            [
                    name: 'Caelyf',
                    description: 'Lightweight framework for cloudfoundry',
                    url: 'https://github.com/caelyf/caelyf',
                    urlImage: 'img/caelyf.png'
            ],
            [
                    name: 'Glide',
                    description: 'Lightweight framework for GAE',
                    url: 'http://glide-gae.appspot.com',
                    urlImage: 'img/glide.png'
            ],
            [
                    name: 'Easyb',
                    description: 'BDD Testing',
                    url: 'http://easyb.org',
            ],
            [
                    name: 'GContracts',
                    description: 'Design by contract',
                    url: 'http://gcontracts.org',
            ],
            [
                    name: 'GroovyFX',
                    description: 'Dsl for JavaFx',
                    url: 'http://groovyfx.org',
                    urlImage: 'img/GroovyFX.png'
            ],
            [
                    name: 'Crash',
                    description: 'Embeddable shell for JVM',
                    url: 'http://www.crashub.org',
            ],
    ]
}
