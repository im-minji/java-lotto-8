package lotto;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LottoResult {
    Map<LottoRank, Integer> winningStatistics = new EnumMap<>(LottoRank.class);

    // 로또 통계판을 0으로 초기화
    public LottoResult() {
        for(LottoRank rank : LottoRank.values()) {
            winningStatistics.put(rank, 0);
        }
    }

    public void lottoWinningCalculate(List<Lotto> lottoNumbersList, WinningLotto winningLotto) {
        long winningCountLong = 0;
        int winningCount = 0;

        List<Integer> winningList = winningLotto.getWinningNumbers().getNumbers();
        int bonusNum = winningLotto.getBonusNumber();

        for (Lotto currentLottoNumbers : lottoNumbersList) {
            winningCountLong = currentLottoNumbers.getNumbers().stream().filter(winningList::contains).count();
            winningCount = (int) winningCountLong;
            boolean hasBonus = currentLottoNumbers.getNumbers().contains(winningLotto.getBonusNumber());
            LottoRank rank = LottoRank.find(winningCount, hasBonus);  // 방금 검사한 로또의 등급(예: LottoRank.FIFTH)을 판별해 결과를 rank라는 임시 변수에 저장
            int currentWinningCount = winningStatistics.get(rank);  // 통계판에 적힌 지금 당첨 개수 가지고 오기
            winningStatistics.put(rank, currentWinningCount + 1);  // 통계판에 현재 당첨 등수에 해당하는 개수에 1 더하기
        }
    }

    public double calculatingRateOfReturn(int lottoPrice) {
        double totalWinningPrize = 0;
        // 통계판을 돌면서 당첨등수에 해당하는 개수(rank) * 상금(winningPrize)를 totalWinningPrize에 더하기
        for (LottoRank rank : winningStatistics.keySet()) {
            int rankCount = winningStatistics.get(rank);
            double currentWinningPrize = (double) rank.getWinningPrize() * (double) rankCount;
            totalWinningPrize += currentWinningPrize;
        }

        double rateOfReturn = (totalWinningPrize / (double) lottoPrice) * 100;

        return rateOfReturn;
    }
}
