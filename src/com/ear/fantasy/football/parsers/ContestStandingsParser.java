package com.ear.fantasy.football.parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

import com.ear.fantasy.football.ContestStanding;
import com.ear.fantasy.football.ParsedPlayer;
import com.opencsv.CSVReader;


public class ContestStandingsParser {

	private static class ContestStandingsFilenameFilter implements FilenameFilter {
		@Override
		public boolean accept(File dir, String name) {
			File file = new File(dir, name);
			return dir.isDirectory() && dir.canRead() && 
					file.canRead() && name.endsWith("csv") && name.startsWith("contest-standings-");
		}
	};
	
	public enum Headers {
		Rank,
		EntryId,
		EntryName,
		TimeRemaining,
		Points,
		Lineups,
		_,
		Player,
		Drafted,
		FPTS
	};
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			
			String csvDirStr = args==null || args==null || args.length==0 || args[0]==null ? "data" : args[0];
			File folder = new File(csvDirStr);
			System.out.println(System.lineSeparator()+"INFO: Searching ["+folder.getAbsolutePath()+"] for CSVs...");	
					
			File[] listOfCSVs = folder.listFiles(new ContestStandingsFilenameFilter());
			if(listOfCSVs == null) {
				throw new IOException("ERR: Invalid contest standings directory ["+folder.getAbsolutePath()+"]");
			}
			
			for(int i = 0; i < listOfCSVs.length; i++) {
				System.out.println(System.lineSeparator()+"File [" + listOfCSVs[i].getAbsolutePath()+"]...");
		 		
				final int csvIdx = i;
				
				// kick off a Chosen Player parser
				new Thread(new Runnable() {				
					public void run() {
						try (CSVReader reader = new CSVReader(new FileReader(listOfCSVs[csvIdx].getAbsolutePath()))) {				
							parseContestStandings(reader);
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
		
	private static void parseContestStandings(CSVReader reader) {
		Headers[] headerRow = {
			Headers.Rank, Headers.EntryId, Headers.EntryName, Headers.TimeRemaining, Headers.Points, Headers.Lineups, Headers._, Headers.Player, Headers.Drafted, Headers.FPTS,
		};

		String[] nextLine;
		
		try {
			
			// skip top row headers
			nextLine = reader.readNext(); 
			
			// nextLine scans down the page, line by line
			while ((nextLine = reader.readNext()) != null) {
				
				String rank = nextLine[Headers.Rank.ordinal()]; 
				String entryId = nextLine[Headers.EntryId.ordinal()]; 
				String entryName = nextLine[Headers.EntryName.ordinal()]; 
				String timeRemaining = nextLine[Headers.TimeRemaining.ordinal()]; 
				String points = nextLine[Headers.Points.ordinal()]; 
				String lineups = nextLine[Headers.Lineups.ordinal()]; 
				String space = null;//nextLine[Headers.Lineups.ordinal()]; 
				String player = nextLine[Headers.Player.ordinal()]; 
				String drafted = nextLine[Headers.Drafted.ordinal()];
				String fpts = nextLine[Headers.FPTS.ordinal()]; 
						
				ContestStanding contestStanding = new ContestStanding(rank, entryId, entryName, timeRemaining, points, lineups, player, drafted, fpts);
				System.out.println(contestStanding.toString());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}		         
}
