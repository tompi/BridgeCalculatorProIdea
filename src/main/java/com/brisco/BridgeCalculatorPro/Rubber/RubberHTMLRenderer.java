package com.brisco.BridgeCalculatorPro.Rubber;

import java.util.ArrayList;
import java.util.List;

import com.brisco.BridgeCalculatorPro.HTMLRenderer;
import com.brisco.common.Game.Contract;
import com.brisco.common.Game.Direction;
import com.brisco.common.Game.Vulnerability;
import com.brisco.common.HTML.Mapping.ContractMapper;

public class RubberHTMLRenderer extends HTMLRenderer {
	List<Contract> _contracts;
	RubberResult _result;

	public RubberHTMLRenderer(List<Contract> contracts,
			RubberResult rubberResult) {
		_contracts = contracts;
		_result = rubberResult;
	}

	@Override
	protected void RenderBody(StringBuilder html) {
		html.append("<center><h2>Rubber</h2></center>");
		html.append("<table align=\"center\" cellspacing=\"8\" cellpadding=\"0\">");

		Vulnerability vulnearability = _result.GetVulnerability();

		// Header
		html.append("<tr>");
		StartTDTable(html,
				vulnearability.IsVulnerable(Direction.North) ? "cd2626"
						: "87aa14");
		int currentGame = _result.GetCurrentGame();
		Header(html, "Us", _result.NorthSouth.Score,
				_result.NorthSouth.GetLeg(currentGame));
		EndTDTable(html);
		StartTDTable(html,
				vulnearability.IsVulnerable(Direction.West) ? "cd2626"
						: "87aa14");
		Header(html, "Us", _result.EastWest.Score,
				_result.EastWest.GetLeg(currentGame));
		EndTDTable(html);
		html.append("</tr>");

		// Above Line
		RubberLine(html, _result.NorthSouth.AboveLine,
				_result.EastWest.AboveLine, "00A5DD");
		RubberLine(html, _result.NorthSouth.BelowLine1,
				_result.EastWest.BelowLine1, "937D05");
		RubberLine(html, _result.NorthSouth.BelowLine2,
				_result.EastWest.BelowLine2, "5E5003");
		RubberLine(html, _result.NorthSouth.BelowLine3,
				_result.EastWest.BelowLine3, "413701");

		html.append("</table>");
	}

	private void RubberLine(StringBuilder html, List<RubberScore> us,
			ArrayList<RubberScore> them, String color) {
		html.append("<tr>");
		RubberLineTD(html, us, color);
		RubberLineTD(html, them, color);
		html.append("</tr>");
	}

	private void RubberLineTD(StringBuilder html, List<RubberScore> scores,
			String color) {
		if (scores != null && scores.size() > 0) {
			StartTDTable(html, color);
			for (RubberScore score : scores) {
				html.append("<table><tr><td>");
				if (score.ContractNumber < 0) {
					html.append("Bonus");
				} else {
					html.append(ContractMapper.GetStringFromContract(_contracts
							.get(score.ContractNumber)));
				}
				html.append("</td><td align=\"right\">");
				html.append(Integer.toString(score.Score));
				html.append("</td></tr></table>");
			}
			EndTDTable(html);
		} else {
			html.append("<td></td>");
		}
	}

	private void Header(StringBuilder html, String who, int total, int leg) {
		html.append("<center><h3>" + who + "</h3></center>");
		html.append("<div align=\"right\">Leg: " + Integer.toString(leg)
				+ "</div>");
		html.append("<div align=\"right\"><b>Total: " + Integer.toString(total)
				+ "</b></div>");
	}

	private void StartTDTable(StringBuilder html, String bgColor) {
		html.append("<td width=\"50%\" valign=\"top\">");
		html.append("<table bgcolor=\"#" + bgColor
				+ "\" width=\"100%\" cellspacing=\"0\" cellpadding=\"4\">");
		html.append("<tr>");
		html.append("<td width=\"150em\">");
	}

	private void EndTDTable(StringBuilder html) {
		html.append("</td></tr></table></td>");
	}
}
