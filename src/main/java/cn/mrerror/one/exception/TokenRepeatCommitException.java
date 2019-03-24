package cn.mrerror.one.exception;

/**
 * 重复提交异常
 */
public class TokenRepeatCommitException extends PlatformException {

    public TokenRepeatCommitException(String message) {
        super(message);
    }

}
