package com.example.system.applicationevent;

import com.example.coreweb.websocket.WSJson;
import org.springframework.context.ApplicationEvent;

/**
 * @author 朱伟伟
 * @date 2022-09-23 09:20:22
 * @description
 */
public class WSJsonApplicationEvent extends ApplicationEvent {

    private static final long serialVersionUID = 2078838923332335138L;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public WSJsonApplicationEvent(WSJson source) {
        super(source);
    }

    public WSJson getWsJson() {
        return (WSJson) getSource();
    }

}
