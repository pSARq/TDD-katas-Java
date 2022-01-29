package calculadora;

import java.util.ArrayList;
import java.util.Arrays;

public class StringCalculator {

    public int add(String values) {

        if (values.length() > 0) {
            String delimiterInitial = calculateDelimiterInitial(values);
            StringBuilder auxiliarString = transformList(values);
            String[] splittedList = calculateSplittedList(delimiterInitial, auxiliarString);
            return calculateAcumulator(splittedList);
        }

        return 0;
    }

    private int calculateAcumulator(String[] splittedList) {
        ArrayList<Integer> numberList = calculateNumberList(splittedList);
        return numberList.stream().mapToInt(number -> number).sum();
    }

    private ArrayList<Integer> calculateNumberList(String[] splittedList) {
        ArrayList<Integer> numberList = new ArrayList<>();
        Arrays.stream(splittedList)
                .mapToInt(Integer::parseInt)
                .forEach(tempValue -> {
                    if (tempValue < 0) {
                        throw new ExceptionNegativeNumber();
                    }
                    if (tempValue > 1000) {
                        return;
                    }
                    numberList.add(tempValue);
                });
        return numberList;
    }

    private String[] calculateSplittedList(String delimiterInitial, StringBuilder cadena) {
        String auxiliarString = String.valueOf(cadena);
        if (delimiterInitial != null) {
            return auxiliarString.substring(1).split(delimiterInitial);
        }
        return auxiliarString.split("[,\n-]");
    }

    private StringBuilder transformList(String values) {
        String[] splittedList = values.split("");
        StringBuilder auxiliarString = new StringBuilder();
        boolean isSeparator = true;

        for (String element : splittedList) {
            if (isSeparator) {
                if (!element.equals("[")) {
                    auxiliarString.append(element);
                } else {
                    auxiliarString.append("-");
                    isSeparator = false;
                }
            } else if (element.equals("]")) {
                isSeparator = true;
            }
        }
        return auxiliarString;
    }

    private String calculateDelimiterInitial(String values) {
        try {
            Integer.parseInt(String.valueOf(values.charAt(0)));
        } catch (Exception e) {
            if (!String.valueOf(values.charAt(0)).equals("-")) {
                return String.valueOf(values.charAt(0));
            }
        }
        return null;
    }
}