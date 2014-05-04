package hello

import framework.model.Framework
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Created by jorge on 03/05/14.
 */
@Repository
interface FrameworkRepository extends MongoRepository<Framework, BigInteger> {
}
