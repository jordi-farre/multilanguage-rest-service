package multilanguage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RemoteCallServiceShould {

    @Mock
    private RemoteCallClient remoteCallClient;
    @Mock
    private RemoteCallRepository remoteCallRepository;
    private RemoteCallService service;

    @Before
    public void initialize() {
        this.service = new RemoteCallService(this.remoteCallClient, this.remoteCallRepository);
    }

    @Test
    public void execute_remote_call_and_store_result() {
        final Response response = ResponseObjectMother.create();
        final Request request = RequestObjectMother.create();
        final RemoteResponse remoteResponse = RemoteResponseObjectMother.create();
        when(this.remoteCallClient.execute(any(Request.class))).thenReturn(remoteResponse);
        when(this.remoteCallRepository.create(remoteResponse)).thenReturn(response);

        Response serviceResponse = this.service.remoteCall(request);

        ArgumentCaptor<Request> requestArgumentCaptor = ArgumentCaptor.forClass(Request.class);
        verify(this.remoteCallClient).execute(requestArgumentCaptor.capture());
        assertThat(request, new ReflectionEquals(requestArgumentCaptor.getValue()));
        ArgumentCaptor<RemoteResponse> responseArgumentCaptor = ArgumentCaptor.forClass(RemoteResponse.class);
        verify(this.remoteCallRepository).create(responseArgumentCaptor.capture());
        assertThat(remoteResponse, new ReflectionEquals(responseArgumentCaptor.getValue()));
        assertThat(response, new ReflectionEquals(serviceResponse));
    }

}
