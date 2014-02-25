package hello

import groovy.transform.Immutable

/**
 * User: jorgefrancoleza
 * Date: 23/02/14
 */
@Immutable
class Greeting {
    long id
    String content
}
