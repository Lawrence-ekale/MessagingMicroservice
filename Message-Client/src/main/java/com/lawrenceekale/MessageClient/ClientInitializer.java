package com.lawrenceekale.MessageClient;

/*import javax.net.ssl.SSLException;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import java.io.File;
import io.grpc.*;
import javax.net.ssl.SSLContext;

public class ClientInitializer {

    public ManagedChannel initializeChannel() throws SSLException {
        File certChainFile = new File("/path/to/client.crt");
        File privateKeyFile = new File("/path/to/client.key");

        SSLContext sslContext = GrpcSslContexts.forClient()
                .trustManager(certChainFile)
                .keyManager(certChainFile, privateKeyFile)
                .build();

        return NettyChannelBuilder.forAddress("localhost", 9001)
                .sslContext(sslContext)
                .build();
    }
}*/
