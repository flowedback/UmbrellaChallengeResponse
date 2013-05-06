package eu.umbrellaid.service;


public interface ChallengeResponse {
	public String calculate(String key, String challenge, String rand);

	public String generateChallenge();

	public String handle(String hash, String challenge);
}
