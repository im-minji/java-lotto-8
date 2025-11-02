package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class LottoTest {
    @Test
    void 로또_번호의_개수가_6개가_넘어가면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // TODO: 추가 기능 구현에 따른 테스트 코드 작성
    @ParameterizedTest
    @ValueSource(ints = {0, 46, -18, 99})
    void 로또_번호가_범위를_벗어나면_예외가_발생한다(int invalidNumber) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, invalidNumber);

        assertThatThrownBy(() -> new Lotto(numbers))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("getNumbers 메서드는 오름차순으로 정렬된 리스트를 반환한다.")
    @Test
    void 로또_번호는_오름차순_정렬을_하지_않으면_예외가_발생한다() {
        List<Integer> unsortedNumbers = new ArrayList<>(List.of(6, 5, 4, 3, 2, 1));
        Lotto lotto = new Lotto(unsortedNumbers);

        List<Integer> sortedNumbers = lotto.getNumbers();

        assertThat(sortedNumbers).isSorted();
        assertThat(sortedNumbers).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @DisplayName("유효한 로또 번호 6개가 주어지면 객체를 성공적으로 생성한다.")
    @Test
    void 유효한_로또_번호로_객체를_생성한다() {
        List<Integer> validNumbers = List.of(1, 2, 3, 4, 5, 6);

        assertThatCode(() -> new Lotto(validNumbers))
                .doesNotThrowAnyException();
    }
}
