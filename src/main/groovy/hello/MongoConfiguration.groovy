package hello

import com.mongodb.Mongo
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoConfiguration

/**
 * Created by jorge on 03/05/14.
 */
@Configuration
class MongoConfiguration extends AbstractMongoConfiguration {
    String getDatabaseName() {
        "framework"
    }

    Mongo mongo() throws Exception {
        new Mongo()
    }

    String getMappingBasePackage() {
        "framework.model"
    }
}
