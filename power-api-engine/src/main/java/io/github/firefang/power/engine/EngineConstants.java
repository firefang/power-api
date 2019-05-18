package io.github.firefang.power.engine;

/**
 * Constant list
 * 
 * @author xinufo
 *
 */
public interface EngineConstants {
    // -------------------------------------
    // Configuration
    // -------------------------------------
    String KEY_KEYWORD_FOLDER = "keyword";
    String KEY_ENCRYPT_FOLDER = "encrypt";
    String DEAFULT_KEYWORD_FOLDER = "keyword";
    String DEAFULT_ENCRYPT_FOLDER = "encrypt";

    // -------------------------------------
    // request parameter
    // -------------------------------------
    String KEY_REQUEST_TYPE = "type";
    String KEY_REQUEST_CONTENT = "content";

    // -------------------------------------
    // response
    // -------------------------------------
    String KEY_RESPONSE_VAR_NAME = "response";

    // -------------------------------------
    // encrypt parameter
    // -------------------------------------
    String KEY_NEED_ENCRYPT = "needEncrypt";
    String KEY_NEED_DECRYPT = "needDecrypt";
    String KEY_NEED_SIGN = "needSign";
    String KEY_NEED_VERIFY_SIGN = "needVerifySign";
    String KEY_ENCRYPT_FIRST = "encryptFirst";
    String KEY_DECRYPT_FIRST = "decryptFirst";

    // -------------------------------------
    // ClassLoader
    // -------------------------------------
    String NAME_KEYWORD_LOADER = "keyword";
    String NAME_ENCRYPT_LOADER = "encrypt";

    // -------------------------------------
    // results of depended cases
    // -------------------------------------
    String KEY_CASE_RESULTS = "ENGINE-CASE-RESULTS";

}
