package io.proximax.utils;

import org.junit.Test;

import static java.util.Arrays.asList;
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

    @Test
    public void shouldReturnCountOfNonNullObjectsOnNonNullCountWhenCollectionHasMixedContents() {
        final long result = CollectionUtils.nonNullCount(asList("test", null, "test2", "test3", null));

        assertThat(result, is(3L));
    }

    @Test
    public void shouldReturnCountOfNonNullObjectsOnNonNullCountWhenEmptyCollection() {
        final long result = CollectionUtils.nonNullCount(emptyList());

        assertThat(result, is(0L));
    }

    @Test
    public void shouldReturnFalseOnIsEqualListWhenSizeNotEqual() {
        final boolean result = CollectionUtils.isEqualList(emptyList(), singletonList("test"));

        assertThat(result, is(false));
    }

    @Test
    public void shouldReturnFalseOnIsEqualListWhenElementsDiffer() {
        final boolean result = CollectionUtils.isEqualList(asList("test1", "test3"), asList("test1", "test2"));

        assertThat(result, is(false));
    }

    @Test
    public void shouldReturnTrueOnIsEqualListWhenNoElementsDiffer() {
        final boolean result = CollectionUtils.isEqualList(asList("test1", "test2"), asList("test1", "test2"));

        assertThat(result, is(true));
    }

}
