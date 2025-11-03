package lotto.model;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LottoResult {
    private static final int MIN_PURCHASE_PRICE = 0;
    private static final double DEFAULT_RATE_OF_RETURN = 0.0;
    private static final double PERCENTAGE_MULTIPLIER = 100.0;

    private final Map<LottoRank, Integer> statistics;

    public LottoResult() {
        this.statistics = new EnumMap<>(LottoRank.class);
        for (LottoRank rank : LottoRank.values()) {
            this.statistics.put(rank, 0);
        }
    }

    public void calculateStatistics(List<Lotto> purchasedLottos, WinningLotto answerKey) {
        calculateRankStatistics(purchasedLottos, answerKey);
    }

    public double getRateOfReturn(int lottoPrice) {
        double totalWinningPrize = calculateTotalWinningPrize();

        if (lottoPrice == MIN_PURCHASE_PRICE) {
            return DEFAULT_RATE_OF_RETURN;
        }

        return (totalWinningPrize / (double) lottoPrice) * PERCENTAGE_MULTIPLIER;
    }

    public Map<LottoRank, Integer> getStatistics() {
        return Collections.unmodifiableMap(this.statistics);
    }

    private void calculateRankStatistics(List<Lotto> purchasedLottos, WinningLotto answerKey) {
        for (Lotto lotto : purchasedLottos) {
            int matchCount = lotto.countMatchingNumbers(answerKey.getWinningNumbers().getNumbers());
            boolean hasBonus = lotto.hasBonusNumber(answerKey.getBonusNumber());

            LottoRank rank = LottoRank.find(matchCount, hasBonus);

            updateStatistics(rank);
        }
    }

    private void updateStatistics(LottoRank rank) {
        int currentCount = this.statistics.get(rank);
        this.statistics.put(rank, currentCount + 1);
    }

    private double calculateTotalWinningPrize() {
        double totalWinningPrize = 0;
        for (Map.Entry<LottoRank, Integer> entry : this.statistics.entrySet()) {
            long prize = entry.getKey().getWinningPrize();
            int count = entry.getValue();
            totalWinningPrize += (double) prize * (double) count;
        }
        return totalWinningPrize;
    }
}