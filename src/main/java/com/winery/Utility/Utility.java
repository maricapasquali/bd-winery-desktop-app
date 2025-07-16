package com.winery.Utility;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.Calendar;
import javax.swing.JFileChooser;

import com.winery.View.Personalmenu.AbstractFrameDefault;

public final class Utility {

	private static final String SPLIT = " - ";
	private static final String SPLIT_USER_NAME = ".";

	private Utility() {
	}
	
	public static Double round2(final Double val) {
	    return new BigDecimal(val.toString()).setScale(2,RoundingMode.HALF_UP).doubleValue();
	}
	
	public static void log(final Object obj) {
		System.out.println(obj);
	}

	public static void logError(final Object obj) {
		System.err.println(obj);
	}

	public static Date dateNow() {
		return new Date(Calendar.getInstance().getTime().getTime());
	}

	public static Date dateSql(final java.util.Date date) {
		return new Date(date.getTime());
	}

	public static int dataPart(final Date d, final int fieldDate) {
		final Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(fieldDate);
	}

	public static Date createDate(final int fieldDate, final int value) {
		final Calendar c = Calendar.getInstance();
		c.set(fieldDate, value);
		return dateSql(c.getTime());
	}

	public static int yearNow() {
		return Utility.dataPart(Utility.dateNow(), Calendar.YEAR);
	}

	public static void checkLength(final String text) {
		check(text.length() < 1);
	}

	public static void checkNumber(final Number number) {
		check(number.doubleValue() == 0);
	}

	public static void check(final boolean cond) {
		if (cond) {
			throw new NullPointerException();
		}
	}

	public static File dbChoosen(final AbstractFrameDefault f) {
		final JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir")));
		File db = null;
		int returnVal = chooser.showOpenDialog(f);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			db = chooser.getSelectedFile();
		}
		return db;
	}
		
	public static String getSplit() {
		return SPLIT;
	}

	public static String getSplitUserName() {
		return SPLIT_USER_NAME;
	}
}
