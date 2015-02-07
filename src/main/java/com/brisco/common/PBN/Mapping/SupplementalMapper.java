package com.brisco.common.PBN.Mapping;

import java.io.InputStream;

import com.brisco.common.PBN.Game.Supplemental;

public class SupplementalMapper {

	public static Supplemental GetSupplementalFromStream(
			InputStream identification) {
		// TODO
		return null;
	}

	public static void AppendSupplemental(StringBuilder pbn,
			Supplemental supplemental) {
		if (supplemental == null) {
			return;
		}
		TagMapper.AppendTag(pbn, Supplemental.Tags.Competition,
                supplemental.Competition);
		TagMapper.AppendTag(pbn, Supplemental.Tags.DealId, supplemental.DealId);
		TagMapper.AppendTag(pbn, Supplemental.Tags.Description,
                supplemental.Description);
		if (supplemental.FrenchMP) {
			TagMapper.AppendTag(pbn, Supplemental.Tags.FrenchMP,
                    supplemental.FrenchMP);
		}
		TagMapper.AppendTag(pbn, Supplemental.Tags.Generator,
                supplemental.Generator);
		TagMapper.AppendTag(pbn, Supplemental.Tags.Hidden, supplemental.Hidden);
		TagMapper.AppendTag(pbn, Supplemental.Tags.Room, supplemental.Room);
		TagMapper.AppendTag(pbn, Supplemental.Tags.Termination,
                supplemental.Termination);
		TagMapper.AppendTag(pbn, Supplemental.Tags.Score, supplemental.Score);
		TagMapper.AppendTag(pbn, Supplemental.Tags.ScoreIMP,
                supplemental.ScoreIMP);
		TagMapper.AppendTag(pbn, Supplemental.Tags.ScoreMP,
                supplemental.ScoreMP);
		TagMapper.AppendTag(pbn, Supplemental.Tags.ScorePercentage,
                supplemental.ScorePercentage);
		TagMapper.AppendTag(pbn, Supplemental.Tags.ScoreRubber,
                supplemental.ScoreRubber);
		TagMapper.AppendTag(pbn, Supplemental.Tags.ScoreRubberHistory,
                supplemental.ScoreRubberHistory);
		TagMapper.AppendTag(pbn, Supplemental.Tags.OptimumScore,
                supplemental.OptimumScore);
		TagMapper.AppendTag(pbn, Supplemental.Tags.BidSystemEW,
                supplemental.BidSystemEW);
		TagMapper.AppendTag(pbn, Supplemental.Tags.BidSystemNS,
                supplemental.BidSystemNS);
		TagMapper.AppendTag(pbn, Supplemental.Tags.PairEW, supplemental.PairEW);
		TagMapper.AppendTag(pbn, Supplemental.Tags.PairNS, supplemental.PairNS);
		TagMapper.AppendTag(pbn, Supplemental.Tags.WestNA, supplemental.WestNA);
		TagMapper.AppendTag(pbn, Supplemental.Tags.NorthNA,
                supplemental.NorthNA);
		TagMapper.AppendTag(pbn, Supplemental.Tags.EastNA, supplemental.EastNA);
		TagMapper.AppendTag(pbn, Supplemental.Tags.SouthNA,
                supplemental.SouthNA);
		TagMapper.AppendTag(pbn, Supplemental.Tags.WestType,
                supplemental.WestType);
		TagMapper.AppendTag(pbn, Supplemental.Tags.NorthType,
                supplemental.NorthType);
		TagMapper.AppendTag(pbn, Supplemental.Tags.EastType,
                supplemental.EastType);
		TagMapper.AppendTag(pbn, Supplemental.Tags.SouthType,
                supplemental.SouthType);
		TagMapper.AppendTag(pbn, Supplemental.Tags.EventDate,
                supplemental.EventDate);
		TagMapper.AppendTag(pbn, Supplemental.Tags.EventSponsor,
                supplemental.EventSponsor);
		TagMapper.AppendTag(pbn, Supplemental.Tags.HomeTeam,
                supplemental.HomeTeam);
		TagMapper.AppendTag(pbn, Supplemental.Tags.Round, supplemental.Round);
		TagMapper.AppendTag(pbn, Supplemental.Tags.Section,
                supplemental.Section);
		TagMapper.AppendTag(pbn, Supplemental.Tags.Stage, supplemental.Stage);
		TagMapper.AppendTag(pbn, Supplemental.Tags.Table, supplemental.Table);
		TagMapper.AppendTag(pbn, Supplemental.Tags.VisitTeam,
                supplemental.VisitTeam);
		TagMapper.AppendTag(pbn, Supplemental.Tags.Time, supplemental.Time,
                true);
		TagMapper.AppendTag(pbn, Supplemental.Tags.UTCDate,
                supplemental.UTCDate);
		TagMapper.AppendTag(pbn, Supplemental.Tags.UTCTime,
                supplemental.UTCTime, true);
		TagMapper.AppendTag(pbn, Supplemental.Tags.TimeControl,
                supplemental.TimeControl);
		TagMapper.AppendTag(pbn, Supplemental.Tags.TimeCall,
                supplemental.TimeCall);
		TagMapper.AppendTag(pbn, Supplemental.Tags.TimeCard,
                supplemental.TimeCard);
		TagMapper.AppendTag(pbn, Supplemental.Tags.Annotator,
                supplemental.Annotator);
		TagMapper.AppendTag(pbn, Supplemental.Tags.AnnotatorNA,
                supplemental.AnnotatorNA);
		TagMapper.AppendTag(pbn, Supplemental.Tags.Application,
                supplemental.Application);
		TagMapper.AppendTag(pbn, Supplemental.Tags.Mode, supplemental.Mode);
	}
}
