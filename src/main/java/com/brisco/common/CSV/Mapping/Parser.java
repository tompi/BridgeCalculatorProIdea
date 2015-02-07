package com.brisco.common.CSV.Mapping;

import com.brisco.common.Game.Deal;
import com.brisco.common.Game.Hand;
import com.brisco.common.Game.Vulnerability;
import com.brisco.common.HTML.Mapping.DirectionMapper;
import com.brisco.common.PBN.Game.Game;
import com.brisco.common.PBN.Mapping.HandMapper;
import com.brisco.common.PBN.Mapping.VulnerabilityMapper;

import java.util.List;

/**
 * Format used by "BigDeal" program
 */
public class Parser {
    private static char separator = ',';

    public static StringBuilder WriteCSV(List<Game> games) {
        StringBuilder csv = new StringBuilder();
        for (Game g : games) {
            AppendGame(g, csv);
        }
        return csv;
    }

    private static void AppendGame(Game g, StringBuilder csv) {
        Deal deal = g.Identification.Deal;
        if (deal == null) deal = new Deal();
        csv.append(GetHand(deal.North));
        csv.append(GetHand(deal.East));
        csv.append(GetHand(deal.South));
        csv.append(GetHand(deal.West));
        csv.append("\"" + g.Identification.Board + "\"" + separator);
        csv.append("\"" + DirectionMapper.GetStringFromDirection(g.Identification.Dealer));
        csv.append("/" + GetVulnerability(g.Identification.Vulnerable) + "\"");
        csv.append(separator + "\n");
    }

    private static String GetVulnerability(Vulnerability vulnerability) {
        switch (vulnerability) {
            case None:
                return "-";
            default:
                return VulnerabilityMapper
                        .GetStringFromVulnerability(vulnerability);
        }
    }

    private static String GetHand(Hand hand) {
		String pbn = HandMapper.GetStringFromHand(hand);
		return '"' + pbn.replace(".", "" + '"' + separator + '"') + '"'
				+ separator;
	}
}
