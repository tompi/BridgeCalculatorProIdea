package com.brisco.common.RBN.Mapping;

import com.brisco.common.RBN.RBNLineType;

public class RBNLineTypeMapper {
	public static char GetChar(RBNLineType type) {
		switch (type) {
		case TitleAndAuthor:
			return 'T';
		case DateAndTime:
			return 'D';
		case Location:
			return 'L';
		case EventOrMainHeading:
			return 'E';
		case SessionStageOrSubheading:
			return 'S';
		case FormOfScoring:
			return 'F';
		case TeamNamesAndCarryovers:
			return 'K';
		case NamesOfPlayersAndRoomOrTable:
			return 'N';
		case BoardNumber:
			return 'B';
		case Hands:
			return 'H';
		case Auction:
			return 'A';
		case ContractAndDeclarer:
			return 'C';
		case PlaySequence:
			return 'P';
		case ResultTricksWonAndScore:
			return 'R';
		case InstantAwards:
			return 'I';
		case MakesAtDoubleDummy:
			return 'M';
		case Pause:
			return 'Q';
		case BidExplanation:
			return '*';
		default:
			return ' ';
		}
	}

	public static RBNLineType GetRBNLineType(char type) {
		switch (type) {
		case 'T':
			return RBNLineType.TitleAndAuthor;
		case 'D':
			return RBNLineType.DateAndTime;
		case 'L':
			return RBNLineType.Location;
		case 'E':
			return RBNLineType.EventOrMainHeading;
		case 'S':
			return RBNLineType.SessionStageOrSubheading;
		case 'F':
			return RBNLineType.FormOfScoring;
		case 'K':
			return RBNLineType.TeamNamesAndCarryovers;
		case 'N':
			return RBNLineType.NamesOfPlayersAndRoomOrTable;
		case 'B':
			return RBNLineType.BoardNumber;
		case 'H':
			return RBNLineType.Hands;
		case 'A':
			return RBNLineType.Auction;
		case 'C':
			return RBNLineType.ContractAndDeclarer;
		case 'P':
			return RBNLineType.PlaySequence;
		case 'R':
			return RBNLineType.ResultTricksWonAndScore;
		case 'I':
			return RBNLineType.InstantAwards;
		case 'M':
			return RBNLineType.MakesAtDoubleDummy;
		case 'Q':
			return RBNLineType.Pause;
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			return RBNLineType.BidExplanation;
		}
		return RBNLineType.Text;
	}
}
