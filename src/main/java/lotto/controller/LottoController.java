package lotto.controller;

import lotto.model.Lotto;
import lotto.model.LottoPublisher;
import lotto.model.LottoResult;
import lotto.model.WinningLotto;
import lotto.model.ErrorMessage;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LottoController {
    private static final int LOTTO_PRICE_UNIT = 1000;

    private final InputView inputView;
    private final OutputView outputView;

    public LottoController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        int lottoPrice = getValidPurchaseAmount();
        List<Lotto> purchasedLottos = purchaseLottos(lottoPrice);

        WinningLotto answerKey = setupWinningLotto();

        showGameResult(lottoPrice, purchasedLottos, answerKey);
    }

    private int getValidPurchaseAmount() {
        while (true) {
            try {
                String input = inputView.readPurchaseAmount();
                return validateAndParsePurchaseAmount(input);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private int validateAndParsePurchaseAmount(String input) {
        int price = parseAmount(input);

        if (price <= 0) {
            throw new IllegalArgumentException(ErrorMessage.PRICE_NEGATIVE.getMessage());
        }

        if (price % LOTTO_PRICE_UNIT != 0) {
            throw new IllegalArgumentException(
                    ErrorMessage.PRICE_NOT_DIVISIBLE.getFormattedMessage(LOTTO_PRICE_UNIT)
            );
        }
        return price;
    }

    private int parseAmount(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.NOT_A_NUMBER.getMessage());
        }
    }

    private List<Lotto> purchaseLottos(int lottoPrice) {
        int lotteryTicketCount = lottoPrice / LOTTO_PRICE_UNIT;

        LottoPublisher lottoPublisher = new LottoPublisher();
        List<Lotto> purchasedLottos = lottoPublisher.publishLotto(lotteryTicketCount);

        outputView.printPurchasedLottos(lotteryTicketCount, purchasedLottos);
        return purchasedLottos;
    }

    private WinningLotto setupWinningLotto() {
        Lotto winningLottoNumbers = getValidWinningNumbers();
        int bonusWinningNumber = getValidBonusNumber(winningLottoNumbers);
        return new WinningLotto(winningLottoNumbers, bonusWinningNumber);
    }

    private Lotto getValidWinningNumbers() {
        while (true) {
            try {
                String input = inputView.readWinningNumbers();
                return parseAndValidateWinningNumbers(input);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Lotto parseAndValidateWinningNumbers(String input) {
        String[] numberStrings = input.split(",");
        List<Integer> numbers = parseNumbers(numberStrings);
        return new Lotto(numbers);
    }

    private List<Integer> parseNumbers(String[] numberStrings) {
        try {
            return Arrays.stream(numberStrings)
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.WINNING_NUMBER_NOT_A_NUMBER.getMessage());
        }
    }


    private int getValidBonusNumber(Lotto winningNumbers) {
        while (true) {
            try {
                String input = inputView.readBonusNumber();
                int bonusNumber = parseBonusNumberString(input);
                validateBonusNumber(winningNumbers, bonusNumber);
                return bonusNumber;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }


    private int parseBonusNumberString(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.BONUS_NUMBER_NOT_A_NUMBER.getMessage());
        }
    }

    private void validateBonusNumber(Lotto winningNumbers, int bonusNumber) {
        if (bonusNumber < Lotto.MIN_NUMBER || bonusNumber > Lotto.MAX_NUMBER) {
            throw new IllegalArgumentException(
                    ErrorMessage.BONUS_NUMBER_INVALID_RANGE.getFormattedMessage(
                            Lotto.MIN_NUMBER, Lotto.MAX_NUMBER
                    )
            );
        }

        if (winningNumbers.hasBonusNumber(bonusNumber)) {
            throw new IllegalArgumentException(ErrorMessage.BONUS_NUMBER_DUPLICATE.getMessage());
        }
    }


    private void showGameResult(int lottoPrice, List<Lotto> purchasedLottos, WinningLotto answerKey) {
        LottoResult lottoResult = new LottoResult();
        lottoResult.calculateStatistics(purchasedLottos, answerKey);
        double rateOfReturn = lottoResult.getRateOfReturn(lottoPrice);

        outputView.printStatisticsHeader();
        outputView.printStatistics(lottoResult.getStatistics());
        outputView.printRateOfReturn(rateOfReturn);
    }
}
