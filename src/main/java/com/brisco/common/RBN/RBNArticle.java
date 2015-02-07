package com.brisco.common.RBN;

import java.util.ArrayList;

public class RBNArticle {
	public RBNArticle() {
		Lines = new ArrayList<RBNLine>();
	}

	public ArrayList<RBNLine> Lines;

	public String Author;
	public String Title;

	public void Add(RBNLine line) {
		if (line == null)
			return;

		RBNLineType type = line.GetRBNLineType();
		switch (type) {
		case TitleAndAuthor:
			TitleAndAuthor ta = (TitleAndAuthor) line;
			Author = ta.Author;
			Title = ta.Title;
			return;
		default:
			Lines.add(line);
		}
	}
}
