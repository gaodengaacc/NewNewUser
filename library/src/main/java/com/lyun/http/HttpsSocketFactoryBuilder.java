package com.lyun.http;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by ZHAOWEIWEI on 2017/2/15.
 */

public class HttpsSocketFactoryBuilder {

    private static final String KEY_STORE_TYPE_BKS = "bks";//证书类型
    private static final String KEY_STORE_TYPE_P12 = "PKCS12";;//证书类型

    private TrustManagerFactory mTrustManagerFactory;
    private KeyManagerFactory mKeyManagerFactory;

    public HttpsSocketFactoryBuilder trust(InputStream trust, String password) {
        KeyStore keyStore = readKeystore(trust, password, KEY_STORE_TYPE_P12);
        try {
            if (keyStore != null) {
                mTrustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                mTrustManagerFactory.init(keyStore);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } finally {
            try {
                trust.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public HttpsSocketFactoryBuilder client(InputStream client, String password) {
        KeyStore keyStore = readKeystore(client, password, KEY_STORE_TYPE_BKS);
        try {
            if (keyStore != null) {
                mKeyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                mKeyManagerFactory.init(keyStore, password.toCharArray());
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public SSLSocketFactory build() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSLv3");
            KeyManager keyManagers[] = null;
            if (mKeyManagerFactory != null) {
                keyManagers = mKeyManagerFactory.getKeyManagers();
            }
            TrustManager trustManagers[] = null;
            if (mTrustManagerFactory != null) {
                trustManagers = mTrustManagerFactory.getTrustManagers();
            }
            sslContext.init(keyManagers, trustManagers, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    private KeyStore readKeystore(InputStream client, String password, String storeType) {
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(storeType);
            keyStore.load(client, password.toCharArray());
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keyStore;
    }

}
