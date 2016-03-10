package com.littlesword.ozbargain.util;

import android.app.Activity;
import android.content.res.Resources;

import com.littlesword.ozbargain.R;

import org.junit.Test;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;

/**
 * Created by kongw1 on 10/03/16.
 */
public class CommonUtilTest {

    @Test
    public void testApplyColorToString() throws Exception {
        String input = "this is $3";
        assertEquals("this is <font color=\"#aa3311\">$3</font>", CommonUtil.applyColorToString(input));
        String input2 = "this is $3.23";
        assertEquals("this is <font color=\"#aa3311\">$3.23</font>", CommonUtil.applyColorToString(input2));
        String input3 = "this is $132.2";
        assertEquals("this is <font color=\"#aa3311\">$132.2</font>", CommonUtil.applyColorToString(input3));
        String input4 = "this is $3.5 in a string";
        assertEquals("this is <font color=\"#aa3311\">$3.5</font> in a string", CommonUtil.applyColorToString(input4));
        String input5 = "$3.5 in a string";
        assertEquals("<font color=\"#aa3311\">$3.5</font> in a string", CommonUtil.applyColorToString(input5));
    }

    @Test
    public void testReadTextFile() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("sad");
        String s = CommonUtil.readTextFile(in);
        assertNotNull(s);
        assertTrue(s.length() > 0);
    }

    /**
     * for the sake of testing private constructors to increase coverage.
     * @throws Exception
     */
    @Test
    public void testPrivateConstructors() {
        final Constructor<?>[] constructors = CommonUtil.class.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        }
    }
}