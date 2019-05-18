package io.github.firefang.power.engine.step.impl;

import java.util.Map;

import io.github.firefang.power.common.util.CollectionUtil;
import io.github.firefang.power.engine.EngineConstants;
import io.github.firefang.power.engine.encrypt.IRequestEncrypter;
import io.github.firefang.power.engine.request.IResponseInfo;
import io.github.firefang.power.engine.step.StepContext;

/**
 * Step of executing decryption
 * 
 * @author xinufo
 *
 */
public class ExecDecryptStep extends BaseEncryptDecryptStep {

    @Override
    public String name() {
        return "执行解密/验签";
    }

    @Override
    protected void handleCaseEnd(StepContext cxt) {
        Map<String, Object> params = cxt.getApiInfo().getResolvedEncryptParams();
        IResponseInfo response = cxt.getResponse();
        // response为空说明可能请求出错
        if (CollectionUtil.isEmpty(params) || response == null) {
            return;
        }
        response = decryptResponse(response, params, cxt);
        cxt.setResponse(response);
    }

    private IResponseInfo decryptResponse(IResponseInfo response, Map<String, Object> params, StepContext cxt) {
        boolean decryptFirst = getBooleanFromMap(params, EngineConstants.KEY_DECRYPT_FIRST, true);
        boolean needDecrypt = getBooleanFromMap(params, EngineConstants.KEY_NEED_DECRYPT, false);
        boolean needVerify = getBooleanFromMap(params, EngineConstants.KEY_NEED_VERIFY_SIGN, false);

        if (!(needDecrypt || needVerify)) {
            // 参数中设置无需加密加签
            return response;
        }

        IRequestEncrypter encrypter = getEncrypter(cxt);
        if (decryptFirst) {
            // 先解密后验签
            if (needDecrypt) {
                response = encrypter.decrypt(response, params);
            }
            if (needVerify) {
                verifySign(response, params, encrypter, cxt);
            }
        } else {
            // 先验签后解密
            if (needVerify) {
                verifySign(response, params, encrypter, cxt);
            }
            if (needDecrypt) {
                response = encrypter.decrypt(response, params);
            }
        }

        return response;
    }

    private void verifySign(IResponseInfo response, Map<String, Object> params, IRequestEncrypter encrypter, StepContext cxt) {
        if (!encrypter.verifySign(response, params)) {
            cxt.setVerifySignFailed(true);
        }
    }

}
