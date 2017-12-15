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

    private static final String DATA = "hello";
    private static final String ID = "067e6162-3b6f-4ae2-a171-2470b63dff00";
    private static final String METHOD = "POST";
    private static final String ORIGIN = "127.0.0.1";
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
        final ResponseDTO expectedResponse = createResponseDTO();
        final RequestDTO requestDTO = createRequestDTO();
        final Response response = createResponse();
        final Request request = new Request(DATA);
        when(this.remoteCallService.remoteCall(any(Request.class))).thenReturn(response);

        final ResponseDTO obtainedResponse = this.controller.remoteCall(requestDTO);

        ArgumentCaptor<Request> requestArgumentCaptor = ArgumentCaptor.forClass(Request.class);
        verify(this.remoteCallService).remoteCall(requestArgumentCaptor.capture());
        assertThat(request, new ReflectionEquals(requestArgumentCaptor.getValue()));
        assertThat(expectedResponse, new ReflectionEquals(obtainedResponse));
    }

    private Response createResponse() {
        return Response.builder()
                .id(ID)
                .method(METHOD)
                .origin(ORIGIN)
                .data(DATA)
                .build();
    }

    private RequestDTO createRequestDTO() {
        final RequestDTO requestDTO = new RequestDTO();
        requestDTO.setRequest(DATA);
        return requestDTO;
    }

    private ResponseDTO createResponseDTO() {
        return ResponseDTO.builder()
                .id(ID)
                .method(METHOD)
                .origin(ORIGIN)
                .data(DATA)
                .build();
    }

}
