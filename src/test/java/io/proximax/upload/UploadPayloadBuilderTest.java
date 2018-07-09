package io.proximax.upload;

import java.io.IOException;
import java.math.BigInteger;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import org.junit.Test;

import io.ipfs.api.IPFS;
import io.nem.sdk.infrastructure.TransactionHttp;
import io.nem.sdk.model.account.Account;
import io.nem.sdk.model.account.Address;
import io.nem.sdk.model.blockchain.NetworkType;
import io.nem.sdk.model.mosaic.Mosaic;
import io.nem.sdk.model.mosaic.XEM;
import io.nem.sdk.model.transaction.Deadline;
import io.nem.sdk.model.transaction.PlainMessage;
import io.nem.sdk.model.transaction.SignedTransaction;
import io.nem.sdk.model.transaction.TransactionAnnounceResponse;
import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.builder.UploadPayloadBuilder;
import io.proximax.model.ProximaxMessage;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.utils.JsonUtils;

public class UploadPayloadBuilderTest {

	@Test
	public void testUploadPayload() {

		try {
			ProximaxMessage param = UploadPayloadBuilder
					.create(new IPFS("/ip4/127.0.0.1/tcp/5001"))
					.data("hello")
					.privacyType(PrivacyStrategy.PLAIN)
					.addFileOrResource("aaaaaa1".getBytes(), "name")
					.addFileOrResource("aaaaaa2".getBytes(), "name1")
					.addFileOrResource("aaaaaa3".getBytes(), "name1")
					.addFileOrResource("aaaaaa4".getBytes(), "name1")
					.addFileOrResource("aaaaaa5".getBytes(), "name1")
					.addFileOrResource("aaaaaa6".getBytes(), "name1")
					.addFileOrResource("aaaaaa7".getBytes(), "name1")
					.addFileOrResource("aaaaaa8".getBytes(), "name1")
					.addFileOrResource("aaaaaa9".getBytes(), "name1")
					.addFileOrResource("aaaaaa10".getBytes(), "name1")
					.addFileOrResource("aaaaaa1".getBytes(), "name")
					.addFileOrResource("aaaaaa2".getBytes(), "name1")
					.addFileOrResource("aaaaaa3".getBytes(), "name1")
					.addFileOrResource("aaaaaa4".getBytes(), "name1")
					.addFileOrResource("aaaaaa5".getBytes(), "name1")
					.addFileOrResource("aaaaaa6".getBytes(), "name1")
					.addFileOrResource("aaaaaa7".getBytes(), "name1")
					.addFileOrResource("aaaaaa8".getBytes(), "name1")
					.addFileOrResource("aaaaaa9".getBytes(), "name1")
					.addFileOrResource("aaaaaa10".getBytes(), "name1")
					.addFileOrResource("aaaaaa1".getBytes(), "name")
					.addFileOrResource("aaaaaa2".getBytes(), "name1")
					.addFileOrResource("aaaaaa3".getBytes(), "name1")
					.addFileOrResource("aaaaaa4".getBytes(), "name1")
					.addFileOrResource("aaaaaa5".getBytes(), "name1")
					.addFileOrResource("aaaaaa6".getBytes(), "name1")
					.addFileOrResource("aaaaaa7".getBytes(), "name1")
					.addFileOrResource("aaaaaa8".getBytes(), "name1")
					.addFileOrResource("aaaaaa9".getBytes(), "name1")
					.addFileOrResource("aaaaaa10".getBytes(), "name1")
					.addFileOrResource("aaaaaa1".getBytes(), "name")
					.addFileOrResource("aaaaaa2".getBytes(), "name1")
					.addFileOrResource("aaaaaa3".getBytes(), "name1")
					.addFileOrResource("aaaaaa4".getBytes(), "name1")
					.addFileOrResource("aaaaaa5".getBytes(), "name1")
					.addFileOrResource("aaaaaa6".getBytes(), "name1")
					.addFileOrResource("aaaaaa7".getBytes(), "name1")
					.addFileOrResource("aaaaaa8".getBytes(), "name1")
					.addFileOrResource("aaaaaa9".getBytes(), "name1")
					.addFileOrResource("aaaaaa10".getBytes(), "name1")
					.addFileOrResource("aaaaaa1".getBytes(), "name")
					.addFileOrResource("aaaaaa2".getBytes(), "name1")
					.addFileOrResource("aaaaaa3".getBytes(), "name1")
					.addFileOrResource("aaaaaa4".getBytes(), "name1")
					.addFileOrResource("aaaaaa5".getBytes(), "name1")
					.addFileOrResource("aaaaaa6".getBytes(), "name1")
					.addFileOrResource("aaaaaa7".getBytes(), "name1")
					.addFileOrResource("aaaaaa8".getBytes(), "name1")
					.addFileOrResource("aaaaaa9".getBytes(), "name1")
					.addFileOrResource("aaaaaa10".getBytes(), "name1")
					.addFileOrResource("aaaaaa1".getBytes(), "name")
					.addFileOrResource("aaaaaa2".getBytes(), "name1")
					.addFileOrResource("aaaaaa3".getBytes(), "name1")
					.addFileOrResource("aaaaaa4".getBytes(), "name1")
					.addFileOrResource("aaaaaa5".getBytes(), "name1")
					.addFileOrResource("aaaaaa6".getBytes(), "name1")
					.addFileOrResource("aaaaaa7".getBytes(), "name1")
					.addFileOrResource("aaaaaa8".getBytes(), "name1")
					.addFileOrResource("aaaaaa9".getBytes(), "name1")
					.addFileOrResource("aaaaaa10".getBytes(), "name1")
					.addFileOrResource("aaaaaa1".getBytes(), "name")
					.addFileOrResource("aaaaaa2".getBytes(), "name1")
					.addFileOrResource("aaaaaa3".getBytes(), "name1")
					.addFileOrResource("aaaaaa4".getBytes(), "name1")
					.addFileOrResource("aaaaaa5".getBytes(), "name1")
					.addFileOrResource("aaaaaa6".getBytes(), "name1")
					.addFileOrResource("aaaaaa7".getBytes(), "name1")
					.addFileOrResource("aaaaaa8".getBytes(), "name1")
					.addFileOrResource("aaaaaa9".getBytes(), "name1")
					.addFileOrResource("aaaaaa10".getBytes(), "name1")
					.build();

			System.out.println(JsonUtils.toJson(param));
			System.out.println(param.toPayload());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testUploadPayloadAndAnnounce() {
		final String recipientAddress = "SDMY4KUCB2MOUPB6DO3TIVGZWAK7GJ6OEVPOGDMM";
		ProximaxMessage param;
		try {
			
			param = UploadPayloadBuilder
					.create(new IPFS("/ip4/127.0.0.1/tcp/5001"))
					.data("hello")
					.privacyType(PrivacyStrategy.PLAIN)
					.addFileOrResource("aaaaaa1".getBytes(), "name1")
					.addFileOrResource("aaaaaa2".getBytes(), "name2")
					.addFileOrResource("aaaaaa3".getBytes(), "name3")
					.addFileOrResource("aaaaaa4".getBytes(), "name4")
					.addFileOrResource("aaaaaa5".getBytes(), "name5")
					.addFileOrResource("aaaaaa6".getBytes(), "name6")
					.addFileOrResource("aaaaaa7".getBytes(), "name7")
					.addFileOrResource("aaaaaa8".getBytes(), "name8")
					.addFileOrResource("aaaaaa9".getBytes(), "name9","Addion","text/plain")
					.addFileOrResource("aaaaaa10".getBytes(), "name10","Additional Data")
					.build();

			//	Build TT.
			final TransferTransaction transferTransaction = TransferTransaction.create(
					Deadline.create(24, ChronoUnit.HOURS), 
					Address.createFromRawAddress(recipientAddress),
					Collections.singletonList(
							new Mosaic(XEM.createRelative(BigInteger.valueOf(1)).getId(), BigInteger.valueOf(1))),
					PlainMessage.create(param.toPayload()), // attach proximax object
					NetworkType.MIJIN_TEST);
			
			// Sign
			final String privateKey = "4BC6EB63F728DB1A72B3AB1462204468F1DCB4F3A62DE6DC44BA039C9B5885D1";
			final Account account = Account.createFromPrivateKey(privateKey, NetworkType.MIJIN_TEST);
			final SignedTransaction signedTransaction = account.sign(transferTransaction);
			final TransactionHttp transactionHttp = new TransactionHttp("http://catapult.internal.proximax.io:3000");

			// Announce
			TransactionAnnounceResponse response = transactionHttp.announce(signedTransaction).toFuture().get();
			System.out
					.println(Address.createFromRawAddress("SDMY4KUCB2MOUPB6DO3TIVGZWAK7GJ6OEVPOGDMM").getNetworkType());
			System.out.println("Announced" + response.getMessage() + " " + signedTransaction.getHash());
		} catch (IOException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
