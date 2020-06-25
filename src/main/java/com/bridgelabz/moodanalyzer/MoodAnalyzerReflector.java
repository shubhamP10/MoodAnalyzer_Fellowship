package com.bridgelabz.moodanalyzer;

import com.bridgelabz.moodanalyzer.exception.MoodAnalysisException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class MoodAnalyzerReflector {
    public static MoodAnalyzer createMoodAnalyzer(String message) throws MoodAnalysisException, InstantiationException {
        try {
            Class<?> moodAnalyzerClass = Class.forName("com.bridgelabz.moodanalyzer.MoodAnalyzer");
            Constructor<?> moodConstructor = moodAnalyzerClass.getConstructor(String.class);
            Object moodObj = moodConstructor.newInstance(message);
            return (MoodAnalyzer) moodObj;
        } catch (ClassNotFoundException e) {
            throw new MoodAnalysisException(MoodAnalysisException.exceptionType.NO_SUCH_CLASS, "No Such Class");
        } catch (InvocationTargetException e) {
            throw new MoodAnalysisException(MoodAnalysisException.exceptionType.METHOD_INVOCATION_ISSUE, "May Be Issues with Data Entered", e);
        } catch (NoSuchMethodException  e) {
            throw new MoodAnalysisException(MoodAnalysisException.exceptionType.NO_SUCH_METHOD,e);
        } catch (IllegalAccessException e) {
            throw new MoodAnalysisException(MoodAnalysisException.exceptionType.ILLIGAL_ACCESS,e);
        }
    }

    public static Object invokeMethod(Object moodAnalyzerObject, String methodName) throws MoodAnalysisException {
        try {
            return moodAnalyzerObject.getClass().getMethod(methodName).invoke(moodAnalyzerObject);
        } catch (NoSuchMethodException e) {
            throw new MoodAnalysisException(MoodAnalysisException.exceptionType.NO_SUCH_METHOD, "Define Proper Method");
        } catch (InvocationTargetException e) {
            throw new MoodAnalysisException(MoodAnalysisException.exceptionType.METHOD_INVOCATION_ISSUE, "May Be Issues with Data Entered", e);
        } catch (IllegalAccessException e) {
            throw new MoodAnalysisException(MoodAnalysisException.exceptionType.ILLIGAL_ACCESS,e);
        }
    }

    public static void setFieldValue(Object myObject, String fieldName, String fieldValue) throws MoodAnalysisException {
        try {
            Field field = myObject.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(myObject, fieldValue);
        } catch (NoSuchFieldException e) {
            throw new MoodAnalysisException(MoodAnalysisException.exceptionType.NO_SUCH_FIELD, "Define Proper Field");
        } catch (IllegalAccessException e) {
            throw new MoodAnalysisException(MoodAnalysisException.exceptionType.ILLIGAL_ACCESS, e);
        }
    }
}
