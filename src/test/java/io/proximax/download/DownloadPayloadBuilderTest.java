package io.proximax.download;

import java.io.IOException;
import java.util.Arrays;
import org.junit.Test;
import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;
import io.nem.sdk.infrastructure.TransactionHttp;
import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.model.ProximaxChildMessage;
import io.proximax.model.ProximaxMessage;
import io.proximax.utils.JsonUtils;

public class DownloadPayloadBuilderTest {

	@Test
	public void testDownloadPayloadAndAnnounce() {

		TransactionHttp transactionHttp;
		try {
			transactionHttp = new TransactionHttp("http://catapult.internal.proximax.io:3000");

			TransferTransaction transferTransaction = ((TransferTransaction) transactionHttp
					.getTransaction("CD685154DA25177ED538EAE81CD2A9EE239F4144DA072E6397879DCAA1201956")
					.blockingSingle());

			ProximaxMessage message = JsonUtils.fromJson(transferTransaction.getMessage().getPayload(),
					ProximaxMessage.class);

			IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
			
			System.out.println(new String(
					ipfs.cat(Multihash.fromBase58(message.getRootDataHash())),"UTF-8"));
			
			ProximaxChildMessage[] children = JsonUtils.fromJson(new String(
					ipfs.cat(Multihash.fromBase58(message.getRootDataHash())),"UTF-8"), ProximaxChildMessage[].class);
			message.setMessageFiles(Arrays.asList(children));
			
			System.out.println(JsonUtils.toJson(message));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
