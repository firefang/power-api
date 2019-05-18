package io.github.firefang.power.engine.step.impl;

import java.util.Map;

import io.github.firefang.power.common.util.CollectionUtil;
import io.github.firefang.power.engine.EngineConstants;
import io.github.firefang.power.engine.encrypt.IRequestEncrypter;
import io.github.firefang.power.engine.request.IRequestInfo;
import io.github.firefang.power.engine.step.StepContext;

/**
 * Step of executing encryption
 * 
 * @author xinufo
 *
 */
public class ExecEncryptStep extends BaseEncryptDecryptStep {

    @Override
    public String name() {
        return "执行加密/加签";
    }

    @Override
    protected void handleCaseStart(StepContext cxt) {
        Map<String, Object> params = cxt.getApiInfo().getResolvedEncryptParams();
        if (CollectionUtil.isEmpty(params)) {
            return;
        }
        IRequestInfo request = encryptRequest(cxt.getRequest(), params, cxt);
        cxt.setRequest(request);
    }

    private IRequestInfo encryptRequest(IRequestInfo request, Map<String, Object> params, StepContext cxt) {
        boolean encryptFirst = getBooleanFromMap(params, EngineConstants.KEY_ENCRYPT_FIRST, true);
        boolean needEncrypt = getBooleanFromMap(params, EngineConstants.KEY_NEED_ENCRYPT, false);
        boolean needSign = getBooleanFromMap(params, EngineConstants.KEY_NEED_SIGN, false);

        if (!(needEncrypt || needSign)) {
            // 参数中设置无需加密加签
            return request;
        }

        IRequestEncrypter encrypter = getEncrypter(cxt);
        if (encryptFirst) {
            // 先加密后加签
            if (needEncrypt) {
                request = encrypter.encrypt(request, params);
            }
            if (needSign) {
                request = encrypter.sign(request, params);
            }
        } else {
            // 先加签后加密
            if (needSign) {
                request = encrypter.sign(request, params);
            }
            if (needEncrypt) {
                request = encrypter.encrypt(request, params);
            }
        }

        return request;
    }

}
