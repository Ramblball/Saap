package http.request;

import http.HTTPRequest;
import http.HttpLiterals;
import http.PayLoad;
import http.Request;
import http.payload.CriteriaReq;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.util.Optional;

@Slf4j
public class GetUsersCriteriaRequest extends HTTPRequest implements Request {

    private static final String PATH = "/service/list/%s/%s";

    @Override
    public Optional<String> send(PayLoad object) {
        CriteriaReq payload = (CriteriaReq) object;
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .header(HttpLiterals.SERVICE_HEADER, payload.getServiceToken())
                .GET();
        String uri = String.format(PATH, payload.getCriteria(), payload.getValue());
        log.info(uri + " -> PUT -> " + payload);
        return makeRequest(request, uri);
    }
}
