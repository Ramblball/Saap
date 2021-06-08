package http;

import http.request.*;

public enum RequestFactory {
    GET_FRIEND(new GetFriend()),
    GET_PERMISSIONS(new GetPermissions()),
    GET_SERVICE_USERS(new GetServiceUsers()),
    GET_USER(new GetUser()),
    POST_LOGIN(new PostLogin()),
    POST_REGISTER(new PostRegister()),
    PUT_ADD_PERMISSION(new PutAddPermission());

    private final Request<?> request;

    RequestFactory(Request<?> request) {
        this.request = request;
    }

    public <T extends Dto> Request<T> getRequest() {
        return (Request<T>) request;
    }
}
