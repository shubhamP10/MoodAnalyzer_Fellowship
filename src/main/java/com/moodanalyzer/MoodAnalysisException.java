package com.moodanalyzer;
/*
* User Deffined Exception class
*/
public class MoodAnalysisException extends Exception {
    enum exceptionType {
        ENTERED_EMPTY, ENTERED_NULL
    }
    exceptionType type;
    public MoodAnalysisException(exceptionType type, String message) {
        super(message);
        this.type = type;
    }
}
