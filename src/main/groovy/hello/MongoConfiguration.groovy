package hello

import com.mongodb.Mongo
import com.mongodb.MongoOptions
import org.springframework.context.annotation.Configuration
import org.springframework.data.authentication.UserCredentials
import org.springframework.data.mongodb.config.AbstractMongoConfiguration

/**
 * Created by jorge on 03/05/14.
 */
@Configuration
class MongoConfiguration extends AbstractMongoConfiguration {
    String getDatabaseName() {
        "ecosystem"
    }

    Mongo mongo() throws Exception {
        String host = System.getenv("OPENSHIFT_MONGODB_DB_HOST")
        def port = System.getenv("OPENSHIFT_MONGODB_DB_PORT")
        if (host && port) {
            def db = new Mongo(host, port as int)
            return db
        } else {
            return new Mongo()
        }
    }

    String getMappingBasePackage() {
        "framework.model"
    }

    UserCredentials getUserCredentials() {
        String username = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME")
        String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD")
        if (username && password) {
            return new UserCredentials(username, password)
        } else {
            return null
        }
    }
}
