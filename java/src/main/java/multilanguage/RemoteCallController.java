package multilanguage;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class RemoteCallController {

    @RequestMapping(name =  "/remote/call", method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseDTO remoteCall(RequestDTO requestDTO) {
        throw new NotImplementedException();
    }

}
