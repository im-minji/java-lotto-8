package lotto;

import lotto.model.Lotto;
import lotto.model.WinningLotto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;

class WinningLottoTest {

    private Lotto mainLotto;
    private int validBonusNumber;
    private int invalidBonusNumber;

    @BeforeEach
    void setUp() {
        mainLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        validBonusNumber = 7;

        invalidBonusNumber = 6; // mainLotto에 이미 6이 존재
    }

    @Test
    void 보너스_번호가_당첨_번호와_중복되면_예외가_발생한다() {
        assertThatThrownBy(() -> new WinningLotto(mainLotto, invalidBonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
    }

    @Test
    @DisplayName("유효한 당첨 번호와 보너스 번호로 객체를 생성할 수 있다.")
    void 당첨_번호와_보너스_번호가_중복되지_않는다면_예외가_발생하지_않는다() {
        assertThatCode(() -> new WinningLotto(mainLotto, validBonusNumber))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName(" getWinningNumbers()는 정확한 Lotto 객체를 반환한다.")
    void Lotto_객체를_반환하면_예외가_발생하지_않는다() {
        WinningLotto answerKey = new WinningLotto(mainLotto, validBonusNumber);
        Lotto returnedLotto = answerKey.getWinningNumbers();
        assertThat(returnedLotto).isEqualTo(mainLotto);
    }

    @Test
    @DisplayName("getBonusNumber()는 정확한 보너스 번호를 반환한다.")
    void 보너스_번호를_반환하면_예외가_발생하지_않는다() {
        WinningLotto answerKey = new WinningLotto(mainLotto, validBonusNumber);
        int returnedBonus = answerKey.getBonusNumber();
        assertThat(returnedBonus).isEqualTo(validBonusNumber); // 7
    }
}