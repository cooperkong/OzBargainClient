package com.littlesword.ozbargain;

import com.littlesword.ozbargain.util.TimeUtil;

import org.junit.Test;

import static org.junit.Assert.*;
import static com.google.common.truth.Truth.assertThat;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testTimeUtil(){
        assertThat(true).isEqualTo(TimeUtil.isNew("11/12/2015 14:29","11/12/2015 14:34" ));
        assertThat(true).isEqualTo(TimeUtil.isNew("11/12/2016 14:29","11/12/2017 14:34" ));
        assertThat(true).isEqualTo(TimeUtil.isNew("11/10/2015 06:29","02/11/2015 14:34" ));
        assertThat(true).isEqualTo(TimeUtil.isNew("11/10/2015 06:29","02/11/2015 14:34" ));

        assertThat(false).isEqualTo(TimeUtil.isNew("11/10/2015 06:29","02/01/2015 14:34" ));
        assertThat(false).isEqualTo(TimeUtil.isNew("11/10/2015 06:29","11/10/2015 05:34" ));
        assertThat(false).isEqualTo(TimeUtil.isNew("11/10/2015 06:29","02/11/2014 14:34" ));
    }
}