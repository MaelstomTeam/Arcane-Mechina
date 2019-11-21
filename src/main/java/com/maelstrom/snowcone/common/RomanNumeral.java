package com.maelstrom.snowcone.common;

import java.util.TreeMap;

public class RomanNumeral {

	private final static TreeMap<Integer, String> number_map = new TreeMap<Integer, String>();

	static {

        number_map.put(1000, "M");
        number_map.put(900, "CM");
        number_map.put(500, "D");
        number_map.put(400, "CD");
        number_map.put(100, "C");
        number_map.put(90, "XC");
        number_map.put(50, "L");
        number_map.put(40, "XL");
        number_map.put(10, "X");
        number_map.put(9, "IX");
        number_map.put(5, "V");
        number_map.put(4, "IV");
        number_map.put(1, "I");

    }

	public final static String toRomanNumeral(int value) {
		if(value == 0)
			return "0";
		int l = number_map.floorKey(value);
		if (value == l) {
			return number_map.get(value);
		}
		return number_map.get(l) + toRomanNumeral(value - l);
	}

}