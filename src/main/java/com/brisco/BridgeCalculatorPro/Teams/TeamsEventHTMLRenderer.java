package com.brisco.BridgeCalculatorPro.Teams;

import com.brisco.BridgeCalculatorPro.HTMLRenderer;
import com.brisco.common.Game.Board;
import com.brisco.common.Game.Contract;
import com.brisco.common.Game.Vulnerability;
import com.brisco.common.HTML.Mapping.AuctionMapper;
import com.brisco.common.HTML.Mapping.ContractMapper;
import com.brisco.common.HTML.Mapping.DealMapper;
import com.brisco.common.HTML.Mapping.VulnerabilityMapper;
import com.brisco.common.PBN.Mapping.DirectionMapper;
import com.brisco.common.Score.Calculator;
import com.brisco.common.Score.IMPCalculator;

public class TeamsEventHTMLRenderer extends HTMLRenderer {
	TeamsEvent _event;

	public TeamsEventHTMLRenderer(TeamsEvent event) {
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
		html.append("<center><h2>Teams Match</h2></center>");
		// Total score:
		RenderTotal(html);

		html.append("<table align=\"center\" cellspacing=\"0\" cellpadding=\"4\" border=\"1px\">");

		// Header
		RenderTHs(html);

		// Boards
		for (TeamsResult result : _event.GetResults(0)) {
			RenderResult(html, result);
		}

		html.append("</table>");
	}

	private void RenderTotal(StringBuilder html) {
		TeamsEventStats stats = new TeamsEventStats(_event);
		boolean lost = stats.IMP < 0;
		String color = lost ? "red" : "green";
		html.append("<table><tr><td><h3>Total score</h3></td></tr>");
		// VP
		html.append("<tr><td><b>VP</b></td><td><font color=\"" + color
				+ "\"><h3>");
		String vp = Integer.toString(stats.VP.WinnerVP);
		if (lost) {
			vp = Integer.toString(stats.VP.LoserVP) + " - " + vp;
		} else {
			vp = vp + " - " + Integer.toString(stats.VP.LoserVP);
		}
		html.append(vp + "</h3></font></td></tr>");

		// IMP
		html.append("<tr><td><b>IMP</b></td><td><font color=\"" + color + "\">"
				+ Integer.toString(stats.IMP) + "</font></td></tr>");

		// Stats
		appendIntTR(html, "Number of boards:", stats.TotalBoards);
		appendIntTR(html, "Scored boards:", stats.ScoredBoards);
		appendIntTR(html, "Won boards:", stats.WonBoards);
		appendIntTR(html, "Tied boards:", stats.TiedBoards);
		appendIntTR(html, "Lost boards:", stats.LostBoards);
		appendIntTR(html, "Hog-factor:", stats.PlayedPercentage);

		html.append("</table>");
	}

	private void appendIntTR(StringBuilder html, String label, int number) {
		html.append("<tr><td>" + label + "</td><td>" + number + "</td></tr>");

	}

	private void RenderResult(StringBuilder html, TeamsResult result) {
		RenderRoomResult(html, result, true);
		RenderRoomResult(html, result, false);

		// Cards and Auction
		if (result.Deal != null || result.OpenAuction != null
				|| result.ClosedAuction != null) {
			html.append("<tr><td colspan=\"6\">");
			html.append("<table align=\"center\">");
			html.append("<tr><td width=\"50%\">");
			DealMapper.AppendDeal(html, result.Deal,
					Board.GetDealer(result.BoardNumber),
					Board.GetVulnerability(result.BoardNumber));
			html.append("</td><td width=\"50%\">");
			AuctionMapper.AppendAuction(html, result.OpenAuction);
			AuctionMapper.AppendAuction(html, result.ClosedAuction);
			html.append("</td></tr></table></td></tr>");
		}
	}

	private void RenderRoomResult(StringBuilder html, TeamsResult result,
			boolean openRoom) {
		Vulnerability vulnerability = Board
				.GetVulnerability(result.BoardNumber);
		html.append("<tr>");
		if (openRoom) {
			html.append("<td rowspan=\"2\">"
					+ Integer.toString(result.BoardNumber) + "</td>");
			html.append("<td rowspan=\"2\">"
					+ VulnerabilityMapper
							.GetStringFromVulnerability(vulnerability)
					+ "</td>");
		}
		Contract contract = openRoom ? result.OpenContract
				: result.ClosedContract;
		if (contract == null) {
			html.append("<td></td><td></td><td></td>");
		} else {
			html.append("<td>" + ContractMapper.GetStringFromContract(contract)
					+ "</td>");
			html.append("<td>"
					+ DirectionMapper.GetStringFromDirection(contract.Player)
					+ "</td>");
			html.append("<td>" + Integer.toString(contract.Tricks) + "</td>");
			html.append("<td>"
					+ Integer.toString(Calculator.GetNorthSouthPoints(contract,
							result.BoardNumber)) + "</td>");
		}

		if (openRoom) {
			int nsIMP = 0;
			if (result.OpenContract != null && result.ClosedContract != null) {
				nsIMP = IMPCalculator.GetNorthSouthIMP(result.BoardNumber,
						result.OpenContract, result.ClosedContract);
			}

			// IMP:
			html.append("<td rowspan=\"2\">" + Integer.toString(nsIMP)
					+ "</td>");
		}
		html.append("</tr>");

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
		html.append("<th>IMP</th>");
		html.append("</tr>");
	}

}
