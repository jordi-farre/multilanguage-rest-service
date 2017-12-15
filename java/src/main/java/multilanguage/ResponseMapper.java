package multilanguage;

public class ResponseMapper {

    public ResponseDTO from(final Response response) {
        return ResponseDTO.builder()
                .id(response.getId())
                .method(response.getMethod())
                .origin(response.getOrigin())
                .data(response.getData())
                .build();
    }

}
