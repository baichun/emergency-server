package com.comtom.bc.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class JaxbConfig {

	@Bean
    public Jaxb2Marshaller jaxbMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setMtomEnabled(true);
//        marshaller.setContextPath("hu.vanio.springwsmtom.wstypes");
        marshaller.setPackagesToScan("com.comtom.bc.server.rest.dto");
        return marshaller;
    }
}
