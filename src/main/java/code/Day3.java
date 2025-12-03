package code;

import code.utils.DayUtils;

import java.math.BigInteger;
import java.util.List;

class Day3 {

  public static void main(String[] args) {
    DayUtils utils = new DayUtils(3, 1, DayUtils.FetchOption.FETCH_IF_EMPTY);

    List<Bank> banks = initInput(utils.getListInput());
    utils.startTimer();
    BigInteger sum = solve(banks);
    utils.endTimer();
    utils.printAnswer(sum);

    utils.startTimer();

    utils.endTimer();
//    utils.printAnswer(sum2);
  }

  private static BigInteger solve(List<Bank> banks) {
    BigInteger sum = BigInteger.ZERO;

    for (Bank bank : banks)
    {
      Integer max1 = 1;
      Integer max2 = 1;
      int backup = 0;
      int size = bank.cells().size();
      System.out.println(bank);
      for (int i = 0; i < size - 1; i++)
      {
        Integer cell = bank.cells().get(i);
        if (cell > max1) {
          max1 = cell;
          backup = i;
        }
      }
      for (int i = backup + 1; i < size; i++)
      {
        Integer cell = bank.cells().get(i);
        if (cell > max2) {
          max2 = cell;
        }
      }
      BigInteger joltage = BigInteger.valueOf(max1 * 10 + max2);
      System.out.println(joltage);
      sum = sum.add(joltage);
    }
    return sum;
  }

  private static List<Bank> initInput(List<String> input) {
    return input.stream().map(b -> new Bank(b.chars().map(d -> d - '0').boxed().toList())).toList();
  }

  record Bank(List<Integer> cells) {
  }


}