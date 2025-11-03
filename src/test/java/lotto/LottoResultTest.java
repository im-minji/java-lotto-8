package lotto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

class LottoResultTest {

    private WinningLotto answerNumbers;
    private LottoResult lottoResult;

    @BeforeEach
    void setUp() {
        Lotto winningNumbers = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        int bonusNumber = 7;

        answerNumbers = new WinningLotto(winningNumbers, bonusNumber);

        lottoResult = new LottoResult();
    }

    @Test
    @DisplayName("LottoResult 생성 시, 모든 등급의 횟수는 0으로 초기화되어야 한다.")
    void 생성자는_모든_등급을_0으로_초기화한다() {
        Map<LottoRank, Integer> statistics = lottoResult.getStatistics();

        assertThat(statistics.values()).allMatch(count -> count == 0); // 모든 값이 0인지 확인
        assertThat(statistics.keySet()).containsAll(List.of(LottoRank.values())); // 모든 랭크가 포함되었는지 확인
    }

    @Test
    @DisplayName("1등(6개 일치) 1장을 올바르게 집계한다.")
    void 통계계산_1등_1장() {
        Lotto firstPrizeLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        List<Lotto> purchasedLottos = List.of(firstPrizeLotto);

        lottoResult.calculateStatistics(purchasedLottos, answerNumbers);

        Map<LottoRank, Integer> statistics = lottoResult.getStatistics();

        assertThat(statistics.get(LottoRank.FIRST)).isEqualTo(1);
        assertThat(statistics.get(LottoRank.NONE)).isEqualTo(0);
    }

    @Test
    @DisplayName("2등(5개+보너스) 1장을 올바르게 집계한다.")
    void 통계계산_2등_1장() {
        Lotto secondPrizeLotto = new Lotto(List.of(1, 2, 3, 4, 5, 7)); // 5개 일치 + 보너스 7
        List<Lotto> purchasedLottos = List.of(secondPrizeLotto);

        lottoResult.calculateStatistics(purchasedLottos, answerNumbers);

        Map<LottoRank, Integer> statistics = lottoResult.getStatistics();

        assertThat(statistics.get(LottoRank.SECOND)).isEqualTo(1);
        assertThat(statistics.get(LottoRank.FIRST)).isEqualTo(0);
        assertThat(statistics.get(LottoRank.THIRD)).isEqualTo(0);
    }

    @Test
    @DisplayName("3등(5개) 1장을 올바르게 집계한다.")
    void 통계계산_3등_1장() {
        Lotto thirdPrizeLotto = new Lotto(List.of(1, 2, 3, 4, 5, 8)); // 5개 일치 + 보너스 불일치(8)
        List<Lotto> purchasedLottos = List.of(thirdPrizeLotto);

        lottoResult.calculateStatistics(purchasedLottos, answerNumbers);

        Map<LottoRank, Integer> statistics = lottoResult.getStatistics();

        assertThat(statistics.get(LottoRank.THIRD)).isEqualTo(1);
        assertThat(statistics.get(LottoRank.SECOND)).isEqualTo(0);
    }


    @Test
    @DisplayName("5등(3개) 2장과 꽝 1장을 올바르게 집계한다.")
    void 통계계산_여러_등수_집계() {
        List<Lotto> purchasedLottos = List.of(
                new Lotto(List.of(1, 2, 3, 10, 11, 12)), // 5등
                new Lotto(List.of(1, 2, 3, 13, 14, 15)), // 5등
                new Lotto(List.of(10, 11, 12, 13, 14, 15))  // 꽝 (0개 일치)
        );

        lottoResult.calculateStatistics(purchasedLottos, answerNumbers);

        Map<LottoRank, Integer> statistics = lottoResult.getStatistics();

        assertThat(statistics.get(LottoRank.FIFTH)).isEqualTo(2);
        assertThat(statistics.get(LottoRank.NONE)).isEqualTo(1);
        assertThat(statistics.get(LottoRank.FOURTH)).isEqualTo(0);
    }


    @Test
    @DisplayName("5등 1장, 8000원 구매 시 수익률 62.5%를 반환한다.")
    void 수익률계산_5등_1장_8000원() {
        int lottoPrice = 8000;

        List<Lotto> purchasedLottos = List.of(
                new Lotto(List.of(1, 2, 3, 10, 11, 12)), // 5등 (5,000원)
                new Lotto(List.of(10, 11, 12, 13, 14, 15)) // 꽝
        );

        lottoResult.calculateStatistics(purchasedLottos, answerNumbers);

        double rateOfReturn = lottoResult.getRateOfReturn(lottoPrice);

        assertThat(rateOfReturn).isEqualTo(62.5, offset(0.001));
    }

    @Test
    @DisplayName("당첨금이 0원일 때 0.0%를 반환한다.")
    void 수익률계산_당첨금_0원() {
        int lottoPrice = 1000;

        List<Lotto> purchasedLottos = List.of(
                new Lotto(List.of(10, 11, 12, 13, 14, 15)) // 꽝
        );

        lottoResult.calculateStatistics(purchasedLottos, answerNumbers);
        double rateOfReturn = lottoResult.getRateOfReturn(lottoPrice);

        // then
        assertThat(rateOfReturn).isEqualTo(0.0);
    }
}