package com.lawrenceekale.MessageClient.interceptors;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@GrpcGlobalClientInterceptor
public class AddAuthKeysInterceptor implements ClientInterceptor {
    @Value("${myApiKey}")
    private String interServiceKey;

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
        log.info("Client interceptor {}",methodDescriptor.getFullMethodName());
        return new ForwardingClientCall.SimpleForwardingClientCall<>(channel.newCall(methodDescriptor,callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                headers.put(Metadata.Key.of("x-validator",Metadata.ASCII_STRING_MARSHALLER), interServiceKey);
                super.start(responseListener, headers);
            }
        };
    }
}
