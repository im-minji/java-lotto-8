package lotto.model;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Lotto {
    private final List<Integer> numbers;

    // 로또 생성 (6개의 숫자로 구성된 정수 리스트를 받아서 로또 한 장으로 만듬)
    public Lotto(List<Integer> numbers) {
        validate(numbers);
        List<Integer> modifiableNumbers = new ArrayList<>(numbers);
        Collections.sort(modifiableNumbers);
        this.numbers = modifiableNumbers;
    }

    // 로또 한 장을 만들 때 검증하는 것들
    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }

        // 1~45
        for (Integer num : numbers) {
            if (num > 45 || num < 1) {
                throw new IllegalArgumentException("[ERROR] 로또 번호는 1~45 사이여야 합니다.");
            }
        }


        // 중복
        if(numbers.size() != numbers.stream().distinct().count()) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복되면 안됩니다.");
        }
    }

    // 로또 한 장에 들어있는 번호를 오름차순으로 정렬해서 getNumbers
    public List<Integer> getNumbers() {
        return Collections.unmodifiableList(this.numbers);
    }

    // TODO: 추가 기능 구현
    public int countMatchingNumbers(List<Integer> winningNumbers) {
        long count = this.numbers.stream()
                .filter(winningNumbers::contains)
                .count();
        return (int) count;
    }

    public boolean hasBonusNumber(int bonusNumber) {
        return this.numbers.contains(bonusNumber);
    }
}
