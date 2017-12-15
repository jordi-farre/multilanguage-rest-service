package multilanguage;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RemoteResponse {

    private String method;

    private String origin;

    private String data;

}
