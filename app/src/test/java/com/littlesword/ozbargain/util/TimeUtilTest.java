package com.littlesword.ozbargain.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.ParseException;
import java.util.HashSet;

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

    private HashSet<String> set = new HashSet<>();

    @Test
    public void testIsNew() throws ParseException {
        assertThat(true).isEqualTo(AppUtil.isNew("11/12/2015 14:29","11/12/2015 14:34" ));
        assertThat(true).isEqualTo(AppUtil.isNew("11/12/2016 14:29","11/12/2017 14:34" ));
        assertThat(true).isEqualTo(AppUtil.isNew("11/10/2015 06:29","02/11/2015 14:34" ));
        assertThat(true).isEqualTo(AppUtil.isNew("11/10/2015 06:29","02/11/2015 14:34" ));
        assertThat(false).isEqualTo(AppUtil.isNew("11/10/2015 06:29","02/01/2015 14:34" ));
        assertThat(false).isEqualTo(AppUtil.isNew("11/10/2015 06:29","11/10/2015 05:34" ));
        assertThat(false).isEqualTo(AppUtil.isNew("11/10/2015 06:29","02/11/2014 14:34" ));
    }

    @Test
    public void testIsWanted(){
        set.add("Gaming");
        set.add("Electrical & Electronics");
        set.add("Food & Grocery");
        assertThat(true).isEqualTo(AppUtil.isWanted("Gaming",set ));
        assertThat(false).isEqualTo(AppUtil.isWanted("Travel",set ));
        assertThat(true).isEqualTo(AppUtil.isWanted("Food & Grocery",set ));

    }


}