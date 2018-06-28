package com.github.hualuomoli.demo.password;

import com.github.hualuomoli.async.dealer.Dealer;
import com.github.hualuomoli.async.executor.Executor;
import com.github.hualuomoli.async.util.AsyncUtils;

public class PasswordUtils {

    /**
     * 验证密码
     * @param password
     * @param callback
     */
    public static void checkPassword(final String password, final CheckCallback callback) {

        // before
        callback.before(password);

        Dealer dealer = AsyncUtils.newDealer("验证支付密码");
        dealer.execute(new Executor<Boolean, Throwable>() {

            @Override
            public Boolean deal(String name) throws Throwable {
                // 模拟网络验证密码过程
                Thread.sleep(3000);
                return Math.random() > 0.2;
            }

            @Override
            public void onSuccess(String name, Boolean success) {
                callback.end(success);
            }

            @Override
            public void onError(String name, Throwable throwable) {
                callback.error(throwable);
            }
        });
    }

    // 验证回调
    public interface CheckCallback {

        void before(String password);

        void end(boolean success);

        void error(Throwable throwable);

    }

}
