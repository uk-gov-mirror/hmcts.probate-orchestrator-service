package uk.gov.hmcts.probate.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

public class BusinessServiceConfiguration {

    static final String SERVICE_AUTHORIZATION = "ServiceAuthorization";

    @Bean
    @Primary
    Decoder feignDecoder(ObjectMapper objectMapper) {
        return new JacksonDecoder(objectMapper);
    }
}
