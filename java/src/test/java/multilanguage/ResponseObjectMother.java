package multilanguage;

public class ResponseObjectMother {

    private static final String DATA = "hello";
    private static final String ID = "067e6162-3b6f-4ae2-a171-2470b63dff00";
    private static final String METHOD = "POST";
    private static final String ORIGIN = "127.0.0.1";

    public static Response create() {
        return Response.builder()
                .id(ID)
                .method(METHOD)
                .origin(ORIGIN)
                .data(DATA)
                .build();
    }

}
