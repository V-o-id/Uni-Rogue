package com.mygdx.game.data;

/**
 * This exception will be thrown when there is an error with the encryption or decryption of the player data.
 * It is a wrapper for the exceptions thrown by the encryption and decryption methods.
 * The specific exception can be accessed with getCause(), which returns a Throwable.
 * The message of the exception can be accessed with getMessage().
 */
class CryptoException extends Exception {

    public CryptoException() {
    }

    CryptoException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
