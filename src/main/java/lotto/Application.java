package lotto;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        // 입출력 요구사항
        System.out.println("구입금액을 입력해 주세요.");
        //- 사용자에게 로또 구입 금액 입력 받기 (단위: 1,000원)
        int lottoPrice = Integer.parseInt(Console.readLine());

        //- 구입 금액에 해당하는 만큼 발행한 로또 수량 및 번호를 출력하기
        int lotteryTicketCount = lottoPrice / 1000;

        System.out.println(lotteryTicketCount + "개를 구매했습니다.");

        // 로또 요구사항
        //로또 번호의 숫자 범위는 정수로 1~45 까지이다.
        List<Integer> lotto;

        List<Lotto> lottoNumbersList = new ArrayList<>();

        //1개의 로또를 발행 시에는 중복되지 않는 6개의 숫자를 뽑는다.
        for(int i=0; i < lotteryTicketCount; i++) {
            lotto = Randoms.pickUniqueNumbersInRange(1, 45, 6); // 로또 번호 리스트 (로또 한 장) 생성
            Lotto lottoNumber = new Lotto(lotto); // 로또 객체 생성 (생성한 로또 번호 리스트 사용)
            lottoNumbersList.add(lottoNumber); // 로또 객체에 로또 한 장 추가
        }
        // 로또 객체 전체 출력 (로또 한 장씩 한 줄에)
        for(int i=0; i < lottoNumbersList.size(); i++) {
            System.out.println(lottoNumbersList.get(i).getNumbers());
        }


        //당첨 번호 추첨 시 중복되지 않는 숫자 6개 + 보너스 번호 1개를 뽑는다.
        //- 사용자에게 당첨 번호(6개)를 입력 받기 (쉼표를 기준으로 구분하기)
        System.out.println("당첨 번호를 입력해 주세요.");
        String WinningNumber = Console.readLine();
        String[] WinningNumbers = WinningNumber.split(",");
        List<Integer> lottoWinningNumber = new ArrayList<>();
        for(int i=0; i<WinningNumbers.length; i++) {
            lottoWinningNumber.add(Integer.parseInt(WinningNumbers[i]));
        }

        //- 사용자에게 보너스 번호(1개)를 입력 받기
        System.out.println("보너스 번호를 입력해 주세요.");
        int bonusWinningNumber = Integer.parseInt(Console.readLine());
        lottoWinningNumber.add(bonusWinningNumber);

        Collections.sort(lottoWinningNumber);
        System.out.println(lottoWinningNumber);

    }
}
