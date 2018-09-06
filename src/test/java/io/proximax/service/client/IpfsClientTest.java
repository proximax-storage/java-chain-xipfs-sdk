package io.proximax.service.client;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.multihash.Multihash;
import io.proximax.connection.IpfsConnection;
import io.proximax.exceptions.IpfsClientFailureException;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import static io.proximax.testsupport.Constants.PATH_FILE;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class IpfsClientTest {

    private static final byte[] SAMPLE_DATA = "the quick brown fox jumps over the lazy dog".getBytes();
    private static final String SAMPLE_DATAHASH = "QmTxpkEitAczbM5S4uZG3zoDToSDNQZQUV4vxBsW9Q1Nhh";
    private static final MerkleNode SAMPLE_MERKLE_NODE = new MerkleNode(SAMPLE_DATAHASH);

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
    public void failOnAddByteArrayWhenNullData() {
        unitUnderTest.addByteStream(null);
    }

    @Test(expected = IpfsClientFailureException.class)
    public void shouldBubbleUpExceptionOnAddByteArray() throws IOException {
        given(mockIpfs.add(any())).willThrow(new RuntimeException());

        unitUnderTest.addByteStream(new ByteArrayInputStream(SAMPLE_DATA)).blockingFirst();
    }

    @Test
    public void shouldReturnDataHashOnAddByteArray() throws IOException {
        given(mockIpfs.add(any())).willReturn(asList(SAMPLE_MERKLE_NODE));

        final String dataHash = unitUnderTest.addByteStream(new ByteArrayInputStream(SAMPLE_DATA)).blockingFirst();

        assertThat(dataHash, is(SAMPLE_DATAHASH));
    }
    @Test(expected = IllegalArgumentException.class)
    public void failOnAddPathWhenNullData() {
        unitUnderTest.addPath(null);
    }

    @Test(expected = IpfsClientFailureException.class)
    public void shouldBubbleUpExceptionOnAddPath() throws IOException {
        given(mockIpfs.add(any())).willThrow(new RuntimeException());

        unitUnderTest.addPath(PATH_FILE).blockingFirst();
    }

    @Test
    public void shouldReturnDataHashOnAddPath() throws IOException {
        given(mockIpfs.add(any())).willReturn(asList(new MerkleNode("QmXrrVBdauMZPNWfHMrtspaCUrfjWyXC2X17qJBykgC8fh"), SAMPLE_MERKLE_NODE));

        final String dataHash = unitUnderTest.addPath(PATH_FILE).blockingFirst();

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
        unitUnderTest.getByteStream(null);
    }

    @Test(expected = IpfsClientFailureException.class)
    public void shouldBubbleUpExceptionOnGet() throws IOException {
        given(mockIpfs.cat(any())).willThrow(new RuntimeException());

        unitUnderTest.getByteStream(SAMPLE_DATAHASH).blockingFirst();
    }

    @Test
    public void shouldReturnDataOnGet() throws IOException {
        given(mockIpfs.catStream(any())).willReturn(new ByteArrayInputStream(SAMPLE_DATA));

        final InputStream dataStream = unitUnderTest.getByteStream(SAMPLE_DATAHASH).blockingFirst();

        assertThat(IOUtils.toByteArray(dataStream), is(SAMPLE_DATA));
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
