package pl.eonsoft.ldap.example

import groovy.util.logging.Slf4j
import org.apache.directory.api.ldap.model.cursor.EntryCursor
import org.apache.directory.api.ldap.model.message.SearchScope
import org.apache.directory.ldap.client.api.LdapNetworkConnection

/**
 * More info: <a href="http://directory.apache.org/api/">Apache Directory LDAP API</a>
 */
@Slf4j
class Main {
    static void main(def args) {
        File configFile = new File("config.groovy")
        if (!configFile.exists()) {
            log.error("config.groovy not find in current directory. Did you copy config-sample.groovy to config.groovy?")
            return;
        }
        def config = new ConfigSlurper().parse(configFile.toURI().toURL())
        new LdapNetworkConnection(config.hostname, config.port).withCloseable { conn ->

            log.debug "connecting..."
            conn.bind(config.user, config.password)
            log.debug "connected"

            log.debug "searching..."
            EntryCursor cursor = conn.search(config.dn, '(objectClass=person)', SearchScope.ONELEVEL)
            cursor.each {
                def displayName = it.displayName.get()
                def mail = it.mail.get()
                log.info "$displayName $mail"
            }
            log.debug "done."
        }
    }
}
