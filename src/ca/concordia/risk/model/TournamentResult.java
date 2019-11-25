package ca.concordia.risk.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TournamentResult {

	private static TournamentResult instance = null;

	public Map<String, List<String>> results;

	public boolean end = false;

	private TournamentResult() {
		results = new HashMap<>();
	}

	public static TournamentResult getInstance() {
		if (instance == null) {
			instance = new TournamentResult();
		}
		return instance;
	}

}
