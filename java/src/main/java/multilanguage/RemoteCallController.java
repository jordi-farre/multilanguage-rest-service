package multilanguage;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class RemoteCallController {

    private RequestMapper requestMapper;
    private RemoteCallService service;
    private ResponseMapper responseMapper;

    public RemoteCallController(final RequestMapper requestMapper, final RemoteCallService service, final ResponseMapper responseMapper) {
        this.requestMapper = requestMapper;
        this.service = service;
        this.responseMapper = responseMapper;
    }

    @RequestMapping(name =  "/remote/call", method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseDTO remoteCall(RequestDTO requestDTO) {
        Request request = this.requestMapper.from(requestDTO);
        Response response = this.service.remoteCall(request);
        return this.responseMapper.from(response);
    }

}
