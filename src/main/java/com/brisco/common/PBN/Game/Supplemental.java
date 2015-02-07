package com.brisco.common.PBN.Game;

import java.util.Date;

public class Supplemental {
	/**
	 * Describes the type of competition. Examples tag values are: "Cavendish",
	 * "Chicago", "Individuals", "Pairs", "Rubber", "Teams".
	 */
	public String Competition;

	/**
	 * Identifies a deal by a unique value. No two PBN deals should have the
	 * same tag value for DealId.
	 */
	public String DealId;

	/**
	 * The purpose of this tag is to give arbitrary game description. It can be
	 * used when no other tag is suited.
	 */
	public String Description;

	/**
	 * This tag indicates if French scoring is used, default is 'No'.
	 */
	public boolean FrenchMP = false;

	/**
	 * This tag indicates how the cards have been generated. It is intended for
	 * bridge computer programs and especially hand generators. The tag value
	 * may include the name of the program and possibly a seed value.
	 */
	public String Generator;

	/**
	 * This tag indicates the directions of the hands that should be hidden by a
	 * viewer after the PBN game has been loaded. The idea is to reveal the
	 * hands on user request, after the user has solved some task using only the
	 * visible cards. The tag value is a sequence of characters representing the
	 * directions to be hidden. The possible characters are: W (West), N
	 * (North), E (East), and S (South). For example: [Hidden "WE"] means, that
	 * initially the cards of E/W must be hidden.
	 */
	public String Hidden;

	/**
	 * This tag can be used in a teams bridge tournament. The only two possible
	 * tag values are: "Open" and "Closed".
	 */
	public String Room;

	/**
	 * This takes a string that describes the reason for the conclusion of the
	 * game. While the Result tag gives the result of the game, it does not
	 * provide any extra information and so the Termination tag is defined for
	 * this purpose. Examples are: "abandoned" abandoned game. "adjudication"
	 * result due to third party adjudication process "death" losing player
	 * called to greater things, one hopes "emergency" game concluded due to
	 * unforeseen circumstances "normal" game terminated in a normal fashion
	 * "rules infraction" administrative forfeit due to losing player's failure
	 * to observe either the Laws of Bridge or the event regulations
	 * "time forfeit" loss due to losing player's failure to meet time control
	 * requirements "unterminated" game not terminated
	 */
	public String Termination;

	/**
	 * This tag gives the number of points of the game based on the trick score
	 */
	public String Score;

	/**
	 * This tag gives the score in International MatchPoints based on the
	 * difference between score points
	 */
	public String ScoreIMP;

	/**
	 * This tag gives the MatchPoints based on the ranking of score points
	 */
	public String ScoreMP;

	/**
	 * This tag gives the percentage score.
	 */
	public String ScorePercentage;

	/**
	 * This tag gives the game's score for Rubber bridge or Chicago bridge
	 * <non-negative integer>/<non-negative integer> where the integer before
	 * "/" is the score above-the-line (bonus points or premium points), and the
	 * integer after "/" is the score below-the-line (trick points)
	 */
	public String ScoreRubber;

	/**
	 * This tag gives the scores above-the-line (bonus points or premium points)
	 * and the scores below-the-line (trick points) for both sides at the
	 * beginning of a deal in a rubber match. The syntax of the tag is:
	 * 
	 * [ScoreRubberHistory "NS <HistoryScore> EW <HistoryScore>"]
	 * 
	 * Each <HistoryScore> consists of 3, 4 or 5 score units (being non-negative
	 * integers), depending on the number of finished rubber games in the
	 * current rubber. The syntax of <HistoryScore> is:
	 * 
	 * <above_previous> <above_current>/<below-1> <below-2> <below-3>
	 * 
	 * where <above_previous> all above-the-line points in previous rubbers (if
	 * any) <above_current> all above-the-line points in the current rubber
	 * <below-n> the below-the-line points, either at begin of the current deal
	 * in game 'n' or at the end of finished game 'n'
	 * 
	 * During the first game, the values for <below-2> and <below-3> are
	 * omitted. During the second game, the values for <below-3> are omitted.
	 * The slash "/" separates the scores above-the-line from the scores
	 * below-the-line just as in tag ScoreRubber. Note that the number of games
	 * won defines the vulnerability. This must be consistent with the
	 * Vulnerable tag (if present).
	 */
	public String ScoreRubberHistory;

