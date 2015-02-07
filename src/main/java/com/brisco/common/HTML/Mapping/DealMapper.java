package com.brisco.common.HTML.Mapping;

import java.util.List;

import com.brisco.common.Game.Card;
import com.brisco.common.Game.Deal;
import com.brisco.common.Game.Direction;
import com.brisco.common.Game.Hand;
import com.brisco.common.Game.Suit;
import com.brisco.common.Game.Vulnerability;
import com.brisco.common.PBN.Mapping.DenominationMapper;

public class DealMapper {

	public static void AppendCSSIncludes(StringBuilder html) {
		html.append("<style type=\"text/css\">\n" + DealCSS + "</style>");
	}

	public static void AppendDeal(StringBuilder html, Deal deal,
			Direction dealer, Vulnerability vulnerability) {
		if (deal == null) {
			return;
		}
		html.append("<div class=\"diagram\">");
		html.append("<div class=\"header\">");
		html.append("<div>Dealer: "
				+ DirectionMapper.GetStringFromDirection(dealer) + "</div>");
		html.append("<div>Vuln: "
				+ VulnerabilityMapper.GetStringFromVulnerability(vulnerability)
				+ "</div>");
		html.append("</div>");

		AppendHand(html, deal.North, "north", "North");
		AppendHand(html, deal.West, "west", "West");
		AppendHand(html, deal.East, "east", "East");
		AppendHand(html, deal.South, "south", "South");

		html.append("</div>");

	}

	private static void AppendHand(StringBuilder html, Hand hand,
			String cssClass, String handName) {
		html.append("<div class=\"hand " + cssClass + "\">");
		html.append("<div class=\"seatname\">" + handName + "</div>");

		AppendSuit(html, Suit.Spades, hand);
		AppendSuit(html, Suit.Hearts, hand);
		AppendSuit(html, Suit.Diamonds, hand);
		AppendSuit(html, Suit.Clubs, hand);

		html.append("</div>");
	}

	private static void AppendSuit(StringBuilder html, Suit suit, Hand hand) {
		html.append("<div class=\"holding\">");
		html.append(SuitMapper.GetStringFromSuit(suit));

		if (hand != null) {
			List<Card> cards = hand.GetCardsWithinSuit(suit);
			if (cards != null) {
				for (Card c : cards) {
					html.append(' ');
					html.append(DenominationMapper
							.GetCharFromDenomination(c.Denomination));
				}
			}
		}
		html.append("</div>");
	}

	private static String DealCSS = ".diagram .header {\n"
			+ "    font-size: smaller;\n" + "    margin-left: 0.25em;\n"
			+ "    margin-right: 0.25em;\n" + "    margin-bottom: 0;\n"
			+ "    margin-top: auto;\n" + "    padding: 0.25em;\n"
			+ "    border: 0.1em outset;\n" + "    width: 6em;\n"
			+ "    float: left;\n" + "    background: #FFFFFF;\n" + "}\n" +

			".diagram {\n" + "    width: 23em;\n" + "}\n" +

			".diagram  .hand {\n" + "    width: 8em;\n" + "}\n" +

			".diagram  .south , .diagram  .north {\n"
			+ "    margin-left: 7em; \n" + "}\n" +

			".diagram .north {\n" + "    padding-bottom: 1ex;\n" + "}\n" +

			".diagram  .south {\n" + "    clear: both;\n"
			+ "    padding-top: 1ex;\n" + "}\n" +

			".diagram  .west {\n" + "    float: left;\n" + "    clear: left;\n"
			+ "}\n" +

			".diagram  .east {\n" + "    float:right;\n" + "}\n" +

			".red {\n" + "    color: red;\n" + "}\n" +

			".hand .seatname {\n" + "    margin-left: 1em;\n"
			+ "    font-size: smaller;\n" + "    font-style: italic;\n" + "}\n";
}
