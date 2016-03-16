package sample.auth;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.trustStore", "keystore-ldap-prod.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "changeit");

        SpringApplication.run(AuthApplication.class, args);
    }
}
