package com.pain.flame.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/20.
 */
public class MessageRouter {

    private List<Rule> rules = new ArrayList<Rule>();

    public Rule start() {
        return new Rule(this);
    }

    public void route(UserMessage userMessage) {
        for (Rule rule : rules) {
            if (!rule.service(userMessage)) {
                break;
            }
        }
    }

    public static class Rule {
        private final MessageRouter router;
        private String msgType;
        private String content;
        private boolean forward = false;

        private List<MessageHandler> handlers = new ArrayList<MessageHandler>();
        private List<MessageInterceptor> interceptors = new ArrayList<MessageInterceptor>();

        Rule(MessageRouter router) {
            this.router = router;
        }

        public Rule msgType(String msgType) {
            this.msgType = msgType;
            return this;
        }

        public Rule content(String content) {
            this.content = content;
            return this;
        }

        public Rule forward(boolean forward) {
            this.forward = forward;
            return this;
        }

        public Rule interceptor(MessageInterceptor interceptor, MessageInterceptor... otherInterceptors) {
            interceptors.add(interceptor);

            if (otherInterceptors != null && otherInterceptors.length > 0) {
                for (MessageInterceptor messageInterceptor : otherInterceptors) {
                    interceptors.add(messageInterceptor);
                }
            }

            return this;
        }

        public Rule handler(MessageHandler handler, MessageHandler... otherHandlers) {
            handlers.add(handler);

            if (otherHandlers != null && otherHandlers.length > 0) {
                for (MessageHandler messageHandler : otherHandlers) {
                    handlers.add(messageHandler);
                }
            }

            return this;
        }

        public MessageRouter end() {
            this.router.rules.add(this);
            return router;
        }

        public boolean service(UserMessage userMessage) {
            Map<String, Object> context = new HashMap<String, Object>();

            for (MessageInterceptor interceptor : interceptors) {
                if (!interceptor.intercept(userMessage, context)) {
                    return forward;
                }
            }

            for (MessageHandler handler : handlers) {
                handler.handle(userMessage, context);
            }

            return forward;
        }

    }
}
