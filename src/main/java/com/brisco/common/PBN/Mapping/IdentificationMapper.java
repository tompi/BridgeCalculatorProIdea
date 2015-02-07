package com.brisco.common.PBN.Mapping;

import java.io.InputStream;

import com.brisco.common.Game.Deal;
import com.brisco.common.PBN.Game.Identification;

public class IdentificationMapper {

	public static Identification GetIdentificationFromStream(
			InputStream identification) {
		// TODO
		return null;
	}

	public static void AppendIdentification(StringBuilder pbn,
			Identification identification) {
		if (identification == null) {
			return;
		}
		TagMapper.AppendTag(pbn, Identification.Tags.Event,
                identification.Event);
		TagMapper.AppendTag(pbn, Identification.Tags.Site, identification.Site);
		TagMapper.AppendTag(pbn, Identification.Tags.Date, identification.Date);
		TagMapper.AppendTag(pbn, Identification.Tags.Board,
                identification.Board);
		TagMapper.AppendTag(pbn, Identification.Tags.West, identification.West);
		TagMapper.AppendTag(pbn, Identification.Tags.North,
                identification.North);
		TagMapper.AppendTag(pbn, Identification.Tags.East, identification.East);
		TagMapper.AppendTag(pbn, Identification.Tags.South,
                identification.South);
		TagMapper.AppendTag(pbn, Identification.Tags.Dealer,
                identification.Dealer);
		TagMapper.AppendTag(pbn, Identification.Tags.Vulnerable,
                identification.Vulnerable);
		TagMapper.AppendTag(pbn, Identification.Tags.Deal,
                identification.Deal != null ? identification.Deal : new Deal());
		TagMapper.AppendTag(pbn, Identification.Tags.Scoring,
                identification.Scoring);
		TagMapper.AppendTag(pbn, Identification.Tags.Declarer,
                identification.Declarer);
		TagMapper.AppendTag(pbn, Identification.Tags.Contract,
                identification.Contract);
		TagMapper.AppendTag(pbn, Identification.Tags.Result,
                identification.Result);
	}
}
