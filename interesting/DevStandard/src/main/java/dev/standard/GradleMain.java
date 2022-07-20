package dev.standard;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * detail: 获取 Gradle 文件夹下随机名字
 * @author Ttt
 * <pre>
 *     Gradle 缓存目录文件命名规则
 *     @see <a href="https://www.cnblogs.com/rainboy2010/p/7062279.html"/>
 *     @see <a href="https://services.gradle.org/distributions"/>
 *     Android Gradle 插件版本说明
 *     @see <a href="https://developer.android.google.cn/studio/releases/gradle-plugin"/>
 *     <p></p>
 *     快捷搜索、下载地址:
 *     distributionUrl=https\://services.gradle.org/distributions/gradle-6.5-all.zip
 *     C:/Users/Administrator/.gradle/wrapper/dists
 * </pre>
 */
class GradleMain {

    public static void main(String[] args) {
        String data = getGradleFileName("https://services.gradle.org/distributions/gradle-6.5-all.zip");
        System.out.println(data); // 2oz4ud9k3tuxjg84bbf55q0tn
    }

    private static String getGradleFileName(final String distributionUrl) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(distributionUrl.getBytes());
            return new BigInteger(1, messageDigest.digest()).toString(36);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}