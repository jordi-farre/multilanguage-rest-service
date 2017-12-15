package multilanguage;

public class RemoteCallService {

    private RemoteCallClient client;
    private RemoteCallRepository repository;

    public RemoteCallService(final RemoteCallClient client, final RemoteCallRepository repository) {
        this.client = client;
        this.repository = repository;
    }

    public Response remoteCall(final Request request) {
        RemoteResponse response = this.client.execute(request);
        return this.repository.create(response);
    }

}
