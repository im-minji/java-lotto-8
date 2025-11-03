package lotto.model;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Lotto {
    public static final int LOTTO_SIZE = 6;
    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 45;

    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);

        List<Integer> modifiableNumbers = new ArrayList<>(numbers);
        Collections.sort(modifiableNumbers);

        this.numbers = Collections.unmodifiableList(modifiableNumbers);
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

    public List<Integer> getNumbers() {
        return this.numbers;
    }

    private void validate(List<Integer> numbers) {
        validateSize(numbers);
        validateRange(numbers);
        validateDuplicates(numbers);
    }

    private void validateSize(List<Integer> numbers) {
        if (numbers.size() != LOTTO_SIZE) {
            throw new IllegalArgumentException(
                    ErrorMessage.LOTTO_INVALID_SIZE.getFormattedMessage(LOTTO_SIZE)
            );
        }
    }

    private void validateRange(List<Integer> numbers) {
        boolean isOutOfRange = numbers.stream()
                .anyMatch(num -> num < MIN_NUMBER || num > MAX_NUMBER);

        if (isOutOfRange) {
            throw new IllegalArgumentException(
                    ErrorMessage.LOTTO_INVALID_RANGE.getFormattedMessage(MIN_NUMBER, MAX_NUMBER)
            );
        }
    }

    // (★수정★) 하드 코딩된 문자열 대신 ErrorMessage Enum 사용
    private void validateDuplicates(List<Integer> numbers) {
        long distinctCount = numbers.stream().distinct().count();

        if (distinctCount != numbers.size()) {
            throw new IllegalArgumentException(
                    ErrorMessage.LOTTO_DUPLICATE_NUMBER.getMessage()
            );
        }
    }
}