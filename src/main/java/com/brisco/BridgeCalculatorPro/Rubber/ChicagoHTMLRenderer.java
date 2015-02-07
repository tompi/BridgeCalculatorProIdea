package com.brisco.BridgeCalculatorPro.Rubber;

import java.util.List;

import com.brisco.BridgeCalculatorPro.HTMLRenderer;
import com.brisco.common.Game.Board;
import com.brisco.common.Game.Contract;
import com.brisco.common.Game.Direction;
import com.brisco.common.Game.Vulnerability;
import com.brisco.common.HTML.Mapping.ContractMapper;

public class ChicagoHTMLRenderer extends HTMLRenderer {
	Chicago _chicago;

	public ChicagoHTMLRenderer(Chicago chicago) {
		_chicago = chicago;
	}

	@Override
	protected void RenderBody(StringBuilder html) {
		html.append("<center><h2>Chicago</h2></center>");
		html.append("<table align=\"center\" cellspacing=\"10\" cellpadding=\"0\">");

		List<Integer> scores = _chicago.GetScores();
		int ns = 0;
		int ew = 0;
		for (Integer score : scores) {
			if (score > 0) {
				ns += score;
			} else {
				ew += (-1 * score);
			}
		}

		// Header
		html.append("<tr>");
		StartTDTable(html, "00A5DD");
		Header(html, "We", ns);
		EndTDTable(html);
		StartTDTable(html, "00A5DD");
		Header(html, "They", ew);
		EndTDTable(html);
		html.append("</tr>");

		for (int i = 0; i < 4; i++) {
			Contract contract = null;
			int score = 0;
			if (i < scores.size()) {
				contract = _chicago.GetContract(i);
				score = scores.get(i);
			}
			ChicagoLine(html, i, contract, score);
		}
		html.append("</table>");
	}

	private void ChicagoLine(StringBuilder html, int i, Contract contract,
			int score) {
		html.append("<tr>");
		int usScore = 0;
		int themScore = 0;
		if (score > 0) {
			usScore = score;
		} else {
			themScore = (-1 * score);
		}
		Vulnerability vulnearability = Board.GetVulnerability(i + 1);
		RubberLineTD(html, contract, usScore, vulnearability, Direction.North,
				Direction.South);
		RubberLineTD(html, contract, themScore, vulnearability, Direction.West,
				Direction.East);
		html.append("</tr>");

	}

	private void RubberLineTD(StringBuilder html, Contract contract, int score,
			Vulnerability vulnearability, Direction direction1,
			Direction direction2) {
		StartTDTable(html, vulnearability.IsVulnerable(direction1) ? "cd2626"
				: "87aa14");
		html.append("<table><tr><td>");
		if (contract != null
				&& (contract.Player == direction1 || contract.Player == direction2)) {
			html.append(ContractMapper.GetStringFromContract(contract));
		} else {
			html.append("&nbsp;");
		}
		html.append("</td><td align=\"right\">");
		if (score > 0) {
			html.append(Integer.toString(score));
		} else {
			html.append("&nbsp;");
		}
		html.append("</td></tr></table>");
		EndTDTable(html);
	}

	private void Header(StringBuilder html, String who, int total) {
		html.append("<center><h3>" + who + "</h3></center>");
		html.append("<div align=\"right\"><b>Total: " + Integer.toString(total)
				+ "</b></div>");
	}

	private void StartTDTable(StringBuilder html, String bgColor) {
		html.append("<td width=\"50%\">");
		html.append("<table bgcolor=\"#" + bgColor
				+ "\" width=\"100%\" cellspacing=\"0\" cellpadding=\"3\">");
		html.append("<tr>");
		html.append("<td width=\"150em\">");
	}

	private void EndTDTable(StringBuilder html) {
		html.append("</td></tr></table></td>");
	}
}