	/**
	 * This tag gives the optimum number of points of a deal based on the trick
	 * score of the optimum result, with open cards (double dummy). This result
	 * is computed by comparing all possible (doubled) contracts, including all
	 * passes. The number of tricks for a certain contract - and hence the
	 * corresponding score - may be derived from the OptimumResultTable. The
	 * optimum score can be given in 4 possible formats: "EW <score>" score of
	 * EW "NS <score>" score of NS "EW <score> NS <score>" score of EW resp. NS
	 * "NS <score> EW <score>" score of NS resp. EW where <score> is the integer
	 * number of points. Note that the NS <score> is opposite to the EW <score>.
	 * For example, if NS can make 3NT with 10 tricks and 5D with 11 tricks with
	 * scores of 630 respectively 600 and EW can play 5C doubled for a score of
	 * -500 then NS's optimum score is 600.
	 */
	public String OptimumScore;

	/*
	 * These tags describe the bidding system for each side
	 */
	public String BidSystemEW;
	/*
	 * These tags describe the bidding system for each side
	 */
	public String BidSystemNS;
	/**
	 * These tags describe the partnerships East/West and North/South. Suitable
	 * tag values are the names of the players, when it is unknown who is
	 * sitting North, East, South, West. Other suitable tag values are the
	 * values of the PairId_NS and PairId_EW columns from the ScoreTables, or
	 * the values of the PairId column from the TotalScoreTable
	 */
	public String PairEW;
	/**
	 * These tags describe the partnerships East/West and North/South. Suitable
	 * tag values are the names of the players, when it is unknown who is
	 * sitting North, East, South, West. Other suitable tag values are the
	 * values of the PairId_NS and PairId_EW columns from the ScoreTables, or
	 * the values of the PairId column from the TotalScoreTable
	 */
	public String PairNS;
	/**
	 * These tags are the e-mail or network addresses of the players.
	 */
	public String WestNA;
	/**
	 * These tags are the e-mail or network addresses of the players.
	 */
	public String NorthNA;
	/**
	 * These tags are the e-mail or network addresses of the players.
	 */
	public String EastNA;
	/**
	 * These tags are the e-mail or network addresses of the players.
	 */
	public String SouthNA;
	/**
	 * These tags describe the player types. Two typical example tag values are:
	 * "human" a human player "program" an algorithmic (computer) player
	 */
	public String WestType;
	/**
	 * These tags describe the player types. Two typical example tag values are:
	 * "human" a human player "program" an algorithmic (computer) player
	 */
	public String NorthType;
	/**
	 * These tags describe the player types. Two typical example tag values are:
	 * "human" a human player "program" an algorithmic (computer) player
	 */
	public String EastType;
	/**
	 * These tags describe the player types. Two typical example tag values are:
	 * "human" a human player "program" an algorithmic (computer) player
	 */
	public String SouthType;
	/**
	 * This tag describes the starting date of the event. The used format is the
	 * same as for the Date tag.
	 */
	public Date EventDate;
	/**
	 * This tag gives the name of the sponsor of the event.
	 */
	public String EventSponsor;
	/**
	 * This tag gives the name of the home team.
	 */
	public String HomeTeam;
	/**
	 * The Round tag value gives the playing round for the game. The round
	 * indicator consists of letter characters, digit characters and the
	 * underscore. Some organizers employ unusual round designations and have
	 * multipart playing rounds and sometimes even have conditional rounds. In
	 * these cases, a multipart round identifier can be made from a sequence of
	 * round indicators separated by periods. The leftmost indicator represents
	 * the most significant playing round, and succeeding indicators represent
	 * playing rounds in descending hierarchical order.
	 */
	public String Round;
	/**
	 * This tag is used for the playing section of a tournament. Examples are
	 * "Open", "Ladies" or "Reserve".
	 */
	public String Section;
	/**
	 * This tag is used for the stage of a multistage event. Examples are
	 * "Preliminary" or "Semifinal".
	 */
	public String Stage;
	/**
	 * This tag identifies the table in a tournament. The tag value will
	 * normally be a positive integer.
	 */
	public String Table;
	/**
	 * This tag gives the name of the visiting team.
	 */
	public String VisitTeam;
	/**
	 * This uses a time-of-day value in the form "HH:MM:SS"; similar to the Date
	 * tag except that it denotes the local clock time (hours, minutes, and
	 * seconds) of the start of the game. Note that colons, not periods, are
	 * used for field separators for the Time tag value. The value is taken from
	 * the local time corresponding to the location given in the Site tag pair.
	 */
	public Date Time;
	/**
	 * This tag is similar to the Date tag except that the date is given
	 * according to the Universal Coordinated Time standard.
	 */
	public Date UTCDate;
	/**
	 * This tag is similar to the Time tag except that the time is given
	 * according to the Universal Coordinated Time standard.
	 */
	public Date UTCTime;
	/**
	 * This tag indicates how many games must be played within a certain time
	 * limit. The tag value has the syntax: "<NrGames>/<NrMinutes>" This means
	 * that the number of games, given by <NrGames>, must be finished before a
	 * time in minutes, given by <NrMinutes>. For example: [TimeControl "4/30"]
	 * means, that 4 games must be finished in half an hour. When there is no
	 * time limit at all, then use "" as tag value.
	 */
	public String TimeControl;
	/**
	 * This tag is used to limit the time for making a single call. The tag
	 * value is defined in number of seconds. This tag can typically be applied
	 * for computer programs.
	 */
	public String TimeCall;
	/**
	 * This tag is used to limit the time for playing a single card. The tag
	 * value is defined in number of seconds. This tag can typically be applied
	 * for computer programs.
	 */
	public String TimeCard;
	/**
	 * This tag identifies the annotator or annotators of the game.
	 */
	public String Annotator;
	/**
	 * This tag is the e-mail or network addresses of the annotator.
	 */
	public String AnnotatorNA;
	/**
	 * The intention of this tag is to fill in a particular application
	 * (especially a computer program). It can be used as an anchor for
	 * Application specific data, that are added immediately after this tag in
	 * comments.
	 */
	public String Application;
	/**
	 * This tag gives the playing mode of the game. Examples are: "EM"
	 * electronic mail "IBS" Internet Bridge Server "OKB" OK Bridge "TABLE"
	 * normal table "TC" general telecommunication
	 */
	public String Mode;

