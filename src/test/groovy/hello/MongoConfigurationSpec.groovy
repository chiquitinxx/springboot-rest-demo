package hello

import com.mongodb.Mongo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

/**
 * Created by gandrianakis on 8/7/2015.
 */
@SpringApplicationConfiguration(classes = Application.class)
class MongoConfigurationSpec extends Specification {

    @Autowired
    Mongo mongo;

    def "make sure that the properties set in the properties file are set in Mongo"() {
        expect:
            mongo.authority.serverAddresses._host[0] == 'localhost'
            mongo.authority.serverAddresses._port[0] == 27017
    }
}
