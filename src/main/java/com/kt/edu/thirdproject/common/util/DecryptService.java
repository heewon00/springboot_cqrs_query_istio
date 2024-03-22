package com.kt.edu.thirdproject.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Slf4j
@Component
public class DecryptService {
    //    private static final String PRIVATE_KEY_STRING = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMrUv558NK116qYyBimbAjg4nVzBmlzUug1OnRK7x4PYvGxB940cDmooxG0y8G0MIOuB+YPj7Wr+B+qCcQigH9eQyFTjb5bM4r5ucj+VTo4m441LFlLYDEvgci7ozgQBOFe+PoCTOezex89009bKqAQU3Nmeg2bZcSZHeZ97xMjdAgMBAAECgYEAtkS6sWPyPpKBO7omWYsHUphlqO39lkA/tSeY9TWzk+LXZ/1bxP2aYoGiCnNFJOlKR/hzzbasKoJ/gJiZlOO31/iGtDqen6h78xvYLv1NiUOAirSR3ves1ygL/BPCVO1m2wyk1q+RpU+WojW7++norR7QC/c2jlnwQr1WnEtv50ECQQDpOidWJjdKTVz2pUpGRsy03Rh7tMbYAr2qoVwHm6O8b3M2wuWkx2zqXR5u0MHdxAGJTDPxxmSn8CRs4KLOCXgxAkEA3qLM7+IENUJB443G+kpKaSDGkd7RHyPWX39I4X2XW5FOro/IK/48FgtbXewWneNd0YyBbNQTLuFPIlzji3tcbQJAO0x86euHjU3EQDXgf6PKsoWwjut0z0FLJhImywxogiW4HolJSC8oYyJP9Y8Aa7lFRrpoTqXJu+VT7Zbj7gz8UQJAOWMjik3rIcNTcpFwEcImjhSpup4Br5IwRutrYNxbsHmzG2HqihR9bn087ufVKJub6lDaZ6/+xd3D5SqmaKTCvQJBANXsyRxddnPKC7CfPjXWBRwY+S6byJSOdvWVTqf4ypKRE6q3NsbeRtJRaWOm1eHASwZyhyJTfgt835rIQRbiRw0=";
    @Value("${jwt.private-key}")
    private String privatekey;

    public Mono<String> login(String encryptedPassword) {
        return Mono.fromCallable(() -> {
            log.info("[{}] ***************encryptedPassword", encryptedPassword);

            // Base64 디코딩 후, 개인키 생성
            byte[] decodedKey = Base64.getDecoder().decode(privatekey);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decodedKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = kf.generatePrivate(spec);

            // RSA 복호화
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
            log.info("[{}] ***************decryptedBytes", decryptedBytes);

            // 복호화된 바이트 배열을 문자열로 변환하여 반환
            return new String(decryptedBytes);
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
