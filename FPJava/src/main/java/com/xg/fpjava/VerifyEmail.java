package com.xg.fpjava;

import static com.xg.fpjava.Case.match;
import static com.xg.fpjava.Case.mcase;
import static com.xg.util.Result.failure;
import static com.xg.util.Result.success;

import com.xg.util.Effect;
import com.xg.util.Result;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * @author xg.zhao   2018 04 01 21:22
 */
public class VerifyEmail {

    static Pattern email = Pattern.compile("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$");
    static Function<String, Boolean> verifyEmailFun = str -> email.matcher(str).matches();
    //    static Function<String, Result> verifyEmailFun2 = str -> str == null ? new Failure("email must not be null")
    //            : str.length() == 0 ? new Failure("email must not be empty") : new Success();
    static Function<String, Result<String>> verifyEmailFun3 = str -> str == null ? failure("email must not be null")
            : str.length() == 0 ? failure("email must not be empty") : success(str);
    static Effect<String> success = str -> System.out.println("Mail send to:" + str);
    static Effect<String> failure = str -> System.out.println("Error message logged:" + str);
    static Function<String, Result<String>> verifyEmailFun4 = str -> match(mcase(() -> success(str)),
                                                                           mcase(() -> str == null, () -> failure("email must not be null")),
                                                                           mcase(() -> str.length() == 0, () -> failure("email must not be empty")),
                                                                           mcase(() -> !email.matcher(str).matches(),
                                                                                 () -> failure("email " + str + "is invalid.")));

    public static void main(String[] args) {
        verifyEmailFun3.apply(null).bind(success, failure);
        verifyEmailFun3.apply("").bind(success, failure);
        verifyEmailFun3.apply("abc@123.com").bind(success, failure);

        verifyEmailFun4.apply(null).bind(success, failure);
        verifyEmailFun4.apply("").bind(success, failure);
        verifyEmailFun4.apply("abc@123.com").bind(success, failure);
    }

    private static void loggerError(String str) {
        System.out.println("Error message logged:" + str);
    }

    private static void sendMail(String str) {
        System.out.println("Mail send to:" + str);
    }

    //    static Executable validate2(String str) {
    //        Result apply = verifyEmailFun2.apply(str);
    //        return (apply instanceof Success) ? () -> sendMail(str) : () -> loggerError(((Failure) apply).getErrorMessage());
    //    }
    //
    //    static void validate(String str) {
    //        Result apply = verifyEmailFun2.apply(str);
    //        if (apply instanceof Success) {
    //            sendMail(str);
    //        } else {
    //            loggerError(((Failure) apply).getErrorMessage());
    //        }
    //    }
}
