package lotto.view;

import java.util.List;
import java.util.Map;
import lotto.model.Lotto;
import lotto.model.LottoRank;

public class OutputView {

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    public void printPurchasedLottos(int count, List<Lotto> lottos) {
        ViewMessage.PURCHASE_COUNT_FORMAT.printf(count); // printf가 자동 줄바꿈

        for (Lotto lotto : lottos) {
            System.out.println(lotto.getNumbers());
        }
    }

    public void printStatisticsHeader() {
        ViewMessage.STATISTICS_HEADER.print();
    }

    public void printStatistics(Map<LottoRank, Integer> statistics) {
        List<LottoRank> ranksToPrint = List.of(
                LottoRank.FIFTH, LottoRank.FOURTH, LottoRank.THIRD,
                LottoRank.SECOND, LottoRank.FIRST
        );

        for (LottoRank rank : ranksToPrint) {
            String message = formatRankMessage(rank, statistics.get(rank));
            System.out.println(message);
        }
    }

    private String formatRankMessage(LottoRank rank, int count) {
        String prize = ViewMessage.PRIZE_MONEY_FORMAT
                .getFormattedMessage(rank.getWinningPrize());

        String matchCount = ViewMessage.STATISTICS_MATCH_COUNT
                .getFormattedMessage(rank.getWinningCount());

        if (rank.isNeedBonus()) {
            matchCount += ViewMessage.STATISTICS_MATCH_BONUS.getMessage();
        }

        return ViewMessage.STATISTICS_RANK_FORMAT
                .getFormattedMessage(matchCount, prize, count);
    }

    public void printRateOfReturn(double rate) {
        ViewMessage.RATE_OF_RETURN_FORMAT.printRate(rate);
    }
}