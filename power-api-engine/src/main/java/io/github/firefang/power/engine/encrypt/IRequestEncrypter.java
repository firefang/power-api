package io.github.firefang.power.engine.encrypt;

import java.util.Map;

import io.github.firefang.power.engine.request.IRequestInfo;
import io.github.firefang.power.engine.request.IResponseInfo;

/**
 * Interface used for encryption and decryption
 * 
 * @author xinufo
 * 
 */
public interface IRequestEncrypter {

    /**
     * Encrypt request
     * 
     * @param resuest information of request
     * @param params parameters used to encrypt
     * @return encrypted request
     */
    default IRequestInfo encrypt(IRequestInfo request, Map<String, Object> params) {
        return request;
    }

    /**
     * Decrypt response
     * 
     * @param response information of response
     * @param params parameters used to encrypt
     * @return decrypted response
     */
    default IResponseInfo decrypt(IResponseInfo response, Map<String, Object> params) {
        return response;
    }

    /**
     * Sign request
     * 
     * @param resuest information of request
     * @param params parameters used to encrypt
     * @return signed request
     */
    default IRequestInfo sign(IRequestInfo request, Map<String, Object> params) {
        return request;
    }

    /**
     * verify the sign of response
     * 
     * @param response information of response
     * @param params parameters used to encrypt
     * @return return true if valid, otherwise false
     */
    default boolean verifySign(IResponseInfo response, Map<String, Object> params) {
        return true;
    }

}
