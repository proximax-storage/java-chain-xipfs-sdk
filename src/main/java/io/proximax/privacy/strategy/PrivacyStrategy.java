package io.proximax.privacy.strategy;

public enum PrivacyStrategy {

	PLAIN(1001), NEM_KEYS(1002),

	SHAMIR(1003), PASSPHRASE(1004);

	private final int value;

	private PrivacyStrategy(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
