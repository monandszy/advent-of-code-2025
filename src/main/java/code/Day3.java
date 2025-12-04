package code;

import code.utils.DayUtils;

import java.math.BigInteger;
import java.util.List;

class Day3 {

  public static void main(String[] args) {
    DayUtils utils = new DayUtils(3, 1, DayUtils.FetchOption.FETCH_IF_EMPTY);

    List<Bank> banks = initInput(utils.getListInput());
    utils.startTimer();
    BigInteger sum = solve(banks, 2);
    utils.endTimer();
    utils.printAnswer(sum);

    utils.startTimer();
    BigInteger sum2 = solve(banks, 12);
    utils.endTimer();
    utils.printAnswer(sum2);
  }

  private static BigInteger solve(List<Bank> banks, int choices) {
    BigInteger sum = BigInteger.ZERO;
    for (Bank bank : banks) {
      BigInteger voltage = BigInteger.ZERO;
//      System.out.println(bank);
      int backup = 0;
      for(int c = choices; c > 0; c--)
      {
        int max = 0;
        int size = bank.cells().size();
        for (int i = backup; i < size - c + 1; i++) {
          int cell = bank.cells().get(i);
          if (cell > max) {
            max = cell;
            backup = i + 1;
          }
        }
        voltage = voltage.multiply(BigInteger.TEN).add(BigInteger.valueOf(max));
      }
      System.out.println(voltage);
      sum = sum.add(voltage);
    }
    return sum;
  }

  private static List<Bank> initInput(List<String> input) {
    return input.stream().map(b -> new Bank(b.chars().map(d -> d - '0').boxed().toList())).toList();
  }

  record Bank(List<Integer> cells) {
  }


}