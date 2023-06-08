package utils;

import javax.net.ssl.SSLException;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContextBuilder;
import java.io.File;

public class TLSUtils {

    public static SslContext createClientSslContext() throws SSLException {
        File clientCertFile = new File("../../../../certs/client.crt");
        File clientKeyFile = new File("../../../../certs/client.key");

        return SslContextBuilder.forClient()
                .trustManager(clientCertFile)
                .keyManager(clientCertFile, clientKeyFile)
                .build();
    }
}
