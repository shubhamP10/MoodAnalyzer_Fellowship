package com.bridgelabz.moodanalyzertest;

import com.bridgelabz.moodanalyzer.MoodAnalyzer;
import com.bridgelabz.moodanalyzer.MoodAnalyzerReflector;
import com.bridgelabz.moodanalyzer.exception.MoodAnalysisException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static com.bridgelabz.moodanalyzer.exception.MoodAnalysisException.exceptionType;
import static org.junit.Assert.assertEquals;

public class MoodAnalyzerTest {
    /*
     *This Test Case Excepts
     * Sad Mood
     */
    @Test
    public void givenMessgae_WhenSad_ShouldReturn_Sad() throws MoodAnalysisException {
        MoodAnalyzer moodAnalyzer = new MoodAnalyzer("I am in Sad Mood");
        String mood = moodAnalyzer.analyseMood();
        Assert.assertEquals("SAD", mood);
    }

    /*
     *This Test Case Excepts
     * Happy Mood
     */
    @Test
    public void givenMessage_WhenNotSad_ShouldReturn_Happy() throws MoodAnalysisException {
        MoodAnalyzer moodAnalyzer = new MoodAnalyzer("I am in any Mood");
        String mood = moodAnalyzer.analyseMood();
        Assert.assertEquals("HAPPY", mood);
    }

    /*
     *This Test Case Will Check For
     * Null Pointer Exception
     */
    @Test
    public void givenNullMood_ShouldReturn_Happy() {
        MoodAnalyzer moodAnalyzer = new MoodAnalyzer(null);
        String mood;
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(MoodAnalysisException.class);
            mood = moodAnalyzer.analyseMood();
            Assert.assertEquals("HAPPY", mood);
        } catch (MoodAnalysisException e) {
            e.printStackTrace();
        }
    }

    /*
     *This Test Case Will Check For
     * Null Pointer Exception
     * for empty and null values
     */
    @Test
    public void givenNullMood_ShouldThrow_Exception() {
        MoodAnalyzer moodAnalyzer = new MoodAnalyzer(null);
        try {
            moodAnalyzer.analyseMood(null);
        } catch (MoodAnalysisException e) {
            Assert.assertEquals(exceptionType.ENTERED_NULL, e.type);
        }
    }

    /*Using Reflection*/
    @Test
    public void givenMoodAnalyserWhenProper_ShouldReturn_Object() throws ClassNotFoundException, NoSuchMethodException {
        Constructor<?> constructor = Class.forName("com.bridgelabz.moodanalyzer.MoodAnalyzer").getConstructor(String.class);
        try {
            Object obj = constructor.newInstance("I'm in a Happy Mood");
            MoodAnalyzer moodAnalyzer = (MoodAnalyzer) obj;
            try {
                String mood = moodAnalyzer.analyseMood();
                Assert.assertEquals("HAPPY", mood);
            } catch (MoodAnalysisException e) {
                e.printStackTrace();
            }
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenMoodAnalyserClassWhenNotProper_ShouldThrow_Exception() throws NoSuchMethodException, ClassNotFoundException {
        try {
            Constructor<?> constructor = Class.forName("com.bridgelabz.moodanalyzer.MoodAnalyzer1").getConstructor(String.class);
            Object obj = constructor.newInstance("I'm in a Happy Mood");
            MoodAnalyzer moodAnalyzer = (MoodAnalyzer) obj;
            String mood = moodAnalyzer.analyseMood();
            assertEquals("HAPPY", mood);
        } catch (MoodAnalysisException e) {

        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenHappyMessage_WithNoProperMethod_ShouldThrow_Exception() {
        try {
            Object moodAnalyzer = MoodAnalyzerReflector.createMoodAnalyzer("I'm in a Happy mood");
            Object mood = MoodAnalyzerReflector.invokeMethod(moodAnalyzer, "analyseMood1");
            assertEquals("HAPPY", mood);
        } catch (MoodAnalysisException e) {
            assertEquals(exceptionType.NO_SUCH_METHOD, e.type);
        }
    }

    @Test
    public void givenHappyMessage_WithReflection_ShouldReturn_Happy() {
        try {
            Object moodAnalyzer = MoodAnalyzerReflector.createMoodAnalyzer("I'm in a Happy mood");
            Object mood = MoodAnalyzerReflector.invokeMethod(moodAnalyzer, "analyseMood");
            assertEquals("HAPPY", mood);
        } catch (MoodAnalysisException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenMoodAnalyserClass_WhenProper_ShouldReturn_Object() throws MoodAnalysisException {
        MoodAnalyzer moodAnalyzer = MoodAnalyzerReflector.createMoodAnalyzer("I'm in a Happy mood");
        try {
            String mood = moodAnalyzer.analyseMood();
            Assert.assertEquals("HAPPY", mood);
        } catch (MoodAnalysisException e) {
            e.printStackTrace();
        }
    }
}
