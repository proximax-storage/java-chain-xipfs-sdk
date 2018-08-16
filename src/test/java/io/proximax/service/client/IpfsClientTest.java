package io.proximax.service.client;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.multihash.Multihash;
import io.proximax.connection.IpfsConnection;
import io.proximax.exceptions.IpfsClientFailureException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class IpfsClientTest {

    public static final byte[] SAMPLE_DATA = "the quick brown fox jumps over the lazy dog".getBytes();
    public static final String SAMPLE_DATAHASH = "QmTxpkEitAczbM5S4uZG3zoDToSDNQZQUV4vxBsW9Q1Nhh";
    public static final MerkleNode SAMPLE_MERKLE_NODE = new MerkleNode(SAMPLE_DATAHASH);

    private IpfsClient unitUnderTest;

    @Mock
    private IpfsConnection mockIpfsConnection;

    @Mock
    private IPFS mockIpfs;

    @Mock
    private IPFS.Pin mockIpfsPin;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);

        unitUnderTest = new IpfsClient(mockIpfsConnection);
        given(mockIpfsConnection.getIpfs()).willReturn(mockIpfs);

        setMockIpfsPin();
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnAddWhenNullData() {
        unitUnderTest.add(null);
    }

    @Test(expected = IpfsClientFailureException.class)
    public void shouldBubbleUpExceptionOnAdd() throws IOException {
        given(mockIpfs.add(any())).willThrow(new RuntimeException());

        unitUnderTest.add(SAMPLE_DATA).blockingFirst();
    }

    @Test
    public void shouldReturnDataHashOnAdd() throws IOException {
        given(mockIpfs.add(any())).willReturn(asList(SAMPLE_MERKLE_NODE));

        final String dataHash = unitUnderTest.add(SAMPLE_DATA).blockingFirst();

        assertThat(dataHash, is(SAMPLE_DATAHASH));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnPinBlockWhenNullDataHash() {
        unitUnderTest.pin(null);
    }

    @Test(expected = IpfsClientFailureException.class)
    public void shouldBubbleUpExceptionOnPin() throws IOException {
        given(mockIpfsPin.add(any())).willThrow(new RuntimeException());

        unitUnderTest.pin(SAMPLE_DATAHASH).blockingFirst();
    }

    @Test
    public void shouldPin() throws IOException {
        given(mockIpfsPin.add(any())).willReturn(asList(Multihash.fromBase58(SAMPLE_DATAHASH)));

        final List<String> dataHashes = unitUnderTest.pin(SAMPLE_DATAHASH).blockingFirst();

        assertThat(dataHashes, is(notNullValue()));
        assertThat(dataHashes, hasSize(1));
        assertThat(dataHashes.get(0), is(SAMPLE_DATAHASH));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnGetWhenNullDataHash() {
        unitUnderTest.get(null);
    }

    @Test(expected = IpfsClientFailureException.class)
    public void shouldBubbleUpExceptionOnGet() throws IOException {
        given(mockIpfs.cat(any())).willThrow(new RuntimeException());

        unitUnderTest.get(SAMPLE_DATAHASH).blockingFirst();
    }

    @Test
    public void shouldReturnDataOnGet() throws IOException {
        given(mockIpfs.cat(any())).willReturn(SAMPLE_DATA);

        final byte[] data = unitUnderTest.get(SAMPLE_DATAHASH).blockingFirst();

        assertThat(data, is(SAMPLE_DATA));
    }

    private void setMockIpfsPin() throws NoSuchFieldException, IllegalAccessException {
        final Field field = IPFS.class.getDeclaredField("pin");
        field.setAccessible(true);
        final Field modifiersField  = Field.class.getDeclaredField("modifiers");
        modifiersField .setAccessible(true);
        modifiersField .setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(mockIpfs, mockIpfsPin);
    }
}
