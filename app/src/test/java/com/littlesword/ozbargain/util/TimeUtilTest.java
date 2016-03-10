package com.littlesword.ozbargain.util;

import com.littlesword.ozbargain.util.TimeUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Constructor;
import java.text.ParseException;
import static com.google.common.truth.Truth.assertThat;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.fail;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class TimeUtilTest {
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public void testIsNew() throws ParseException {
        assertThat(true).isEqualTo(TimeUtil.isNew("11/12/2015 14:29","11/12/2015 14:34" ));
        assertThat(true).isEqualTo(TimeUtil.isNew("11/12/2016 14:29","11/12/2017 14:34" ));
        assertThat(true).isEqualTo(TimeUtil.isNew("11/10/2015 06:29","02/11/2015 14:34" ));
        assertThat(true).isEqualTo(TimeUtil.isNew("11/10/2015 06:29","02/11/2015 14:34" ));
        assertThat(false).isEqualTo(TimeUtil.isNew("11/10/2015 06:29","02/01/2015 14:34" ));
        assertThat(false).isEqualTo(TimeUtil.isNew("11/10/2015 06:29","11/10/2015 05:34" ));
        assertThat(false).isEqualTo(TimeUtil.isNew("11/10/2015 06:29","02/11/2014 14:34" ));
    }

    @Test
    public void testIsNewException() throws ParseException {
        thrown.expect(ParseException.class );
        TimeUtil.isNew("11-10-2015 06:29","02/11/2014 14:34" );
    }

    @Test(expected=IllegalAccessException.class)
    public void testConstructorPrivate() throws Exception {
        TimeUtil.class.newInstance();
        fail("Utility class constructor should be private");
    }

    /**
     * for the sake of testing private constructors to increase coverage.
     * @throws Exception
     */
    @Test
    public void constructorInaccessibilityTest() throws Exception {
        Constructor[] ctors = TimeUtil.class.getDeclaredConstructors();
        assertEquals("Utility class should only have one constructor",
                1, ctors.length);
        assertFalse("Utility class constructor should be inaccessible",
                ctors[0].isAccessible());
    }


}