package lotto;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
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
        // 보너스 번호는 나중에 2,3등에만 활용하려고 다른 변수로 뺌

        Collections.sort(lottoWinningNumber);

        System.out.println("당첨 통계");
        System.out.println("---");

        // 당첨된 번호 갯수 변수 설정 (맞춘 번호 있으면 winningCount++)
        long winningCountLong = 0;
        int winningCount = 0;

        // 각 등수(LottoRank)가 몇 개(Integer) 존재하는 지 저장하기 위한 Map
        Map<LottoRank, Integer> winningStatistics = new EnumMap<>(LottoRank.class);

        // LottoRank의 모든 상수들을 하나씩 꺼내서 'rank'라고 부르며 반복 실행
        // LottoRank.values() = [FIRST, SECOND, THIRD, FOURTH, FIFTH, NONE]
        for(LottoRank rank : LottoRank.values()) {
            winningStatistics.put(rank, 0);
        }

        // 스트림 사용

        for (Lotto currentLottoNumbers : lottoNumbersList) {
            winningCountLong = currentLottoNumbers.getNumbers().stream().filter(lottoWinningNumber::contains).count();
            winningCount = (int) winningCountLong;

            boolean hasBonus = currentLottoNumbers.getNumbers().contains(bonusWinningNumber);

            // 방금 검사한 로또의 등급(예: LottoRank.FIFTH)을 판별해 결과를 rank라는 임시 변수에 저장
            LottoRank rank = LottoRank.find(winningCount, hasBonus);

            // 통계판에 적힌 지금 당첨 개수 가지고 오기
            int currentWinningCount = winningStatistics.get(rank);

            // 통계판에 현재 당첨 등수에 해당하는 개수에 1 더하기
            winningStatistics.put(rank, currentWinningCount + 1);
        }

        // 당첨 내역 출력 양식
        // 3개 일치 (5,000원) - 1개
        // 4개 일치 (50,000원) - 0개
        // 5개 일치 (1,500,000원) - 0개
        // 5개 일치, 보너스 볼 일치 (30,000,000원) - 0개
        // 6개 일치 (2,000,000,000원) - 0개

        List<LottoRank> rankForPrinting = List.of(LottoRank.FIFTH, LottoRank.FOURTH, LottoRank.THIRD, LottoRank.SECOND, LottoRank.FIRST);

        for(LottoRank rank1 : rankForPrinting) {
            int winningCounting = rank1.getWinningCount();
            String winningPrize = String.format("%,d", rank1.getWinningPrize());
            int rank = winningStatistics.get(rank1);
            boolean hasBonus = rank1.isNeedBonus();

            System.out.print(winningCounting + "개 일치");

            if(winningCounting == 5 && hasBonus) {
                System.out.print(", 보너스 볼 일치");
            }

            System.out.print(" (" + winningPrize + "원) ");
            System.out.println("- " + rank + "개");
        }
    }
}
