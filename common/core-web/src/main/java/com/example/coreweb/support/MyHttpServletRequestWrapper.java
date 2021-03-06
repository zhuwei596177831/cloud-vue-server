package com.example.coreweb.support;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author 朱伟伟
 * @date 2020-12-23 16:05:48
 * @description 重写HttpServletRequestWrapper 使body中数据可以重复读取
 * @see org.springframework.web.bind.annotation.RequestBody
 */
public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final Logger logger = LogManager.getLogger(getClass());
    private final String body;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public MyHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        if (isApplicationJson()) {
            this.body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        } else {
            this.body = URLDecoder.decode(getParameterValue(), getCharacterEncoding());
        }
        if (StringUtils.hasText(body)) {
            logger.info("MyHttpServletRequestWrapper body:\n{}", body);
        }
    }

    private boolean isApplicationJson() {
        String contentType = getContentType();
        return StringUtils.startsWithIgnoreCase(contentType, MediaType.APPLICATION_JSON_VALUE);
    }


    private boolean isFormPost() {
        String contentType = getContentType();
        return (contentType != null &&
                (contentType.contains(MediaType.APPLICATION_FORM_URLENCODED_VALUE) || StringUtils.startsWithIgnoreCase(contentType, "multipart/")) &&
                (HttpMethod.POST.matches(getMethod()) || HttpMethod.PUT.matches(getMethod()))
        );
    }

    @Override
    public String getCharacterEncoding() {
        String characterEncoding = super.getCharacterEncoding();
        return characterEncoding == null ? StandardCharsets.UTF_8.displayName() : characterEncoding;
    }

    private String getParameterValue() {
        StringBuilder stringBuilder;
        stringBuilder = new StringBuilder();
        Map<String, String[]> form = super.getParameterMap();
        if (form != null && !form.isEmpty()) {
            for (Iterator<String> nameIterator = form.keySet().iterator(); nameIterator.hasNext(); ) {
                String name = nameIterator.next();
                List<String> values = Arrays.asList(form.get(name));
                for (Iterator<String> valueIterator = values.iterator(); valueIterator.hasNext(); ) {
                    String value = valueIterator.next();
                    stringBuilder.append(name);
                    if (value != null) {
                        stringBuilder.append('=');
                        stringBuilder.append(value);
                        if (valueIterator.hasNext()) {
                            stringBuilder.append('&');
                        }
                    }
                }
                if (nameIterator.hasNext()) {
                    stringBuilder.append('&');
                }
            }
        }
        return stringBuilder.toString();
    }

    public String getBody() {
        return body;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

}
