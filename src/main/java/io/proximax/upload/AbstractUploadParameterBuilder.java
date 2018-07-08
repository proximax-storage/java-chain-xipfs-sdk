package io.proximax.upload;


import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.nem.sdk.model.mosaic.Mosaic;
import io.proximax.steps.CommonUploadBuildSteps;
import io.proximax.steps.ReceiverPublicKeyStep;
import io.proximax.steps.SenderPrivateKeyStep;


/**
 * The Class AbstractUploadParameterBuilder.
 *
 * @param <NextBuildStepAfterPublicKey> the generic type
 * @param <FinalBuildSteps> the generic type
 */
public abstract class AbstractUploadParameterBuilder<NextBuildStepAfterPublicKey, FinalBuildSteps>
		implements
		CommonUploadBuildSteps<FinalBuildSteps>,
		SenderPrivateKeyStep,
		ReceiverPublicKeyStep {

	/** The instance. */
	protected AbstractUploadParameter instance;

	/**
	 * Instantiates a new abstract upload parameter builder.
	 *
	 * @param instance the instance
	 */
	protected AbstractUploadParameterBuilder(AbstractUploadParameter instance) {
		this.instance = instance;
	}

	/* (non-Javadoc)
	 * @see io.nem.xpx.builder.steps.MosaicsStep#mosaics(org.nem.core.model.mosaic.Mosaic[])
	 */
	@Override
	public FinalBuildSteps mosaics(Mosaic... mosaics) {
		instance.setMosaics(mosaics);
		return  (FinalBuildSteps) this;
	}

	/* (non-Javadoc)
	 * @see io.nem.xpx.builder.steps.KeywordsStep#keywords(java.lang.String)
	 */
	@Override
	public FinalBuildSteps keywords(String keywords) {
		this.instance.setKeywords(keywords);
		return  (FinalBuildSteps) this;
	}

	/* (non-Javadoc)
	 * @see io.nem.xpx.builder.steps.MetadataStep#metadata(java.util.Map)
	 */
	@Override
	public FinalBuildSteps metadata(Map<String, String> metadata) {
		this.instance.setMetaData(metadata);
		return  (FinalBuildSteps) this;
	}


	/* (non-Javadoc)
	 * @see io.nem.xpx.builder.steps.SenderPrivateKeyStep#senderPrivateKey(java.lang.String)
	 */
	@Override
	public ReceiverPublicKeyStep senderPrivateKey(String senderPrivateKeyStep) {
		this.instance.setSenderPrivateKey(senderPrivateKeyStep);
		return this;
	}

	/* (non-Javadoc)
	 * @see io.nem.xpx.builder.steps.ReceiverPublicKeyStep#receiverPublicKey(java.lang.String)
	 */
	@Override
	public NextBuildStepAfterPublicKey receiverPublicKey(String receiverPublicKey) {
		this.instance.setReceiverPublicKey(receiverPublicKey);
		return (NextBuildStepAfterPublicKey) this;
	}

}
