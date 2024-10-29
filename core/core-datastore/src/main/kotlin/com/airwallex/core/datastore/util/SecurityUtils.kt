package com.airwallex.core.datastore.util

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.*
import android.util.Base64
import com.airwallex.core.util.BYTE_TO_STRING_SEPARATOR
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object SecurityUtils {
    private const val CIPHER_IV_LENGTH = 12
    private const val IV_SEPARATOR = "|"
    private const val PROVIDER = "AndroidKeyStore"


    private val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    private val charset by lazy {
        charset("UTF-8")

    }
    private val keyStore by lazy {
        KeyStore.getInstance(PROVIDER).apply {
            load(null)
        }
    }
    private val keyGenerator by lazy {
        KeyGenerator.getInstance(KEY_ALGORITHM_AES, PROVIDER)
    }

    @Synchronized
    fun encryptData(keyAlias: String, text: String): String {
        cipher.init(Cipher.ENCRYPT_MODE, generateSecretKey(keyAlias))

        var result = Base64.encodeToString(cipher.iv, Base64.DEFAULT) + IV_SEPARATOR

        val bytes = cipher.doFinal(text.toByteArray(charset))

        result += bytes.joinToString(BYTE_TO_STRING_SEPARATOR)

        return result
    }

    @Synchronized
    fun decryptData(keyAlias: String, encryptedData: String): String {
        val split = encryptedData.split(IV_SEPARATOR)
        if (split.size != 2)
            throw IllegalArgumentException("Passed data is incorrect. There was no IV specified with it.")

        val ivString = split[0]
        val actualEncryptedDataString = split[1]

        val actualEncryptedDataByteArray = actualEncryptedDataString.split(BYTE_TO_STRING_SEPARATOR).map { it.toByte() }.toByteArray()
        val ivSpec = GCMParameterSpec(128, Base64.decode(ivString, Base64.DEFAULT))

        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(keyAlias), ivSpec)
        return cipher.doFinal(actualEncryptedDataByteArray).toString(charset)
    }

    private fun generateSecretKey(keyAlias: String): SecretKey {
        return keyGenerator.apply {
            init(
                    KeyGenParameterSpec
                            .Builder(keyAlias, PURPOSE_ENCRYPT or PURPOSE_DECRYPT)
                            .setBlockModes(BLOCK_MODE_GCM)
                            .setEncryptionPaddings(ENCRYPTION_PADDING_NONE)
                            .build()
            )
        }.generateKey()
    }

    private fun getSecretKey(keyAlias: String) =
            (keyStore.getEntry(keyAlias, null) as KeyStore.SecretKeyEntry).secretKey
}
