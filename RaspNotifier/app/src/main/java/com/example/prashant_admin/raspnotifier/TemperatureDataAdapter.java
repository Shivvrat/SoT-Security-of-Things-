package com.example.prashant_admin.raspnotifier;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chilkatsoft.CkCrypt2;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by prashant-admin on 20/12/17.
 */

public class TemperatureDataAdapter extends RecyclerView.Adapter<TemperatureDataViewHolder>{

    private static final String IV_STRING = "";
    private static final String charset = "UTF-8";
    private List<TemperatureData> temperatureDataList;
    protected Context context;
    static {
        try {
            System.loadLibrary("chilkat");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load.\n" + e);
            System.exit(1);
        }
    }
        public TemperatureDataAdapter(Context context, List<TemperatureData> temperatureDataList) {
        this.temperatureDataList = temperatureDataList;
        this.context = context;
    }
    @Override
    public TemperatureDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TemperatureDataViewHolder viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        viewHolder = new TemperatureDataViewHolder(layoutView, temperatureDataList);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(TemperatureDataViewHolder holder, int position) {
        CkCrypt2 crypt = new CkCrypt2();
        boolean success = crypt.UnlockComponent("Anything for 30-day trial");
        if (success != true) {
            System.out.println(crypt.lastErrorText());
            return;
        }

        //  AES is also known as Rijndael.
        crypt.put_CryptAlgorithm("aes");

        //  CipherMode may be "ecb", "cbc", "ofb", "cfb", "gcm", etc.
        //  Note: Check the online reference documentation to see the Chilkat versions
        //  when certain cipher modes were introduced.
        crypt.put_CipherMode("ecb");

        //  KeyLength may be 128, 192, 256
        crypt.put_KeyLength(128);

        //  The padding scheme determines the contents of the bytes
        //  that are added to pad the result to a multiple of the
        //  encryption algorithm's block size.  AES has a block
        //  size of 16 bytes, so encrypted output is always
        //  a multiple of 16.
        crypt.put_PaddingScheme(0);

        //  EncodingMode specifies the encoding of the output for
        //  encryption, and the input for decryption.
        //  It may be "hex", "url", "base64", or "quoted-printable".
        crypt.put_EncodingMode("hex");

        //  An initialization vector is required if using CBC mode.
        //  ECB mode does not use an IV.
        //  The length of the IV is equal to the algorithm's block size.
        //  It is NOT equal to the length of the key.
        String ivHex = "000102030405060708090A0B0C0D0E0F";
        /*crypt.SetEncodedIV(ivHex,"hex");*/

        //  The secret key must equal the size of the key.  For
        //  256-bit encryption, the binary secret key is 32 bytes.
        //  For 128-bit encryption, the binary secret key is 16 bytes.
        String keyHex = "5468617473206d79204b756e67204675";
        crypt.SetEncodedKey(keyHex,"hex");

        //  Encrypt a string...
        //  The input string is 44 ANSI characters (i.e. 44 bytes), so
        //  the output should be 48 bytes (a multiple of 16).
        //  Because the output is a hex string, it should
        //  be 96 characters long (2 chars per byte).
        /*String encStr = crypt.encryptStringENC("The quick brown fox jumps over the lazy dog.");
        System.out.println(encStr);*/



        String key = temperatureDataList.get(position).getKey();
        String tempData = temperatureDataList.get(position).getTemperature();
        String decryptedTemp = "";
        //  Now decrypt:
        String decStr = crypt.decryptEncoded(tempData);
        System.out.println(decStr);
        decStr = decStr.toUpperCase();
        int val = 0;String digits = "0123456789ABCDEF";
        for (int i = 0; i < decStr.length(); i++)
        {
            char c = decStr.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        decryptedTemp = Integer.toString(val);
        String finalTemp = decryptedTemp +" \u2103";
        holder.temperatureTextView.setText(finalTemp);
        holder.messageTextView.setText(temperatureDataList.get(position).getMessage());
        holder.dateTextView.setText(temperatureDataList.get(position).getDate());
    }
    @Override
    public int getItemCount() {
        return this.temperatureDataList.size();
    }

    public static String aesEncryptString(String content, String key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        byte[] contentBytes = content.getBytes(charset);
        byte[] keyBytes = key.getBytes(charset);
        String s = "";
        byte[] encryptedBytes = aesEncryptBytes(contentBytes, keyBytes);
        Base64.Encoder encoder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            encoder = Base64.getEncoder();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            s = encoder.encodeToString(encryptedBytes);
            return encoder.encodeToString(encryptedBytes);
        }
        return s;
    }

    public static String aesDecryptString(String content, String key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        Base64.Decoder decoder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            decoder = Base64.getDecoder();
        }
        byte[] encryptedBytes = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            encryptedBytes = decoder.decode(content);
        }
        byte[] keyBytes = key.getBytes(charset);
        byte[] decryptedBytes = aesDecryptBytes(encryptedBytes, keyBytes);
        return new String(decryptedBytes, charset);
    }
    public static byte[] aesEncryptBytes(byte[] contentBytes, byte[] keyBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        return cipherOperation(contentBytes, keyBytes, Cipher.ENCRYPT_MODE);
    }

    public static byte[] aesDecryptBytes(byte[] contentBytes, byte[] keyBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        return cipherOperation(contentBytes, keyBytes, Cipher.DECRYPT_MODE);
    }
    private static byte[] cipherOperation(byte[] contentBytes, byte[] keyBytes, int mode) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

        byte[] initParam = IV_STRING.getBytes(charset);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(mode, secretKey, ivParameterSpec);

        return cipher.doFinal(contentBytes);
    }
}