	public static final class Tags {
		public static final String Competition = "Competition";
		public static final String DealId = "DealId";
		public static final String Description = "Description";
		public static final String FrenchMP = "FrenchMP";
		public static final String Generator = "Generator";
		public static final String Hidden = "Hidden";
		public static final String Room = "Room";
		public static final String Termination = "Termination";
		public static final String Score = "Score";
		public static final String ScoreIMP = "ScoreIMP";
		public static final String ScoreMP = "ScoreMP";
		public static final String ScorePercentage = "ScorePercentage";
		public static final String ScoreRubber = "ScoreRubber";
		public static final String ScoreRubberHistory = "ScoreRubberHistory";
		public static final String OptimumScore = "OptimumScore";
		public static final String BidSystemEW = "BidSystemEW";
		public static final String BidSystemNS = "BidSystemNS";
		public static final String PairEW = "PairEW";
		public static final String PairNS = "PairNS";
		public static final String WestNA = "WestNA";
		public static final String NorthNA = "NorthNA";
		public static final String EastNA = "EastNA";
		public static final String SouthNA = "SouthNA";
		public static final String WestType = "WestType";
		public static final String NorthType = "NorthType";
		public static final String EastType = "EastType";
		public static final String SouthType = "SouthType";
		public static final String EventDate = "EventDate";
		public static final String EventSponsor = "EventSponsor";
		public static final String HomeTeam = "HomeTeam";
		public static final String Round = "Round";
		public static final String Section = "Section";
		public static final String Stage = "Stage";
		public static final String Table = "Table";
		public static final String VisitTeam = "VisitTeam";
		public static final String Time = "Time";
		public static final String UTCDate = "UTCDate";
		public static final String UTCTime = "UTCTime";
		public static final String TimeControl = "TimeControl";
		public static final String TimeCall = "TimeCall";
		public static final String TimeCard = "TimeCard";
		public static final String Annotator = "Annotator";
		public static final String AnnotatorNA = "AnnotatorNA";
		public static final String Application = "Application";
		public static final String Mode = "Mode";

	}
}
