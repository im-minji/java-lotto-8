package lotto.view;

public enum ViewMessage {
    // InputView
    PROMPT_PURCHASE_AMOUNT("구입금액을 입력해 주세요."),
    PROMPT_WINNING_NUMBERS("\n당첨 번호를 입력해 주세요."),
    PROMPT_BONUS_NUMBER("\n보너스 번호를 입력해 주세요."),

    // OutputView
    PURCHASE_COUNT_FORMAT("\n%d개를 구매했습니다."),
    STATISTICS_HEADER("\n당첨 통계\n---"),
    STATISTICS_RANK_FORMAT("%s (%s원) - %d개"),
    STATISTICS_MATCH_COUNT("%d개 일치"),
    STATISTICS_MATCH_BONUS(", 보너스 볼 일치"),
    PRIZE_MONEY_FORMAT("%,d"),
    RATE_OF_RETURN_FORMAT("총 수익률은 %.1f%%입니다.%n");


    private final String message;

    ViewMessage(String message) {
        this.message = message;
    }

    public void print() {
        System.out.println(this.message);
    }

    public void printf(Object... args) {
        System.out.printf(this.message + "\n", args);
    }

    public void printRate(Object... args) {
        System.out.printf(this.message, args);
    }

    public String getFormattedMessage(Object... args) {
        return String.format(this.message, args);
    }

    public String getMessage() {
        return this.message;
    }
}