package com.example.guest.weatherandroid;


import com.example.guest.weatherandroid.Services.AppService;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testEpochConversion(){
        long time = 1496275200;

        Date date = new Date(time * 1000);

        SimpleDateFormat format = new SimpleDateFormat("E", Locale.US);
        String text = format.format(date);
        assertEquals("Thu",text);
    }

    @Test
    public void testAppService(){
        AppService ser = new AppService();
        String num = ser.formatTemp(12.31233);
        assertEquals("12",num);
    }
}