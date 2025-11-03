package lotto.model;

public enum ErrorMessage {
    NOT_A_NUMBER("숫자로 된 금액을 입력해 주세요."),
    PRICE_NOT_DIVISIBLE("금액은 %d원 단위로 입력해주세요."),
    PRICE_NEGATIVE("금액은 0원 이상으로 입력해주세요"),

    WINNING_NUMBER_NOT_A_NUMBER("당첨 번호는 숫자로 입력해 주세요."),

    BONUS_NUMBER_NOT_A_NUMBER("보너스 번호는 숫자로 입력해 주세요."),
    BONUS_NUMBER_INVALID_RANGE("보너스 번호는 %d부터 %d 범위 내에서 입력해 주세요."),
    BONUS_NUMBER_DUPLICATE_WITH_WINNING_NUMBERS("보너스 번호는 당첨번호와 중복될 수 없습니다."),

    LOTTO_INVALID_SIZE("로또 번호는 %d개여야 합니다."),
    LOTTO_INVALID_RANGE("로또 번호는 %d부터 %d 사이여야 합니다."),
    LOTTO_DUPLICATE_NUMBER("로또 번호는 중복되면 안됩니다."),

    BONUS_NUMBER_DUPLICATE("보너스 번호는 당첨 번호와 중복될 수 없습니다.");


    private static final String PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return PREFIX + message;
    }

    public String getFormattedMessage(Object... args) {
        return String.format(PREFIX + message, args);
    }
}