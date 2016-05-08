package com.littlesword.ozbargain.util;

import com.littlesword.ozbargain.Injection;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by kongw1 on 11/03/16.
 */
public class PrivateConstructorTest {

    @Test
    public void callPrivateConstructorsForCodeCoverage() throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Class<?>[] classesToConstruct = {CommonUtil.class, AppUtil.class, Injection.class};
        for(Class<?> clazz : classesToConstruct)
        {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            assertNotNull(constructor.newInstance());
        }
    }
}
