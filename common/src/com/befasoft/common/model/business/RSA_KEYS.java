package com.befasoft.common.model.business;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class RSA_KEYS {

    BigInteger publicModulus, publicExponent;
    BigInteger privateModulus, privateExponent;

    public RSA_KEYS(BigInteger privateModulus, BigInteger privateExponent,BigInteger publicModulus, BigInteger publicExponent) {
        this.publicModulus = publicModulus;
        this.publicExponent = publicExponent;
        this.privateModulus = privateModulus;
        this.privateExponent = privateExponent;
    }

    public RSA_KEYS(BigInteger publicModulus, BigInteger publicExponent) {
        this.publicModulus = publicModulus;
        this.publicExponent = publicExponent;
    }

    /**
     * Genera una llave publica
     *
     * @return Llave publica
     * @throws Exception Error al generar la llave
     */
    public PublicKey getPublicKey() throws Exception {
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(publicModulus, publicExponent);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        return fact.generatePublic(keySpec);
    }

    /**
     * Genera una llave privada
     *
     * @return Llave privada
     * @throws Exception Error al generar la llave
     */
    public PrivateKey getPrivateKey() throws Exception {
        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(privateModulus, privateExponent);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        return fact.generatePrivate(keySpec);
    }

    /*
     * Metodos Get/Set
     */

    public BigInteger getPublicModulus() {
        return publicModulus;
    }

    public void setPublicModulus(BigInteger publicModulus) {
        this.publicModulus = publicModulus;
    }

    public BigInteger getPublicExponent() {
        return publicExponent;
    }

    public void setPublicExponent(BigInteger publicExponent) {
        this.publicExponent = publicExponent;
    }

    public BigInteger getPrivateModulus() {
        return privateModulus;
    }

    public void setPrivateModulus(BigInteger privateModulus) {
        this.privateModulus = privateModulus;
    }

    public BigInteger getPrivateExponent() {
        return privateExponent;
    }

    public void setPrivateExponent(BigInteger privateExponent) {
        this.privateExponent = privateExponent;
    }
}
