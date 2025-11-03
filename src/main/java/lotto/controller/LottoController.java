package lotto.controller;

import lotto.model.Lotto;
import lotto.model.LottoPublisher;
import lotto.model.LottoResult;
import lotto.model.WinningLotto;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.Arrays;
import java.util.List;

public class LottoController {
    private static final int LOTTO_PRICE_UNIT = 1000;
    private static final int RANDOM_MIN_NUMBER = 0;
    private static final int RANDOM_MAX_NUMBER = 9;

    private final InputView inputView;
    private final OutputView outputView;

    public LottoController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        // 1. 로또 구매 (및 발행/출력)
        int lottoPrice = getValidPurchaseAmount();
        List<Lotto> purchasedLottos = purchaseLottos(lottoPrice);

        // 2. 당첨 번호 설정
        WinningLotto answerKey = setupWinningLotto();

        // 3. 결과 집계 및 출력
        showGameResult(lottoPrice, purchasedLottos, answerKey);
    }

    private int getValidPurchaseAmount() {
        while (true) {
            try {
                String input = inputView.readPurchaseAmount();
                return validatePurchaseAmount(input); // 검증 로직 분리
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private int validatePurchaseAmount(String input) {
        int price;
        try {
            price = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자로 된 금액을 입력해 주세요.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("[ERROR] 금액은 0원 이상으로 입력해주세요");
        }
        if (price % LOTTO_PRICE_UNIT != 0) {
            throw new IllegalArgumentException("[ERROR] 금액은 " + LOTTO_PRICE_UNIT + "원 단위로 입력해주세요.");
        }
        return price;
    }

    private List<Lotto> purchaseLottos(int lottoPrice) {
        int lotteryTicketCount = lottoPrice / LOTTO_PRICE_UNIT;

        LottoPublisher lottoPublisher = new LottoPublisher();
        List<Lotto> purchasedLottos = lottoPublisher.publishLottos(lotteryTicketCount);

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
                return parseAndValidateWinningNumbers(input); // 검증 로직 분리
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Lotto parseAndValidateWinningNumbers(String input) {
        String[] numberStrings = input.split(",");
        List<Integer> numbers;
        try {
            numbers = Arrays.stream(numberStrings)
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList(); // Java 16+
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호는 숫자로 입력해 주세요.");
        }
        return new Lotto(numbers);
    }

    private int getValidBonusNumber(Lotto winningNumbers) {
        while (true) {
            try {
                String input = inputView.readBonusNumber();
                return parseAndValidateBonusNumber(input, winningNumbers);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private int parseAndValidateBonusNumber(String input, Lotto winningNumbers) {
        int bonusNumber;
        try {
            bonusNumber = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 숫자로 입력해 주세요.");
        }
        if (bonusNumber < Lotto.MIN_NUMBER || bonusNumber > Lotto.MAX_NUMBER) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 " + Lotto.MIN_NUMBER + "부터 " + Lotto.MAX_NUMBER + " 범위 내에서 입력해 주세요.");
        }
        if (winningNumbers.hasBonusNumber(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨번호와 중복될 수 없습니다.");
        }
        return bonusNumber;
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