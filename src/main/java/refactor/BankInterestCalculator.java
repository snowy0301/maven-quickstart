package refactor;

public class BankInterestCalculator {

    private final int HALF_YEAR_SPAN = 6;
    private final int ONE_YEAR_SPAN = 12;
    private final double ROUND_3_DIGITS = 1000.0;

    public enum Type {
        STANDARD {
            @Override
            public double getRate(int months) {
                if (months < 6) return 0.03;
                else if (months < 12) return 0.045;
                else return 0.06;
            }
        },
        PREMIUM {
            @Override
            public double getRate(int months) {
                if (months < 6) return 0.05;
                else if (months < 12) return 0.065;
                else return 0.08;
            }
        },
        VIP {
            @Override
            public double getRate(int months) {
                if (months < 6) return 0.07;
                else if (months < 12) return 0.085;
                else return 0.1;
            }
        };

        public abstract double getRate(int months);
    }

    public double calculateInterest(double principal, int months, Type type) {
        if (principal <= 0) throw new IllegalArgumentException("Money must be greater than 0.");
        if (months < 1 || months > 12) throw new IllegalArgumentException("Months must be between 1 and 12");
        double rate = type.getRate(months);
//        return Math.round(principal * rate * months / 12 * ROUND_3_DIGITS) / ROUND_3_DIGITS;
        return principal * rate * months / 12;
    }


//        private final String name;
//
//        private Type(String name) {
//            this.name = name;
//        }
//
//        public String getName() {
//            return name;
//        }

    // Tính lãi suất dựa vào số tiền gửi, kỳ hạn (tháng), loại sản phẩm
//    public double calculateInterest(double principal, int months, Type type) {
//        double rate = 0.0;
//
//        if (type.equals(Type.STANDARD)) {
//            if (months < HALF_YEAR_SPAN) {
//                rate = 0.03;
//            } else if (months < ONE_YEAR_SPAN) {
//                rate = 0.045;
//            } else {
//                rate = 0.06;
//            }
//        } else if (type.equals(Type.PREMIUM)) {
//            if (months < HALF_YEAR_SPAN) {
//                rate = 0.05;
//            } else if (months < ONE_YEAR_SPAN) {
//                rate = 0.065;
//            } else {
//                rate = 0.08;
//            }
//        } else {
//            if (months < HALF_YEAR_SPAN) {
//                rate = 0.07;
//            } else if (months < ONE_YEAR_SPAN) {
//                rate = 0.085;
//            } else {
//                rate = 0.1;
//            }
//        }
//
//        return Math.round(principal * rate * months / 12 * ROUND_3_DIGITS) / ROUND_3_DIGITS;
//    }
}