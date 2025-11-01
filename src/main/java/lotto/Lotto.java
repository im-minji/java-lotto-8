package lotto;

import java.util.Collections;
import java.util.List;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }

        for (Integer num : numbers) {
            if (num > 45 || num < 1) {
                throw new IllegalArgumentException("[ERROR] 로또 번호는 1~45 사이여야 합니다.");
            }
        }
        // 1~45

        // 중복
        if(numbers.size() != numbers.stream().distinct().count()) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복되면 안됩니다.");
        }
    }

    public List<Integer> getNumbers() {
        Collections.sort(numbers);
        return numbers;
    }
    // TODO: 추가 기능 구현
}
