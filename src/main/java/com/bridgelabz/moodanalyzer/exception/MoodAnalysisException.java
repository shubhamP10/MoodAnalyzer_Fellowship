package com.bridgelabz.moodanalyzer.exception;

/*
 * User Deffined Exception class
 */
public class MoodAnalysisException extends Exception {
    public exceptionType type;

    public MoodAnalysisException(exceptionType type, String message) {
        super(message);
        this.type = type;
    }

    public MoodAnalysisException(exceptionType type, Throwable cause) {
        super(cause);
        this.type = type;
    }

    public MoodAnalysisException(exceptionType type, String messgae, Throwable cause) {
        super(messgae);
        new MoodAnalysisException(type, cause);
    }

    public enum exceptionType {
        ENTERED_EMPTY, ENTERED_NULL, NO_SUCH_CLASS, NO_SUCH_METHOD,
        METHOD_INVOCATION_ISSUE, NO_SUCH_FIELD, ILLIGAL_ACCESS
    }
}
