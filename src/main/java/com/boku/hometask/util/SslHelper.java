package com.boku.hometask.util;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public final class SslHelper {

    private SslHelper() {
    }

    public static SSLContext makeIgnorantSslContext() throws Exception {
        TrustManager[] trustAllCerts = { new X509TrustManager() {

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }
        }};

        // Get a new SSL context
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(null, trustAllCerts, new java.security.SecureRandom());
        return context;
    }
}
