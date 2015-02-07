package com.brisco.BridgeCalculatorPro.ScoreSheet;

import com.brisco.BridgeCalculatorPro.HTMLRenderer;
import com.brisco.common.Game.Board;
import com.brisco.common.Game.Direction;
import com.brisco.common.Game.Vulnerability;
import com.brisco.common.HTML.Mapping.AuctionMapper;
import com.brisco.common.HTML.Mapping.ContractMapper;
import com.brisco.common.HTML.Mapping.DealMapper;
import com.brisco.common.HTML.Mapping.VulnerabilityMapper;
import com.brisco.common.PBN.Mapping.DirectionMapper;
import com.brisco.common.Score.Calculator;

public class ScoreSheetHTMLRenderer extends HTMLRenderer {
	Event _event;

	public ScoreSheetHTMLRenderer(Event event) {
		_event = event;
	}

	@Override
	protected void RenderIncludes(StringBuilder html) {
		super.RenderIncludes(html);
		DealMapper.AppendCSSIncludes(html);
	}

	@Override
	protected void RenderBodyTag(StringBuilder html) {
		html.append("<body>");
	}

	@Override
	protected void RenderBody(StringBuilder html) {
		html.append("<center><h2>Scoresheet</h2></center>");
		html.append("<table align=\"center\" cellspacing=\"0\" cellpadding=\"4\" border=\"1px\">");

		// Header
		RenderTHs(html);

		// Boards
		for (Result result : _event.GetResults()) {
			RenderResult(html, result);
		}

		html.append("</table>");
	}

	private void RenderResult(StringBuilder html, Result result) {
		Direction declarer = result.Contract.Player;
		Vulnerability vulnerability = Board
				.GetVulnerability(result.BoardNumber);
		html.append("<tr>");
		html.append("<td>" + Integer.toString(result.BoardNumber) + "</td>");
		html.append("<td>"
				+ VulnerabilityMapper.GetStringFromVulnerability(vulnerability)
				+ "</td>");
		html.append("<td>"
				+ ContractMapper.GetStringFromContract(result.Contract)
				+ "</td>");
		html.append("<td>" + DirectionMapper.GetStringFromDirection(declarer)
				+ "</td>");
		html.append("<td>" + Integer.toString(result.Contract.Tricks) + "</td>");
		html.append("<td>"
				+ Integer.toString(Calculator.GetNorthSouthPoints(
						result.Contract, result.BoardNumber)) + "</td>");
		html.append("</tr>");

		// Cards and Auction
		if (result.Deal != null || result.Auction != null) {
			html.append("<tr><td colspan=\"6\">");
			html.append("<table align=\"center\">");
			html.append("<tr><td width=\"50%\">");
			DealMapper.AppendDeal(html, result.Deal,
					Board.GetDealer(result.BoardNumber), vulnerability);
			html.append("</td><td width=\"50%\">");
			AuctionMapper.AppendAuction(html, result.Auction);
			html.append("</td></tr></table></td></tr>");
		}
	}

	private void RenderTHs(StringBuilder html) {
		html.append("<colgroup><col align=\"right\"/>");
		html.append("<col align=\"center\" span=\"3\"/>");
		html.append("<col align=\"right\" span=\"2\"/></colgroup>");
		html.append("<tr bgcolor=\"00A5DD\">");
		html.append("<th>Board no.</th>");
		html.append("<th>Vulnerability</th>");
		html.append("<th>Contract</th>");
		html.append("<th>Declarer</th>");
		html.append("<th>Tricks</th>");
		html.append("<th>Score</th>");
		html.append("</tr>");
	}

}
