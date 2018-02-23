
package com.isobar.test.elevator.management.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

public class TestUtilities {

    public static void assertCanonical(Object instance, Object duplicate, Object other) {
        Object nullInstance = null;

        assertThat(instance, not(is(nullValue())));
        assertThat(duplicate, not(is(nullValue())));
        assertThat(other, not(is(nullValue())));

        assertThat(instance, is(instance));
        assertThat(instance, is(duplicate));
        assertThat(instance.equals(other), is(false));
        assertThat(instance.equals(nullInstance), is(false));
        assertThat(instance.hashCode(), is(duplicate.hashCode()));
    }
}
