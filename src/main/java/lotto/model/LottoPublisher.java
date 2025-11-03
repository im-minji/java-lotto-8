package lotto.model;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;

public class LottoPublisher {

    public List<Lotto> publishLotto(int count) {
        List<Lotto> publishedLotto = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            List<Integer> lottoNumbers = Randoms.pickUniqueNumbersInRange(
                    Lotto.MIN_NUMBER,
                    Lotto.MAX_NUMBER,
                    Lotto.LOTTO_SIZE
            );
            Lotto newLotto = new Lotto(lottoNumbers);
            publishedLotto.add(newLotto);
        }
        return publishedLotto;
    }
}
