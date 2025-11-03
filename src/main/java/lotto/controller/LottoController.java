package lotto.controller;

import java.util.ArrayList;
import java.util.List;
import lotto.model.Lotto;
import lotto.model.LottoPublisher;
import lotto.model.LottoResult;
import lotto.model.WinningLotto;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {

    private final InputView inputView;
    private final OutputView outputView;

    public LottoController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        // (1) Model (로또 구매)
        int lottoPrice = getValidPurchaseAmount();
        int lotteryTicketCount = lottoPrice / 1000;

        LottoPublisher lottoPublisher = new LottoPublisher();
        List<Lotto> purchasedLottos = lottoPublisher.publishLottos(lotteryTicketCount);

        // (2) View (구매 결과 출력)
        outputView.printPurchasedLottos(lotteryTicketCount, purchasedLottos);

        // (3) Model (당첨 번호 생성)
        Lotto winningLottoNumbers = getValidWinningNumbers();
        int bonusWinningNumber = getValidBonusNumber(winningLottoNumbers);
        WinningLotto answerKey = new WinningLotto(winningLottoNumbers, bonusWinningNumber);

        // (4) Model (통계 및 수익률 계산)
        LottoResult lottoResult = new LottoResult();
        lottoResult.calculateStatistics(purchasedLottos, answerKey);
        double rateOfReturn = lottoResult.getRateOfReturn(lottoPrice);

        // (5) View (최종 결과 출력)
        outputView.printStatisticsHeader();
        outputView.printStatistics(lottoResult.getStatistics());
        outputView.printRateOfReturn(rateOfReturn);
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
            price = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자로 된 금액을 입력해 주세요.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("[ERROR] 금액은 0원 이상으로 입력해주세요");
        }
        if (price % 1000 != 0) {
            throw new IllegalArgumentException("[ERROR] 금액은 1,000원 단위로 입력해주세요.");
        }
        return price;
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
        List<Integer> numbers = new ArrayList<>();
        try {
            for (String numStr : numberStrings) {
                numbers.add(Integer.parseInt(numStr.trim()));
            }
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
        if (bonusNumber < 1 || bonusNumber > 45) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 1~45 범위 내에서 입력해 주세요.");
        }
        if (winningNumbers.hasBonusNumber(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨번호와 중복될 수 없습니다.");
        }
        return bonusNumber;
    }
}
