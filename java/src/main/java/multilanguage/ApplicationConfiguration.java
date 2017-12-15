package multilanguage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public RequestMapper requestMapper() {
        return new RequestMapper();
    }

    @Bean
    public ResponseMapper responseMapper() {
        return new ResponseMapper();
    }

    @Bean
    public RemoteCallClient remoteCallClient() {
        return new RemoteCallClient();
    }

    @Bean
    public RemoteCallRepository remoteCallRepository() {
        return new RemoteCallRepository();
    }

    @Bean
    public RemoteCallService remoteCallService() {
        return new RemoteCallService(remoteCallClient(), remoteCallRepository());
    }

}
