package com.example.coreweb.websocket;

import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketExtension;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * @author 朱伟伟
 * @date 2022-09-23 15:59:51
 * @description
 */
public class WebSocketSessionDecorator implements WebSocketSession {

    private final WebSocketSession delegate;

    /**
     * 当前WebSocketSession是否认证
     */
    private boolean authenticated = false;

    /**
     * 数据标识
     */
    private String flagId;

    public WebSocketSessionDecorator(WebSocketSession delegate) {
        this.delegate = delegate;
    }

    public WebSocketSession getDelegate() {
        return delegate;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String getFlagId() {
        return flagId;
    }

    public void setFlagId(String flagId) {
        this.flagId = flagId;
    }

    @Override
    public String getId() {
        return getDelegate().getId();
    }

    @Override
    public URI getUri() {
        return getDelegate().getUri();
    }

    @Override
    public HttpHeaders getHandshakeHeaders() {
        return getDelegate().getHandshakeHeaders();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return getDelegate().getAttributes();
    }

    @Override
    public Principal getPrincipal() {
        return getDelegate().getPrincipal();
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return getDelegate().getLocalAddress();
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return getDelegate().getRemoteAddress();
    }

    @Override
    public String getAcceptedProtocol() {
        return getDelegate().getAcceptedProtocol();
    }

    @Override
    public void setTextMessageSizeLimit(int messageSizeLimit) {
        getDelegate().setTextMessageSizeLimit(messageSizeLimit);
    }

    @Override
    public int getTextMessageSizeLimit() {
        return getDelegate().getTextMessageSizeLimit();
    }

    @Override
    public void setBinaryMessageSizeLimit(int messageSizeLimit) {
        getDelegate().setBinaryMessageSizeLimit(messageSizeLimit);
    }

    @Override
    public int getBinaryMessageSizeLimit() {
        return getDelegate().getBinaryMessageSizeLimit();
    }

    @Override
    public List<WebSocketExtension> getExtensions() {
        return getDelegate().getExtensions();
    }

    @Override
    public void sendMessage(WebSocketMessage<?> message) throws IOException {
        getDelegate().sendMessage(message);
    }

    @Override
    public boolean isOpen() {
        return getDelegate().isOpen();
    }

    @Override
    public void close() throws IOException {
        getDelegate().close();
    }

    @Override
    public void close(CloseStatus status) throws IOException {
        getDelegate().close(status);
    }
}
