package multilanguage;

public class RequestMapper {

    public Request from(final RequestDTO requestDTO) {
        return new Request(requestDTO.getRequest());
    }

}
