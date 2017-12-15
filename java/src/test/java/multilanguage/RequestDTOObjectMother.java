package multilanguage;

public class RequestDTOObjectMother {

    private static final String DATA = "hello";

    public static RequestDTO create() {
        final RequestDTO requestDTO = new RequestDTO();
        requestDTO.setRequest(DATA);
        return requestDTO;
    }

}
