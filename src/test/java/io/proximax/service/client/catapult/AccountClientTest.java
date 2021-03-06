package io.proximax.service.client.catapult;

import io.proximax.core.crypto.PublicKey;
import io.proximax.sdk.infrastructure.AccountHttp;
import io.proximax.sdk.model.account.AccountInfo;
import io.proximax.sdk.model.account.Address;
import io.proximax.exceptions.AccountNotFoundException;
import io.proximax.exceptions.PublicKeyNotFoundException;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static io.proximax.service.client.catapult.AccountClient.PUBLIC_KEY_NOT_FOUND;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

public class AccountClientTest {

    private static final String SAMPLE_ADDRESS = "SCW3N4V7LQ4UQBJK3PZGV3YGKMC67LLXYADF765M";
    private static final String SAMPLE_PUBLIC_KEY = "8E1A94D534EA6A3B02B0B967701549C21724C7644B2E4C20BF15D01D50097ACB";

    private AccountClient unitUnderTest;

    @Mock
    private AccountHttp mockAccountHttp;

    @Mock
    private AccountInfo mockAccountInfo;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        unitUnderTest = new AccountClient(mockAccountHttp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenMissingAddress() {
        unitUnderTest.getPublicKey(null);
    }

    @Test(expected = AccountNotFoundException.class)
    public void failWhenAccountHttpThrowsRuntimeException() {
        given(mockAccountHttp.getAccountInfo(Address.createFromRawAddress(SAMPLE_ADDRESS))).willThrow(new RuntimeException());

        unitUnderTest.getPublicKey(SAMPLE_ADDRESS);
    }

    @Test(expected = PublicKeyNotFoundException.class)
    public void failWhenAccountInfoHas000000PublicKey() {
        given(mockAccountHttp.getAccountInfo(Address.createFromRawAddress(SAMPLE_ADDRESS)))
                .willReturn(Observable.just(mockAccountInfo));
        given(mockAccountInfo.getPublicKey()).willReturn(PUBLIC_KEY_NOT_FOUND);

        unitUnderTest.getPublicKey(SAMPLE_ADDRESS);
    }

    @Test
    public void shouldReturnPublicKey() {
        given(mockAccountHttp.getAccountInfo(Address.createFromRawAddress(SAMPLE_ADDRESS)))
                .willReturn(Observable.just(mockAccountInfo));
        given(mockAccountInfo.getPublicKey()).willReturn(SAMPLE_PUBLIC_KEY);

        final PublicKey publicKey = unitUnderTest.getPublicKey(SAMPLE_ADDRESS);

        assertThat(publicKey, is(notNullValue()));
        assertThat(publicKey.toString().toUpperCase(), is(SAMPLE_PUBLIC_KEY));
    }
}
