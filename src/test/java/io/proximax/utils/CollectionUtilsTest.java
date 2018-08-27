package io.proximax.utils;

import org.junit.Test;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CollectionUtilsTest {

    @Test
    public void shouldReturnTrueOnIsEmptyWhenNullCollection() {
        final boolean result = CollectionUtils.isEmpty(null);

        assertThat(result, is(true));
    }

    @Test
    public void shouldReturnTrueOnIsEmptyWhenEmptyCollection() {
        final boolean result = CollectionUtils.isEmpty(emptyList());

        assertThat(result, is(true));
    }

    @Test
    public void shouldReturnFalseOnIsEmptyWhenCollectionHasContent() {
        final boolean result = CollectionUtils.isEmpty(singletonList("test"));

        assertThat(result, is(false));
    }

    @Test
    public void shouldReturnFalseOnIsNotEmptyWhenNullCollection() {
        final boolean result = CollectionUtils.isNotEmpty(null);

        assertThat(result, is(false));
    }

    @Test
    public void shouldReturnFalseOnIsNotEmptyWhenEmptyCollection() {
        final boolean result = CollectionUtils.isNotEmpty(emptyList());

        assertThat(result, is(false));
    }

    @Test
    public void shouldReturnTrueOnIsNotEmptyWhenCollectionHasContent() {
        final boolean result = CollectionUtils.isNotEmpty(singletonList("test"));

        assertThat(result, is(true));
    }
}
