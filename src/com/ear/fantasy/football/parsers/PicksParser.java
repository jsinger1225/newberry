package com.ear.fantasy.football.parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

import com.ear.fantasy.football.ParsedPlayer;
import com.opencsv.CSVReader;


public class PicksParser {

	private static class CSVFilenameFilter implements FilenameFilter {
		@Override
		public boolean accept(File dir, String name) {
			return dir.isDirectory() && dir.canRead() && 
					name.endsWith("csv") && name.startsWith("Week") && 
					new File(dir, name).canRead();
		}		
	};
	
	public enum ChosenPlayerHeader {
		QB,
		RB1,
		RB2,
		WR1,
		WR2,
		WR3,
		TE,
		FLEX,
		DST,
	};
	
	public enum AvailablePlayerHeader {
		Position,
		NameID,
		Name,
		ID,	 
		Salary,
		GameInfo,
		TeamAbbrev,
	};
	
	/**
	 * http://opencsv.sourceforge.net/
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			
			String csvDirStr = args==null || args==null || args.length==0 || args[0]==null ? "data" : args[0];
			File folder = new File(csvDirStr);
			System.out.println(System.lineSeparator()+"INFO: Searching ["+folder.getAbsolutePath()+"] for CSVs...");	
					
			File[] listOfCSVs = folder.listFiles(new CSVFilenameFilter());
			if(listOfCSVs == null) {
				throw new IOException("ERR: Invalid CSV directory ["+folder.getAbsolutePath()+"]");
			}
			
			for(int i = 0; i < listOfCSVs.length; i++) {
				System.out.println(System.lineSeparator()+"File [" + listOfCSVs[i].getAbsolutePath()+"]...");
		 		
				final int csvIdx = i;
				
				// kick off a Chosen Player parser
				new Thread(new Runnable() {				
					public void run() {
						try (CSVReader reader = new CSVReader(new FileReader(listOfCSVs[csvIdx].getAbsolutePath()))) {				
							parseChosenPlayers(reader);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();

				// kick off an Available Player parser
				new Thread(new Runnable() {	
					public void run() {						
						try (CSVReader reader = new CSVReader(new FileReader(listOfCSVs[csvIdx].getAbsolutePath()))) {				
							parseAvailablePlayers(reader);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();					
			}
		} catch(Exception e) {
			System.err.println("ERR: Unhandled exception");
			e.printStackTrace();
		}
	}
		
	private static void parseChosenPlayers(CSVReader reader) {
		ChosenPlayerHeader[] chosenPlayerHeaderRow = {
			ChosenPlayerHeader.QB, ChosenPlayerHeader.RB1, ChosenPlayerHeader.RB2, ChosenPlayerHeader.WR1, ChosenPlayerHeader.WR2, ChosenPlayerHeader.WR3, ChosenPlayerHeader.TE, ChosenPlayerHeader.FLEX, ChosenPlayerHeader.DST,
		};

		String[] nextLine;
		
		try {
			
			// skip top row headers
			nextLine = reader.readNext(); 
			
			// nextLine scans down the page, line by line
			while ((nextLine = reader.readNext()) != null) {
	
				// check each line for existence of header rows
				for(int col=0; col<chosenPlayerHeaderRow.length; col++) {
					String nextEntity = nextLine[col];
					
					// This a chosen player row if not empty
					if(!nextEntity.isEmpty()) {
						// parse players
						String playerName = nextEntity.substring(0, nextEntity.indexOf('(')).trim(); 
						String playerId = nextEntity.substring(nextEntity.indexOf('(')+1, nextEntity.indexOf(')')).trim();
						
						ParsedPlayer player = new ParsedPlayer(chosenPlayerHeaderRow[col], Integer.parseInt(playerId), playerName);
						System.out.println(player.toString());
					}							
				}							
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
			
	private static void parseAvailablePlayers(CSVReader reader) {
		AvailablePlayerHeader[] availableHeaderRow = {
			AvailablePlayerHeader.Position, AvailablePlayerHeader.NameID, AvailablePlayerHeader.Name, AvailablePlayerHeader.ID, AvailablePlayerHeader.Salary, AvailablePlayerHeader.GameInfo, AvailablePlayerHeader.TeamAbbrev,
		};

		int EMPTY_COL_COUNT = 10;
		String[] availableLine;
		
		try {

			// skip available row headers
			availableLine = reader.readNext(); 
			
			// availableLine scans down the page, line by line
			while ((availableLine = reader.readNext()) != null) {
	
				// check each line for existence of header rows
				for(int col=EMPTY_COL_COUNT; col<availableHeaderRow.length+EMPTY_COL_COUNT; col++) {
					String nextEntity = availableLine[col];
					
					// This an available player row if not empty
					if(!nextEntity.isEmpty()) {
						// parse players
						String position = nextEntity.trim();
						//AvailablePlayerHeader availablePlayerHeader = 
						String nameId = availableLine[col+1].trim();
						String playerName = availableLine[col+2].trim();
						int id = -1;
						try { id = Integer.parseInt(availableLine[col+3]); } catch(NumberFormatException nfe) {};//nfe.printStackTrace();}
						int salary = 0;
						try { salary = Integer.parseInt(availableLine[col+4]); } catch(NumberFormatException nfe) {};//nfe.printStackTrace();}
						String gameInfo = availableLine[col+5].trim();
						String teamAbbrev = availableLine[col+6].trim();
											
						ParsedPlayer player = new ParsedPlayer(position, nameId, playerName, id, salary, gameInfo, teamAbbrev);
						System.out.println(player.toString());
					}							
				}							
			}					

		} catch (IOException e) {
			e.printStackTrace();
		} 
	}		         
}
