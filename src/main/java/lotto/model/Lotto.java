package lotto.model;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Lotto {
    public static final int LOTTO_SIZE = 6;
    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 45;

    private final List<Integer> numbers;

    // 로또 생성 (6개의 숫자로 구성된 정수 리스트를 받아서 로또 한 장으로 만듬)
    public Lotto(List<Integer> numbers) {
        validate(numbers);

        List<Integer> modifiableNumbers = new ArrayList<>(numbers);
        Collections.sort(modifiableNumbers);
        this.numbers = Collections.unmodifiableList(modifiableNumbers);
    }

    // 로또 한 장에 들어있는 번호를 오름차순으로 정렬해서 getNumbers
    public List<Integer> getNumbers() {
        return this.numbers;
    }

    public int countMatchingNumbers(List<Integer> winningNumbers) {
        long count = this.numbers.stream()
                .filter(winningNumbers::contains)
                .count();
        return (int) count;
    }

    public boolean hasBonusNumber(int bonusNumber) {
        return this.numbers.contains(bonusNumber);
    }

    // 로또 한 장을 만들 때 검증하는 것들
    private void validate(List<Integer> numbers) {
        validateSize(numbers);
        validateRange(numbers);
        validateDuplicates(numbers);
    }

    private void validateSize(List<Integer> numbers) {
        if (numbers.size() != LOTTO_SIZE) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 " + LOTTO_SIZE + "개여야 합니다.");
        }
    }

    private void validateRange(List<Integer> numbers) {
        boolean isOutOfRange = numbers.stream()
                .anyMatch(num -> num < MIN_NUMBER || num > MAX_NUMBER);

        if (isOutOfRange) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 " + MIN_NUMBER + "부터 " + MAX_NUMBER + " 사이여야 합니다.");
        }
    }

    private void validateDuplicates(List<Integer> numbers) {
        long distinctCount = numbers.stream()
                .distinct()
                .count();

        if (distinctCount != LOTTO_SIZE) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복되면 안됩니다.");
        }
    }
}
