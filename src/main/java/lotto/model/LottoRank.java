package lotto.model;

public enum LottoRank {
    // 몇 개가 일치해야 하는 지
    // 상금이 얼마인지(2,000,000,000원)
    // 보너스가 필요한지


    // 당첨 규칙과 상금에 대한 데이터(상수)
    FIRST(6, 2000000000L, false),
    SECOND(5, 30000000L, true),
    THIRD(5, 1500000L, false),
    FOURTH(4, 50000L, false),
    FIFTH(3, 5000L, false),
    NONE(0, 0, false);

    private final int winningCount;
    private final long winningPrize;
    private final boolean needBonus;

    // 로또 통계판 생성
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

    // 몇 개 맞았는 지, 보너스 번호가 있는 지 검사해서 로또 랭크를 반환해주는 메서드
    public static LottoRank find(int winningCount, boolean needBonus) {
        if(winningCount == 6) {return LottoRank.FIRST;}

        if(winningCount == 5 && needBonus) {return LottoRank.SECOND;}

        if(winningCount == 5) {return LottoRank.THIRD;}

        if(winningCount == 4) {return LottoRank.FOURTH;}

        if(winningCount == 3) {return LottoRank.FIFTH;}

        return LottoRank.NONE;
    }
}
