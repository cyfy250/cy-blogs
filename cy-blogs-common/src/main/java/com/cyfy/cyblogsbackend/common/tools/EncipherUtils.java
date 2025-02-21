package com.cyfy.cyblogsbackend.common.tools;

import com.cyfy.cyblogsbackend.common.exception.BusinessException;
import com.cyfy.cyblogsbackend.common.exception.ErrorCode;
import org.mindrot.jbcrypt.BCrypt;

public class EncipherUtils {
    // 盐值，用于加强密码
    private static final String SALT_PRE = "cyfy";
    private static final String SALT_SUFF = "2504";
    // 密码最小长度，最大长度
    private static final int PWD_MIN_LENGTH = 6;
    private static final int PWD_MAX_LENGTH = 25;

    public static String hashPsd(String psd) {
        if (psd == null || psd.length() < PWD_MIN_LENGTH || psd.length() > PWD_MAX_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 增加密码长度，提高加密强度
        String password = SALT_PRE + psd + SALT_SUFF;
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPsd(String psd, String hashPsd) {
        // 增加密码长度，提高加密强度
        String password = SALT_PRE + psd + SALT_SUFF;
        return BCrypt.checkpw(password, hashPsd);
    }
}
