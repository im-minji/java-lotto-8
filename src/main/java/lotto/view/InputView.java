package lotto.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public String readPurchaseAmount() {
        ViewMessage.PROMPT_PURCHASE_AMOUNT.print();
        return Console.readLine();
    }

    public String readWinningNumbers() {
        ViewMessage.PROMPT_WINNING_NUMBERS.print();
        return Console.readLine();
    }

    public String readBonusNumber() {
        ViewMessage.PROMPT_BONUS_NUMBER.print();
        return Console.readLine();
    }
}