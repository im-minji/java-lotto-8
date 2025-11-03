package lotto.model;

import java.util.List;

public class WinningLotto {
    private final Lotto winningNumbers;
    private final int bonusNumber;

    public WinningLotto(Lotto winningNumbers, int bonusNumber) {
        validateRange(bonusNumber);
        validateDuplication(winningNumbers, bonusNumber);

        this.winningNumbers = winningNumbers;
        this.bonusNumber = bonusNumber;
    }

    private void validateRange(int bonusNumber) {
        if (bonusNumber < Lotto.MIN_NUMBER || bonusNumber > Lotto.MAX_NUMBER) {
            throw new IllegalArgumentException(
                    ErrorMessage.BONUS_NUMBER_INVALID_RANGE.getFormattedMessage(
                            Lotto.MIN_NUMBER, Lotto.MAX_NUMBER
                    )
            );
        }
    }


    private void validateDuplication(Lotto winningNumbers, int bonusNumber) {
        List<Integer> mainNumberList = winningNumbers.getNumbers();

        if (mainNumberList.contains(bonusNumber)) {
            throw new IllegalArgumentException(
                    ErrorMessage.BONUS_NUMBER_DUPLICATE.getMessage()
            );
        }
    }

    public Lotto getWinningNumbers() {
        return winningNumbers;
    }

    public int getBonusNumber() {
        return bonusNumber;
    }
}