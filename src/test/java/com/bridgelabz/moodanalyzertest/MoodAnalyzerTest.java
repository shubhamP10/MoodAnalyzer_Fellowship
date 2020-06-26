package com.bridgelabz.moodanalyzertest;

import com.bridgelabz.moodanalyzer.MoodAnalyzer;
import com.bridgelabz.moodanalyzer.MoodAnalyzerReflector;
import com.bridgelabz.moodanalyzer.exception.MoodAnalysisException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
            assertEquals(exceptionType.ENTERED_NULL, e.type);
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

    @Test
    public void givenEmptyMood_ShouldThrow_Exception() {
        MoodAnalyzer moodAnalyzer = new MoodAnalyzer(" ");
        try {
            moodAnalyzer.analyseMood(" ");
        } catch (MoodAnalysisException e) {
            Assert.assertEquals(exceptionType.ENTERED_EMPTY, e.type);
        }
    }

    /*Using Reflection*/
    @Test
    public void givenMoodAnalyserClass_WhenProper_ShouldReturnObject() {
        try {
            MoodAnalyzer moodAnalyzer = MoodAnalyzerReflector.createMoodAnalyzer("com.bridgelabz.moodanalyzer.MoodAnalyzer",
                    "String", "I'm in a Happy mood");
            String mood = moodAnalyzer.analyseMood();
            Assert.assertEquals("HAPPY", mood);
        } catch (MoodAnalysisException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenMoodAnalyserClass_WhenNotProper_ShouldThrow_Exception() {
        try {
            MoodAnalyzer mood = MoodAnalyzerReflector.createMoodAnalyzer("com.bridgelabz.moodanalyzer.Mood",
                    "String",
                    "I'am in Happy Mood");
            Assert.assertEquals("HAPPY", mood);
        } catch (MoodAnalysisException e) {
            System.out.println(e.type);
            assertEquals(exceptionType.NO_SUCH_CLASS, e.type);
        }
    }

    @Test
    public void givenConstructor_WhenImproperParameters_ShouldThrow_Exception() {
        try {
            MoodAnalyzer moodAnalyzer = MoodAnalyzerReflector.createMoodAnalyzer("com.bridgelabz.moodanalyzer.Mood",
                    "Integer",
                    "I'am in Happy Mood");
        } catch (MoodAnalysisException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenConstructor_WhenNoParameters_ShouldThrow_DefaultConstructor() {
        try {
            MoodAnalyzer moodAnalyzer = MoodAnalyzerReflector.createMoodAnalyzer("com.bridgelabz.moodanalyzer.Mood");
        } catch (MoodAnalysisException e) {
            System.out.println(e.getMessage());
        }
    }

    /*Checking Proper Method*/
    @Test
    public void givenHappyMessage_WithReflection_ShouldReturn_Happy() {
        try {
            MoodAnalyzer moodAnalyzer = MoodAnalyzerReflector.createMoodAnalyzer("com.bridgelabz.moodanalyzer.MoodAnalyzer", "String",
                    "I am in Happy Mood");
            assertEquals("HAPPY", MoodAnalyzerReflector.invokeMethod(moodAnalyzer));
        } catch (MoodAnalysisException e) {
            System.out.println(e.getMessage());
        }
    }

    /*Checking Improper Method*/
    @Test
    public void givenHappyMessage_WithImproperMethod_ShouldThrow_Exception() {
        try {
            MoodAnalyzer moodAnalyzer = MoodAnalyzerReflector.createMoodAnalyzer("com.bridgelabz.moodanalyzer.MoodAnalyzer", "Integer",
                    "I am in Happy Mood");
            Object mood = MoodAnalyzerReflector.invokeMethod(moodAnalyzer);
            assertEquals("HAPPY", mood);
        } catch (MoodAnalysisException e) {
            System.out.println(e.type);
            assertEquals(exceptionType.NO_SUCH_METHOD, e.type);
        }
    }

    /*For Field Checking*/
    @Test
    public void givenMoodAnalyser_OnChangeMood_ShouldReturnHappy() {
        try {
            MoodAnalyzer moodAnalyzer = MoodAnalyzerReflector.createMoodAnalyzer("com.bridgelabz.moodanalyzer.MoodAnalyzer", "String",
                    "I am in Happy Mood");
            MoodAnalyzerReflector.setFieldValue(moodAnalyzer, "message", "I'm in Happy Mood");
            Object mood = MoodAnalyzerReflector.invokeMethod(moodAnalyzer);
            assertEquals("HAPPY", mood);
        } catch (MoodAnalysisException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenMoodAnalyser_OnChangeMood_WhenFieldIsNull_ShouldThrowException() {
        try {
            MoodAnalyzer moodAnalyzer = MoodAnalyzerReflector.createMoodAnalyzer("com.bridgelabz.moodanalyzer.MoodAnalyzer");
            MoodAnalyzerReflector.setFieldValue(moodAnalyzer, "message", null);
            assertEquals("HAPPY", MoodAnalyzerReflector.invokeMethod(moodAnalyzer));
        } catch (MoodAnalysisException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenInvalidFieldName_ShouldThrowException() {
        try {
            MoodAnalyzer moodAnalyzer = MoodAnalyzerReflector.createMoodAnalyzer("com.bridgelabz.moodanalyzer.MoodAnalyzer");
            MoodAnalyzerReflector.setFieldValue(moodAnalyzer, "Mood", "I am in Happy Mood");
            assertEquals("HAPPY", MoodAnalyzerReflector.invokeMethod(moodAnalyzer));
        } catch (MoodAnalysisException e) {
            System.out.println(e.getMessage());
        }
    }

}
