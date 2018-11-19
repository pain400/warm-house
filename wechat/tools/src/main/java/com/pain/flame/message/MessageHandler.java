package com.pain.flame.message;

import java.util.Map;

/**
 * Created by Administrator on 2018/9/20.
 */
public interface MessageHandler {
    void handle(UserMessage userMessage, Map<String, Object> context);
}
