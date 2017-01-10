package com.ear.fantasy.football;

import com.ear.fantasy.football.parsers.PicksParser.AvailablePlayerHeader;
import com.ear.fantasy.football.parsers.PicksParser.ChosenPlayerHeader;

public class ParsedPlayer {
	
	public enum PlayerType {
		QB, RB, WR, TE, FLEX, DST,
	}
	
	private PlayerType _type;
	
	private ChosenPlayerHeader _chosenPlayerColumn;
	private int _id;
	private String _name;
	
	private AvailablePlayerHeader _availablePlayerColumn;
	private String _nameID;
	private int _salary;
	private String _gameInfo;
	private String _teamAbbrev;
	
	
	public ParsedPlayer(ChosenPlayerHeader col, int id, String name) {
		_chosenPlayerColumn = col;
		_id = id;
		_name = name;
	}
	
	public ParsedPlayer(String position, String nameID, String name, int id, int salary, String gameInfo, String abbrev) {
		//_availablePlayerColumn = col;
		try {_type = ParsedPlayer.PlayerType.valueOf(position); } catch(IllegalArgumentException e) {};// e.printStackTrace(); }
		_nameID = nameID;
		_id = id;
		_name = name;
		_salary = salary;
		_gameInfo = gameInfo;
		_teamAbbrev = abbrev;
	}
	
	public ChosenPlayerHeader getChosenPlayerHeader() {
		return _chosenPlayerColumn;
	}

	public AvailablePlayerHeader getAvailablePlayerHeader() {
		return _availablePlayerColumn;
	}

	public int getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}
	
	public String getNameID() {
		return _nameID;
	}
	
	public int getSalary() {
		return _salary;
	}

	public String getGameInfo() {
		return _gameInfo;
	}

	public String getTeamAbbrev() {
		return _teamAbbrev;
	}

	public PlayerType getType() {
		return _type;
	}
	
	public void setType(String type) {
		_type = PlayerType.valueOf(type);
	}
	
	@Override
	public String toString() {
		String ret = "";//_type==null ? "" : _type.name();
		
		if(_teamAbbrev == null) {
			ret += System.lineSeparator()+_chosenPlayerColumn.name()+" "+_name+" ("+_id+")";
		} else {
			ret += System.lineSeparator()+"\t"+_type+" "+_nameID+" "+_name+" "+_id+" "+_salary+" "+_gameInfo+" "+_teamAbbrev;
		}
		
		return ret;
	}
}
