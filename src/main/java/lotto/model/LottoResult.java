package lotto;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LottoResult {
    private final Map<LottoRank, Integer> winningStatistics;

    // 2. 생성자: 맵 생성 및 0으로 초기화
    public LottoResult() {
        this.winningStatistics = new EnumMap<>(LottoRank.class);
        for (LottoRank rank : LottoRank.values()) {
            this.winningStatistics.put(rank, 0);
        }
    }

    public void calculateStatistics(List<Lotto> purchasedLottos, WinningLotto winningLotto) {

        for (Lotto lotto : purchasedLottos) {
            int matchCount = lotto.countMatchingNumbers(winningLotto.getWinningNumbers().getNumbers());
            boolean hasBonus = lotto.hasBonusNumber(winningLotto.getBonusNumber());

            LottoRank rank = LottoRank.find(matchCount, hasBonus);

            int currentCount = this.winningStatistics.get(rank);
            this.winningStatistics.put(rank, currentCount + 1);
        }
    }

    public double getRateOfReturn(int lottoPrice) {
        double totalWinningPrize = 0;

        for (LottoRank rank : this.winningStatistics.keySet()) {
            int rankCount = this.winningStatistics.get(rank);
            double currentPrize = (double) rank.getWinningPrize() * (double) rankCount;
            totalWinningPrize += currentPrize;
        }

        if (lottoPrice == 0) {
            return 0.0; // 0으로 나누기 방지
        }

        return (totalWinningPrize / (double) lottoPrice) * 100.0;
    }

    public Map<LottoRank, Integer> getStatistics() {
        return Collections.unmodifiableMap(this.winningStatistics);
    }
}