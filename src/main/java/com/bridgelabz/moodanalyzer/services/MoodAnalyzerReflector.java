package com.bridgelabz.moodanalyzer;

import com.bridgelabz.moodanalyzer.exception.MoodAnalysisException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class MoodAnalyzerReflector {

    public static MoodAnalyzer createMoodAnalyzer(String... parameters) throws MoodAnalysisException {
        try {
            Class<?> moodAnalyzerClass = Class.forName(parameters[0]);
            if (parameters.length > 1) {
                Class<?> classObject = Class.forName("java.lang." + parameters[1]);
                Constructor<?> moodConstructor = moodAnalyzerClass.getConstructor(classObject);
                Object moodObject = moodConstructor.newInstance(parameters[2]);
                return (MoodAnalyzer) moodObject;
            } else {
                Constructor<?> constructor = moodAnalyzerClass.getConstructor();
                Object moodObject = constructor.newInstance();
                return (MoodAnalyzer) moodObject;
            }
        } catch (ClassNotFoundException e) {
            throw new MoodAnalysisException(MoodAnalysisException.exceptionType.NO_SUCH_CLASS, "No Such Class");
        } catch (NoSuchMethodException e) {
            throw new MoodAnalysisException(MoodAnalysisException.exceptionType.NO_SUCH_METHOD, e);
        } catch (InvocationTargetException e) {
            throw new MoodAnalysisException(MoodAnalysisException.exceptionType.METHOD_INVOCATION_ISSUE, "May Be Issues with Data Entered", e);
        } catch (IllegalAccessException e) {
            throw new MoodAnalysisException(MoodAnalysisException.exceptionType.ILLIGAL_ACCESS, e);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String invokeMethod(MoodAnalyzer moodAnalyzer) throws MoodAnalysisException {
        try {
            return moodAnalyzer.analyseMood();
        } catch (MoodAnalysisException e) {
            throw new MoodAnalysisException(MoodAnalysisException.exceptionType.NO_SUCH_METHOD, "No Such Method");
        }
    }

    public static void setFieldValue(MoodAnalyzer moodAnalyzer, String fieldName, String fieldValue) throws MoodAnalysisException {
        if (fieldValue == null) {
            throw new MoodAnalysisException(MoodAnalysisException.exceptionType.ENTERED_NULL, "Cannot Set Null to Field");
        }
        try {
            Field field = moodAnalyzer.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(moodAnalyzer, fieldValue);
        } catch (NoSuchFieldException e) {
            throw new MoodAnalysisException(MoodAnalysisException.exceptionType.NO_SUCH_FIELD, "Define Proper Field");
        } catch (IllegalAccessException e) {
            throw new MoodAnalysisException(MoodAnalysisException.exceptionType.ILLIGAL_ACCESS, e);
        }
    }
}
