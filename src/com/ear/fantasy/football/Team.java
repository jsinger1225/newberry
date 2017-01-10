package com.ear.fantasy.football;

public class Team {

	private ParsedPlayer _QB;
	private ParsedPlayer _RB1;
	private ParsedPlayer _RB2;
	private ParsedPlayer _WR1;
	private ParsedPlayer _WR2;
	private ParsedPlayer _WR3;
	private ParsedPlayer _TE;
	private ParsedPlayer _FLEX;
	private ParsedPlayer _DST;
	
	public Team() {
		
	}
	
	public ParsedPlayer getQB() {
		return _QB;
	}
	
	public ParsedPlayer getRB1() {
		return _RB1;
	}
	
	public ParsedPlayer getRB2() {
		return _RB2;
	}
	
	public ParsedPlayer getWR1() {
		return _WR1;
	}
	
	public ParsedPlayer getWR2() {
		return _WR2;
	}
	
	public ParsedPlayer getWR3() {
		return _WR3;
	}
	
	public ParsedPlayer getTE() {
		return _TE;
	}
	
	public ParsedPlayer getFLEX() {
		return _FLEX;
	}
	
	public ParsedPlayer getDST() {
		return _DST;
	}
	
}
