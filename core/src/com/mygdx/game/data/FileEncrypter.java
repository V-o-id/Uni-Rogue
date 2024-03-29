package com.mygdx.game.data;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * This class is used to encrypt and decrypt files.
 * It uses the AES algorithm.
 * A string key is used to encrypt and decrypt the files.
 * source: <a href="https://www.codejava.net/coding/file-encryption-and-decryption-simple-example">codejava.net</a>
 */
public class FileEncrypter {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    /**
     * Static method that encrypts a file with the given key. The encrypted file will be saved to the given output file.
     * @param key the key used to encrypt the file
     * @param inputFile the file to encrypt
     * @param outputFile the file to save the encrypted file to
     * @throws CryptoException if an error occurs during the encryption
     * Note: the output file will be overwritten if it already exists, output file and input file can be the same
     */
    public static void encrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }

    /**
     * Static method that decrypts a file with the given key. The decrypted file will be saved to the given output file.
     * @param key the key used to decrypt the file
     * @param inputFile the file to decrypt
     * @param outputFile the file to save the decrypted file to
     * @throws CryptoException if an error occurs during the decryption
     * Note: the output file will be overwritten if it already exists, output file and input file can be the same
     */
    public static void decrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }

    private static void doCrypto(int cipherMode, String key, File inputFile,
                                 File outputFile) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                 | InvalidKeyException | BadPaddingException
                 | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }

}
