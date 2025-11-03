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
        System.out.println("\n" + count + "개를 구매했습니다.");
        for (Lotto lotto : lottos) {
            System.out.println(lotto.getNumbers());
        }
    }

    public void printStatisticsHeader() {
        System.out.println("\n당첨 통계");
        System.out.println("---");
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
        String prize = String.format("%,d", rank.getWinningPrize());
        String matchCount = rank.getWinningCount() + "개 일치";

        if (rank.isNeedBonus()) {
            matchCount += ", 보너스 볼 일치";
        }

        return String.format("%s (%s원) - %d개", matchCount, prize, count);
    }

    public void printRateOfReturn(double rate) {
        System.out.printf("총 수익률은 %.1f%%입니다.%n", rate);
    }
}