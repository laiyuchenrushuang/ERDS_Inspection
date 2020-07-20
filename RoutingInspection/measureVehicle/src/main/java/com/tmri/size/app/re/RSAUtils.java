package com.tmri.size.app.re;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


/**
 * Created by tmri on 2018/4/11.
 */

public class RSAUtils {

    private static String publicKeystr="30819F300D06092A864886F70D010101050003818D0030818902818100B386E7086680ACA14DBBAA64248B07C59E7A75EE4B3C9C69C0B5A3AA4764DEEB6218980576175E9DDEDD129736C2049DF76F80651D0B9FFE25F0D17F86FF15CE0F0559AFBB6909C967E785FDCE7934CA57C09E73CC797A5A09F879756FCE44816930228182BB5AD0591F757B6674C04CEBEC0E95EB34D5D96B3DC41601E769D50203010001";

    private static final String transformation="RSA/NONE/PKCS1Padding";

    /**
     * 通过字符串生成公钥
     */
    public static PublicKey getPublicKey(){
    PublicKey publicKey = null;
    try {
       // byte [] decode = Base64.decode(publicKeystr.getBytes());
        byte[] decode = parseHexStr2Byte(publicKeystr);
        X509EncodedKeySpec x509 = new X509EncodedKeySpec(decode);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        publicKey = keyFactory.generatePublic(x509);
        } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    } catch (InvalidKeySpecException e) {
    e.printStackTrace();
    }
        return publicKey;
    }


    /**
     * 加密
     */
    public static String encrypt(String data){
        try {
                Cipher cipher = Cipher.getInstance(transformation);
                cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
               byte[] bytes=cipher.doFinal(data.getBytes());
           // String st = byteToBase64String(bytes);
            String st = parseByte2HexStr(bytes);
            return st;
            } catch (Exception e) {
            e.printStackTrace();
            }
            return null;
                            }

    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
    /**
     * 解密
     */
    public static String decrypt(String RSAstring){
        try {
            byte[] stByte = parseHexStr2Byte(RSAstring);
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, getPublicKey());
            byte[] bytes = cipher.doFinal(stByte);
            return new String(bytes);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
