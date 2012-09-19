package com.befasoft.common.tools;

import java.util.Random;

public class AlgorithmSHA1 {
    private static Random randomGenerator = new Random();

    /**
     * Constructor AlgorithmSHA1
     * Constructor privado para evitar la creaci�n de objetos de esta clase
     */
    private AlgorithmSHA1() {
    }

    private static String hex_chr = "0123456789abcdef";

    private static String hex(int num) {
        String str = "";
        for(int j = 7; j >= 0; j--)
            str += hex_chr.charAt((num >> (j * 4)) & 0x0F);
        return str;
    }

    private static int[] str2blks_SHA1(String str) {
        int nblk = ((str.length() + 8) >> 6) + 1;
        int[] blks = new int[nblk * 16];
        for(int i = 0; i < nblk * 16; i++)
            blks[i] = 0;
        for(int i = 0; i < str.length(); i++)
            blks[i >> 2] |= str.charAt(i) << (24 - (i % 4) * 8);
        int i = str.length();
        blks[i >> 2] |= 0x80 << (24 - (i % 4) * 8);
        blks[nblk * 16 - 1] = str.length() * 8;
        return blks;
    }

    private static int safe_add(int x, int y) {
        int lsw = (x & 0xFFFF) + (y & 0xFFFF);
        int msw = (x >> 16) + (y >> 16) + (lsw >> 16);
        return (msw << 16) | (lsw & 0xFFFF);
    }

    private static int rol(int num, int cnt) {
        return (num << cnt) | (num >>> (32 - cnt));
    }

    private static int ft(int t, int b, int c, int d) {
        if (t < 20) return (b & c) | ((~b) & d);
        if (t < 40) return b ^ c ^ d;
        if (t < 60) return (b & c) | (b & d) | (c & d);
        return b ^ c ^ d;
    }

    private static int kt(int t) {
        return (t < 20) ?  1518500249 : (t < 40) ?  1859775393 :
                (t < 60) ? -1894007588 : -899497514;
    }

    public static String calcSHA1(String str) {
        int a =  1732584193;
        int b = -271733879;
        int c = -1732584194;
        int d =  271733878;
        int e = -1009589776;

        if (str == null || str.equals("")) {
            return hex((int)(Math.random()*12345678)) +
                    hex((int)(Math.random()*12345678)) +
                    hex((int)(Math.random()*12345678)) +
                    hex((int)(Math.random()*12345678)) +
                    hex((int)(Math.random()*12345678));
        }

        int[] x = str2blks_SHA1(str);
        int[] w = new int[80];

        for(int i = 0; i < x.length; i += 16) {
            int olda = a;
            int oldb = b;
            int oldc = c;
            int oldd = d;
            int olde = e;

            for(int j = 0; j < 80; j++) {
                if (j < 16)
                    w[j] = x[i + j];
                else
                    w[j] = rol(w[j-3] ^ w[j-8] ^ w[j-14] ^ w[j-16], 1);

                int t = safe_add(safe_add(rol(a, 5), ft(j, b, c, d)),
                        safe_add(safe_add(e, w[j]), kt(j)));
                e = d;
                d = c;
                c = rol(b, 30);
                b = a;
                a = t;
            }

            a = safe_add(a, olda);
            b = safe_add(b, oldb);
            c = safe_add(c, oldc);
            d = safe_add(d, oldd);
            e = safe_add(e, olde);
        }
        return hex(a) + hex(b) + hex(c) + hex(d) + hex(e);
    }

    /**
     * Genera una llave (Cadena aleatoria)
     */
    public static String generateKey() {
        String llave = "";
        int j = randomGenerator.nextInt(Constants.MAX_LENGTH_KEY - Constants.MIN_LENGTH_KEY + 1) + Constants.MIN_LENGTH_KEY;
        for (int i = 0; i < j; i++) {
            llave = llave.concat(String.valueOf((char)(randomGenerator.nextInt(Constants.MAX_CHAR_CODE - Constants.MIN_CHAR_CODE + 1) + Constants.MIN_CHAR_CODE)));
        }
        return llave;
    }

    /**
     * Crea un ID de session
     * @param clientKey Cadena partir de la cual se generar� el ID de Sesi�n
     * @param serverKey Cadena partir de la cual se generar� el ID de Sesi�n
     * @return El nuevo ID de Sesi�n
     */
    public static String createSessionID(String clientKey, String serverKey) {
        int firstChar = (int)serverKey.charAt(0);
        String[] arrPhrase = Constants.PHRASE_KEY.split(" ");
        String wordPhrase = arrPhrase[firstChar % arrPhrase.length];
        int secondChar = (int)serverKey.charAt(1);
        int indexKey = secondChar % 4;

        // Calcular y retornar el nuevo ID de Sesi�n adecuado
        switch (indexKey) {
            case 0:
                return calcSHA1(Constants.SECURITY_KEY + clientKey + serverKey + wordPhrase);
            case 1:
                return calcSHA1(clientKey + Constants.SECURITY_KEY + serverKey + wordPhrase);
            case 2:
                return calcSHA1(clientKey + serverKey + Constants.SECURITY_KEY + wordPhrase);
            case 3:
                return calcSHA1(clientKey + serverKey + wordPhrase + Constants.SECURITY_KEY);
            default:
                return calcSHA1(clientKey + serverKey + wordPhrase + Constants.SECURITY_KEY);
        }

    }

    public static String createSessionID(String clientKey, String serverKey, String user, String passwd) {
        int firstChar = (int)serverKey.charAt(0);
        String[] arrPhrase = Constants.PHRASE_KEY.split(" ");
        String wordPhrase = arrPhrase[firstChar % arrPhrase.length];
        int secondChar = (int)serverKey.charAt(1);
        int indexKey = secondChar % 6;

        // Calcular y retornar el nuevo ID de Sesi�n adecuado
        switch (indexKey) {
            case 0:
                return calcSHA1(Constants.SECURITY_KEY + clientKey + serverKey + wordPhrase + user + passwd);
            case 1:
                return calcSHA1(clientKey + Constants.SECURITY_KEY + serverKey + wordPhrase + user + passwd);
            case 2:
                return calcSHA1(clientKey + serverKey + Constants.SECURITY_KEY + wordPhrase + user + passwd);
            case 3:
                return calcSHA1(clientKey + serverKey + wordPhrase + Constants.SECURITY_KEY + user + passwd);
            case 4:
                return calcSHA1(clientKey + serverKey + wordPhrase + user + Constants.SECURITY_KEY + passwd);
            case 5:
                return calcSHA1(clientKey + serverKey + wordPhrase  + user + passwd + Constants.SECURITY_KEY);
            default:
                return calcSHA1(clientKey + serverKey + wordPhrase  + user + passwd + Constants.SECURITY_KEY);
        }

    }
}
