package cn.daycode.fatalism.common.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Random;

public class BankCardNumberUtil {

	private static BankCardNumberUtil instance = new BankCardNumberUtil();

	public static String get() {
		BankNameEnum[] nameEnums = BankNameEnum.values();
		return instance.generate(nameEnums[new Random().nextInt(nameEnums.length)], BankCardTypeEnum.DEBIT);
	}

	public static void main(String[] args) {
		while (true) {
			System.out.println(get());
		}
	}


	public String generateByPrefix(Integer prefix) {
		Random random = new Random(System.currentTimeMillis());
		String bardNo = prefix + StringUtils.leftPad(random.nextInt(999999999) + "", 9, "0");

		char[] chs = bardNo.trim().toCharArray();
		int luhnSum = getLuhnSum(chs);
		char checkCode = luhnSum % 10 == 0 ? '0' : (char) (10 - luhnSum % 10 + '0');
		return bardNo + checkCode;
	}


	public String generate(BankNameEnum bankName, BankCardTypeEnum cardType) {
		Integer[] candidatePrefixes = null;
		if (cardType == null) {
			candidatePrefixes = bankName.getAllCardPrefixes();
		} else {
			switch (cardType) {
				case DEBIT:
					candidatePrefixes = bankName.getDebitCardPrefixes();
					break;
				case CREDIT:
					candidatePrefixes = bankName.getCreditCardPrefixes();
					break;
				default:
			}
		}

		if (candidatePrefixes == null || candidatePrefixes.length == 0) {
			throw new RuntimeException("don't have related card info for specific bank card");
		}

		Integer prefix = candidatePrefixes[new Random().nextInt(candidatePrefixes.length)];
		return generateByPrefix(prefix);
	}


	private int getLuhnSum(char[] chs) {
		int luhnSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhnSum += k;
		}
		return luhnSum;
	}

	
	enum BankCardTypeEnum {
	
		DEBIT("debit card"),
	
		CREDIT("card");

		private final String name;

		BankCardTypeEnum(String name) {
			this.name = name;
		}
	}


	enum BankNameEnum {
		ICBC("102", "ICBC", new Integer[]{622200, 622230, 622235, 622210, 622215}),
		BOC("104", "BOC", new Integer[]{622752}),
		CCB("105", "CCB", new Integer[]{622280}),
		BCOM("301", "BCOM", new Integer[]{622260}),
		CITIC("302", "CITIC", new Integer[]{622690}),
		CEB("303", "CEB", new Integer[]{622655,622650,622658}),
		HXB("304", "HXB", new Integer[]{622636, 622637}),
		CMBC("305", "CMBC", new Integer[]{622600,622601,622602,622603}),
		CGB("306", "CGB", new Integer[]{622568}),
		PAB("307", "PAB", new Integer[]{622155,622156}),
		CMB("308", "CMB", new Integer[]{622578,622576,622581,439228,628262,628362,628362,628262}),
		;

	
		private final String code;

	
		private final String name;

	
		private String abbrName;

	
		private Integer[] creditCardPrefixes;

	
		private Integer[] debitCardPrefixes;

		private Integer[] allCardPrefixes;

		BankNameEnum(String code, String name) {
			this.code = code;
			this.name = name;
		}

		BankNameEnum(String code, String name, String abbrName) {
			this.code = code;
			this.name = name;
			this.abbrName = abbrName;
		}

		BankNameEnum(String code, String name, String abbrName, Integer[] debitCardPrefixes,
				Integer[] creditCardPrefixes) {
			this.code = code;
			this.name = name;
			this.abbrName = abbrName;
			this.creditCardPrefixes = creditCardPrefixes;
			this.debitCardPrefixes = debitCardPrefixes;

			this.allCardPrefixes = (Integer[]) ArrayUtils.addAll(this.creditCardPrefixes, this.debitCardPrefixes);
		}

		BankNameEnum(String code, String name, Integer[] debitCardPrefixes) {
			this.code = code;
			this.name = name;
			this.debitCardPrefixes = debitCardPrefixes;
			this.allCardPrefixes = debitCardPrefixes;
		}

		BankNameEnum(String code, String name, Integer[] debitCardPrefixes, Integer[] creditCardPrefixes) {
			this.code = code;
			this.name = name;
			this.creditCardPrefixes = creditCardPrefixes;
			this.debitCardPrefixes = debitCardPrefixes;

			this.allCardPrefixes = (Integer[]) ArrayUtils.addAll(this.creditCardPrefixes, this.debitCardPrefixes);
		}

		public String getName() {
			return this.name;
		}

		public String getAbbrName() {
			return this.abbrName;
		}

		public Integer[] getCreditCardPrefixes() {
			return this.creditCardPrefixes;
		}

		public Integer[] getDebitCardPrefixes() {
			return this.debitCardPrefixes;
		}

		public Integer[] getAllCardPrefixes() {
			return this.allCardPrefixes;
		}

		public String getCode() {
			return this.code;
		}
	}
}


