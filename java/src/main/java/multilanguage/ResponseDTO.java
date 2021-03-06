package multilanguage;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseDTO {

    private String id;

    private String method;

    private String origin;

    private String data;

}
