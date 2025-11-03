package lotto;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;

public class LottoPublisher {
    
    public List<Lotto> publishLottos(int count) {
        List<Lotto> publishedLottos = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            List<Integer> lottoNumbers = Randoms.pickUniqueNumbersInRange(1, 45, 6);
            Lotto newLotto = new Lotto(lottoNumbers); // Lotto 생성자가 유효성 검증
            publishedLottos.add(newLotto);
        }
        return publishedLottos;
    }
}
