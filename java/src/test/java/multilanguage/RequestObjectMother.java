package multilanguage;

public class RequestObjectMother {

    private static final String DATA = "hello";

    public static Request create() {
        return new Request(DATA);
    }

}
