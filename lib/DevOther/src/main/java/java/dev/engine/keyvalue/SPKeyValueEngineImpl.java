package java.dev.engine.keyvalue;

import java.lang.reflect.Type;

import dev.engine.json.DevJSONEngine;
import dev.engine.json.IJSONEngine;
import dev.engine.keyvalue.IKeyValueEngine;
import dev.utils.app.share.IPreference;
import dev.utils.common.ConvertUtils;

/**
 * detail: SharedPreferences Key-Value Engine 实现
 * @author Ttt
 */
public class SPKeyValueEngineImpl
        implements IKeyValueEngine<SPConfig> {

    private final SPConfig    mConfig;
    // SharedPreferences
    private final IPreference mPreference;
    // JSON Engine
    private       IJSONEngine mJSONEngine;

    public SPKeyValueEngineImpl(final SPConfig config) {
        this(config, DevJSONEngine.getEngine());
    }

    public SPKeyValueEngineImpl(
            final SPConfig config,
            final IJSONEngine jsonEngine
    ) {
        this.mConfig     = config;
        this.mJSONEngine = jsonEngine;
        // SharedPreferences
        mPreference = config.getPreference();
    }

    // =

    public SPKeyValueEngineImpl setJSONEngine(final IJSONEngine jsonEngine) {
        this.mJSONEngine = jsonEngine;
        return this;
    }

    // =============
    // = 对外公开方法 =
    // =============

    @Override
    public SPConfig getConfig() {
        return mConfig;
    }

    @Override
    public void remove(String key) {
        mPreference.remove(key);
    }

    @Override
    public void removeForKeys(String[] keys) {
        mPreference.removeAll(keys);
    }

    @Override
    public boolean contains(String key) {
        return mPreference.contains(key);
    }

    @Override
    public void clear() {
        mPreference.clear();
    }

    // =======
    // = 存储 =
    // =======

    @Override
    public boolean putInt(
            String key,
            int value
    ) {
        mPreference.put(key, value);
        return true;
    }

    @Override
    public boolean putLong(
            String key,
            long value
    ) {
        mPreference.put(key, value);
        return true;
    }

    @Override
    public boolean putFloat(
            String key,
            float value
    ) {
        mPreference.put(key, value);
        return true;
    }

    @Override
    public boolean putDouble(
            String key,
            double value
    ) {
        mPreference.put(key, value);
        return true;
    }

    @Override
    public boolean putBoolean(
            String key,
            boolean value
    ) {
        mPreference.put(key, value);
        return true;
    }

    @Override
    public boolean putString(
            String key,
            String value
    ) {
        String content = value;
        if (value != null && mConfig.cipher != null) {
            byte[] bytes = mConfig.cipher.encrypt(ConvertUtils.toBytes(value));
            content = ConvertUtils.newString(bytes);
        }
        mPreference.put(key, content);
        return true;
    }

    @Override
    public <T> boolean putEntity(
            String key,
            T value
    ) {
        if (mJSONEngine != null) {
            String json = mJSONEngine.toJson(value);
            return putString(key, json);
        }
        return false;
    }

    // =======
    // = 获取 =
    // =======

    @Override
    public int getInt(String key) {
        return mPreference.getInt(key);
    }

    @Override
    public long getLong(String key) {
        return mPreference.getLong(key);
    }

    @Override
    public float getFloat(String key) {
        return mPreference.getFloat(key);
    }

    @Override
    public double getDouble(String key) {
        return mPreference.getDouble(key);
    }

    @Override
    public boolean getBoolean(String key) {
        return mPreference.getBoolean(key);
    }

    @Override
    public String getString(String key) {
        return mPreference.getString(key);
    }

    @Override
    public <T> T getEntity(
            String key,
            Type typeOfT
    ) {
        return getEntity(key, typeOfT, null);
    }

    // =

    @Override
    public int getInt(
            String key,
            int defaultValue
    ) {
        return mPreference.getInt(key, defaultValue);
    }

    @Override
    public long getLong(
            String key,
            long defaultValue
    ) {
        return mPreference.getLong(key, defaultValue);
    }

    @Override
    public float getFloat(
            String key,
            float defaultValue
    ) {
        return mPreference.getFloat(key, defaultValue);
    }

    @Override
    public double getDouble(
            String key,
            double defaultValue
    ) {
        return mPreference.getDouble(key, defaultValue);
    }

    @Override
    public boolean getBoolean(
            String key,
            boolean defaultValue
    ) {
        return mPreference.getBoolean(key, defaultValue);
    }

    @Override
    public String getString(
            String key,
            String defaultValue
    ) {
        String content = mPreference.getString(key, null);
        if (content == null) return defaultValue;
        if (mConfig.cipher != null) {
            byte[] bytes = mConfig.cipher.decrypt(ConvertUtils.toBytes(content));
            content = ConvertUtils.newString(bytes);
        }
        return content;
    }

    @Override
    public <T> T getEntity(
            String key,
            Type typeOfT,
            T defaultValue
    ) {
        if (mJSONEngine != null) {
            String json   = getString(key, null);
            T      object = (T) mJSONEngine.fromJson(json, typeOfT);
            if (object == null) return defaultValue;
            return object;
        }
        return null;
    }
}