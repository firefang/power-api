package io.github.firefang.power.engine.step.impl;

import java.util.Map;

import io.github.firefang.power.engine.EngineConstants;
import io.github.firefang.power.engine.encrypt.IRequestEncrypter;
import io.github.firefang.power.engine.step.StepContext;
import io.github.firefang.power.engine.util.JarLoaderUtil;

/**
 * Base class for encryption and decryption step
 * 
 * @author xinufo
 *
 */
public abstract class BaseEncryptDecryptStep extends BaseStagedStep {
    private static final String KEY_ENCRYPTRT = "encrypter";

    protected boolean getBooleanFromMap(Map<String, Object> map, String key, boolean defaultValue) {
        Object value = map.getOrDefault(key, defaultValue);
        return ((Boolean) value).booleanValue();
    }

    protected synchronized IRequestEncrypter getEncrypter(StepContext cxt) {
        return (IRequestEncrypter) cxt.getShare().computeIfAbsent(KEY_ENCRYPTRT, k -> {
            String className = cxt.getProjectInfo().getEntity().getEncryptClass();
            if (className == null) {
                throw new IllegalArgumentException("未设置用于加解密的类名");
            }
            return createEncrypter(className);
        });
    }

    protected IRequestEncrypter createEncrypter(String className) {
        try {
            ClassLoader loader = JarLoaderUtil.getClassLoader(EngineConstants.NAME_ENCRYPT_LOADER);
            Class<?> clazz = loader.loadClass(className);
            return (IRequestEncrypter) clazz.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("不能实例化加密类: " + className, e);
        }
    }

}
