package com.brisco.common.PBN.Mapping;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.brisco.common.Game.Contract;
import com.brisco.common.Game.Deal;
import com.brisco.common.Game.Direction;
import com.brisco.common.Game.Vulnerability;

public class TagMapper {
	private static SimpleDateFormat _dateFormat = new SimpleDateFormat(
			"yyyy.MM.dd");
	private static SimpleDateFormat _timeFormat = new SimpleDateFormat(
			"HH:mm:ss");

	public static void AppendTag(StringBuilder pbn, String tagName,
			String tagContent) {
		if (!isNullOrEmpty(tagContent)) {
			pbn.append("[" + tagName + " \"" + tagContent + "\"]\n");
		}
	}

	private static boolean isNullOrEmpty(String s) {
		if (s == null) {
			return true;
		}
		return (s.trim().length() == 0);
	}

	public static void AppendTag(StringBuilder pbn, String tagName, Date date) {
		if (date != null) {
			AppendTag(pbn, tagName, _dateFormat.format(date));
		}
	}

	public static void AppendTag(StringBuilder pbn, String tagName, int i) {
		if (i > -1) {
			AppendTag(pbn, tagName, Integer.toString(i));
		}
	}

	public static void AppendTag(StringBuilder pbn, String tagName,
			Direction direction) {
		if (direction != null) {
			AppendTag(pbn, tagName,
					DirectionMapper.GetStringFromDirection(direction));
		}
	}

	public static void AppendTag(StringBuilder pbn, String tagName,
			Vulnerability vulnerability) {
		if (vulnerability != null) {
			AppendTag(pbn, tagName,
					VulnerabilityMapper
							.GetStringFromVulnerability(vulnerability));
		}

	}

	public static void AppendTag(StringBuilder pbn, String tagName, Deal deal) {
		if (deal != null) {
			AppendTag(pbn, tagName, DealMapper.GetStringFromDeal(deal));
		}
	}

	public static void AppendTag(StringBuilder pbn, String tagName,
			Contract contract) {
		if (contract != null) {
			AppendTag(pbn, tagName,
					ContractMapper.GetStringFromContract(contract));
		}

	}

	public static void AppendTag(StringBuilder pbn, String tagName, boolean bool) {
		AppendTag(pbn, tagName, bool ? "Yes" : "No");

	}

	public static void AppendTag(StringBuilder pbn, String tagName, Date date,
			boolean timeOnly) {
		if (date != null) {
			AppendTag(pbn, tagName, _timeFormat.format(date));
		}
	}
}
