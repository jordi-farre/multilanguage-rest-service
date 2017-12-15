package multilanguage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RemoteCallControllerShould {

    private RemoteCallController controller;
    @Mock
    private RemoteCallService remoteCallService;

    @Before
    public void initialize() {
        final RequestMapper requestMapper = new RequestMapper();
        final ResponseMapper responseMapper = new ResponseMapper();
        this.controller = new RemoteCallController(requestMapper, this.remoteCallService, responseMapper);
    }

    @Test
    public void execute_remote_call() {
        final ResponseDTO expectedResponse = ResponseDTOObjectMother.create();
        final RequestDTO requestDTO = RequestDTOObjectMother.create();
        final Response response = ResponseObjectMother.create();
        final Request request = RequestObjectMother.create();
        when(this.remoteCallService.remoteCall(any(Request.class))).thenReturn(response);

        final ResponseDTO obtainedResponse = this.controller.remoteCall(requestDTO);

        ArgumentCaptor<Request> requestArgumentCaptor = ArgumentCaptor.forClass(Request.class);
        verify(this.remoteCallService).remoteCall(requestArgumentCaptor.capture());
        assertThat(request, new ReflectionEquals(requestArgumentCaptor.getValue()));
        assertThat(expectedResponse, new ReflectionEquals(obtainedResponse));
    }

}
