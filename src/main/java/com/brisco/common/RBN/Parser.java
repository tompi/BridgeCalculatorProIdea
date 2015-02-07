package com.brisco.common.RBN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.brisco.common.RBN.Mapping.RBNLineMapper;

public class Parser {

	public RBNArticle Parse(InputStream rbn) throws IOException {
		RBNArticle article = new RBNArticle();
		BufferedReader br = new BufferedReader(new InputStreamReader(rbn));
		String line = null;

		Hands currentHands = null;
		Auction currentAuction = null;
		while ((line = br.readLine()) != null) {
			RBNLine rbnLine = RBNLineMapper.GetRBNLine(line);
			if (rbnLine != null) {
				switch (rbnLine.GetRBNLineType()) {
				case Hands:
					currentHands = (Hands) rbnLine;
					article.Add(rbnLine);
					break;
				case Auction:
					currentAuction = (Auction) rbnLine;
					if (currentHands != null) {
						currentHands.Vulnerability = currentAuction.Vulnerability;
						currentHands.Dealer = currentAuction.Auction.Dealer;
					}
					article.Add(rbnLine);
					break;
				case BidExplanation:
					if (currentAuction != null) {
						currentAuction.Auction.Explanations
								.add(((BidExplanation) rbnLine).Explanation);
					}
					break;
				default:
					article.Add(rbnLine);
					break;
				}

			}
		}
		return article;
	}
}
