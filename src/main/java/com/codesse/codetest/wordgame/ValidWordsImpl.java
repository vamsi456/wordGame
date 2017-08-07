package com.codesse.codetest.wordgame;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * Copyright (C) 2015 Codesse. All rights reserved.
 * ••••••••••••••••••••••••••••••••••••••••••••••••
 */
 
/*
* I can not completely explain for author what he would change.
* Because it depends from goals which author want to reach.
* If words will be adding to Vector  it is not a good idea to use Vector. For example, HashSet don`t need
* to check already existed words before adding. And Vector's method "contains" is very slow in comparison to other Collections.
* Also author have to close "reader" stream. Better way is using a new ability of Java 8 - try-catch with resources.
* Also Exception is very wide exception, better to use IOException that more suitable in this case.
* If this class will be used in applications it I would recommend use private field with getter-setter.
* */
 
public class ValidWordsImpl implements ValidWords {

	Vector v = new Vector();

	public ValidWordsImpl() {
		try {
			InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream("/wordlist.txt"), "utf-8");
			BufferedReader in = new BufferedReader(reader);
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				v.add(inputLine);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean contains(String word) {
		return v.contains(word);
	}

	@Override
	public int size() {
		return v.size();
	}
}
