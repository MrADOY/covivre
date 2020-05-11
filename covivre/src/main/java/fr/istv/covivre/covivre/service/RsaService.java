package fr.istv.covivre.covivre.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import java.io.InputStream;
import java.security.*;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;


@Service
@Scope("singleton")
public class RsaService {

    @Value("${server.ssl.key-store-type}")
    private String keyStoreType;

    private String keyStorePath = "/keystore/covivre.p12";

    @Value("${server.ssl.key-store-password}")
    private String keyStorePassword;

    @Value("${server.ssl.key-alias}")
    private String keyAlias;

    private String algoTypeInstance = "RSA";

    private KeyPair keyPair;

    @PostConstruct
    private void postConstruct() throws Exception{
        InputStream ins = RsaService.class.getResourceAsStream(keyStorePath);
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(ins, keyStorePassword.toCharArray());
        KeyStore.PasswordProtection keyPassword =
                new KeyStore.PasswordProtection(keyStorePassword.toCharArray());
        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(keyAlias, keyPassword);
        java.security.cert.Certificate cert = keyStore.getCertificate(keyAlias);
        PublicKey publicKey = cert.getPublicKey();
        PrivateKey privateKey = privateKeyEntry.getPrivateKey();
        this.keyPair =  new KeyPair(publicKey, privateKey);
    }

    public String encrypt(String plainText) throws Exception {
        Cipher encryptCipher = Cipher.getInstance(algoTypeInstance);
        encryptCipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(UTF_8));
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public String decrypt(String cipherText) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(cipherText);
        Cipher decriptCipher = Cipher.getInstance(algoTypeInstance);
        decriptCipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        return new String(decriptCipher.doFinal(bytes), UTF_8);
    }

    public String sign(String plainText) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(keyPair.getPrivate());
        privateSignature.update(plainText.getBytes(UTF_8));
        byte[] signature = privateSignature.sign();
        return Base64.getEncoder().encodeToString(signature);
    }

    public boolean verify(String plainText, String signature) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(keyPair.getPublic());
        publicSignature.update(plainText.getBytes(UTF_8));
        byte[] signatureBytes = Base64.getDecoder().decode(signature);
        return publicSignature.verify(signatureBytes);
    }

    public String getIV(){
        byte iv[] = new byte[16];
            SecureRandom my_rand = new SecureRandom();
            my_rand.nextBytes(iv);
        return Base64.getEncoder().encodeToString(iv);

    }


}
