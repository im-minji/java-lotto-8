package lotto;

public enum LottoRank {
    // winningCount
    // 상금이 얼마인지(2,000,000,000원)
    // 보너스가 필요한지(false)

    // 당첨 내역 출력
    // - 당첨은 1등부터 5등까지 있다. 당첨 기준과 금액은 아래와 같다.
    //    - 1등: 6개 번호 일치 / 2,000,000,000원
    //    - 2등: 5개 번호 + 보너스 번호 일치 / 30,000,000원
    //    - 3등: 5개 번호 일치 / 1,500,000원
    //    - 4등: 4개 번호 일치 / 50,000원
    //    - 5등: 3개 번호 일치 / 5,000원

    FIRST(6, 2000000000L, false),
    SECOND(5, 30000000L, true),
    THIRD(5, 1500000L, false),
    FOURTH(4, 50000L, false),
    FIFTH(3, 5000L, false),
    NONE(0, 0, false);


    private final int winningCount;
    private final long winningPrize;
    private final boolean needBonus;

    LottoRank(int winningCount, long winningPrize, boolean needBonus) {
        this.winningCount = winningCount;
        this.winningPrize = winningPrize;
        this.needBonus = needBonus;
    }

    public int getWinningCount() {
        return winningCount;
    }

    public long getWinningPrize() {
        return winningPrize;
    }

    public boolean isNeedBonus() {
        return  needBonus;
    }

    public static LottoRank find(int winningCount, boolean needBonus) {
        if(winningCount == 6) {return LottoRank.FIRST;}

        if(winningCount == 5 && needBonus) {return LottoRank.SECOND;}

        if(winningCount == 5 && !needBonus) {return LottoRank.THIRD;}

        if(winningCount == 4) {return LottoRank.FOURTH;}

        if(winningCount == 3) {return LottoRank.FIFTH;}

        return LottoRank.NONE;
    }
}
