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


}