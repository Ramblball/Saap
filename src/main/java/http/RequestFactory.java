package http;

import http.request.*;

public enum RequestFactory {
    GET_FRIEND(new GetFriend(new HttpSender())),
    GET_PERMISSIONS(new GetPermissions(new HttpSender())),
    GET_SERVICE_USERS(new GetServiceUsers(new HttpSender())),
    GET_USER(new GetUser(new HttpSender())),
    POST_LOGIN(new PostLogin(new HttpSender())),
    POST_REGISTER(new PostRegister(new HttpSender())),
    PUT_ADD_PERMISSION(new PutAddPermission(new HttpSender()));

    private final Request<?> request;

    RequestFactory(Request<?> request) {
        this.request = request;
    }

    public <T extends Dto> Request<T> getRequest() {
        return (Request<T>) request;
    }
}
