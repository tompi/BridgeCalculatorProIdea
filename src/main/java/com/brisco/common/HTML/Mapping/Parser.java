package com.brisco.common.HTML.Mapping;

import java.util.List;

import com.brisco.common.Game.Hand;
import com.brisco.common.PBN.Game.Game;
import com.brisco.common.PBN.Mapping.HandMapper;
import com.brisco.common.PBN.Mapping.VulnerabilityMapper;

public class Parser {
	private static char separator = ',';

	// Custom CSV format for Mark Smits
	public static StringBuilder WriteCSV(List<Game> games) {
		StringBuilder csv = new StringBuilder();
		for (Game g : games) {
			AppendGame(g, csv);
		}
		return csv;
	}

	private static void AppendGame(Game g, StringBuilder csv) {
		String line = GetHand(g.Identification.Deal.North)
				+ GetHand(g.Identification.Deal.East)
				+ GetHand(g.Identification.Deal.South)
				+ GetHand(g.Identification.Deal.West)
				+ '"'
				+ VulnerabilityMapper
						.GetStringFromVulnerability(g.Identification.Vulnerable)
				+ '"' + separator + '"' + g.Identification.Board + '"'
				+ separator + '\n';
		csv.append(line);
	}

	private static String GetHand(Hand hand) {
		String pbn = HandMapper.GetStringFromHand(hand);
		return '"' + pbn.replace(".", "" + '"' + separator + '"') + '"'
				+ separator;
	}
}
