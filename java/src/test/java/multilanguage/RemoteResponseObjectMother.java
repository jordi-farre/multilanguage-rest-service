package multilanguage;

public class RemoteResponseObjectMother {

    private static final String DATA = "hello";
    private static final String METHOD = "POST";
    private static final String ORIGIN = "127.0.0.1";

    public static RemoteResponse create() {
        return RemoteResponse.builder()
                .method(METHOD)
                .origin(ORIGIN)
                .data(DATA)
                .build();
    }

}
