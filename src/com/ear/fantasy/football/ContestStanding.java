package com.ear.fantasy.football;

public class ContestStanding {
	
	private static class Lineup {
		String _raw;
		Lineup(String s) {
			_raw = s;
		}
	};
	
	private int _rank;
	private String _entityId;
	private String _entryName;
	private int _timeRemaining;
	private float _points;
	private Lineup _lineup;
	private String _player;
	private String _percentDraft;
	private float _fpts;
	
	public ContestStanding(String rank, String entityId, String entryName, String timeRemaining, String points, String lineup, String player, String percentDraft, String fpts) {
		_rank = Integer.parseInt(rank);
		_entityId = entityId;
		_entryName = entryName;
		_timeRemaining = Integer.parseInt(timeRemaining);
		_points = Float.parseFloat(points);
		_lineup = new Lineup(lineup);
		_player = player;
		_percentDraft = percentDraft;
		_fpts = Float.parseFloat(fpts);
	}
	
	public int getRank() {
		return _rank;
	}

	public String getEntityId() {
		return _entityId;
	}
	
	public String getEntryName() {
		return _entryName;
	}
	
	public int getTimeRemaining() {
		return _timeRemaining;
	}

	public float getPoints() {
		return _points;
	}

	public String getPercent() {
		return _percentDraft;
	}

	public float getFPTS() {
		return _fpts;
	}
}
