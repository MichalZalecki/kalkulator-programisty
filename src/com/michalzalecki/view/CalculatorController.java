package com.michalzalecki.view;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Michal on 2015-03-21.
 */
public class CalculatorController {
    public Button btnResult;
    public Button btnFlip;
    public Button btnMod;
    public Button btnSqrt;
    public Button btnMemSub;
    public Button btnMemAdd;
    public Button btnMemSave;
    public Button btnMemClr;
    public Button btnDigitA;
    public Button btnDigitB;
    public Button btnDigitC;
    public Button btnDigitD;
    public Button btnDigitE;
    public Button btnDigitF;
    public Button btnDigit0;
    public Button btnDigit1;
    public Button btnDigit2;
    public Button btnDigit3;
    public Button btnDigit4;
    public Button btnDigit5;
    public Button btnDigit6;
    public Button btnDigit7;
    public Button btnDigit8;
    public Button btnDigit9;
    public Button btnBack;
    public Button btnCE;
    public Button btnC;
    public Button btnOpo;
    public Button btnDivi;
    public Button btnMul;
    public Button btnSub;
    public Button btnAdd;
    public Button btnComa;
    public TextField textFieldResult;
    public TextField textFieldBinDigitsRow2;
    public TextField textFieldBinDigitsRow1;
    public Button btnModBin;
    public Button btnModOct;
    public Button btnModDec;
    public Button btnModHex;

    private int mode = 10;

    public void initialize() {
        setMode(10);
    }

    private void resultSet(String result) {
        String finalResult;
        if (result.length() == 0)
            finalResult = "0";
        else
            finalResult = result.toUpperCase();

        textFieldResult.setText(finalResult);
        setRows();
    }

    private long getLongResult() {
        return Long.parseLong(textFieldResult.getText(), mode);
    }

    private void setRows() {
        long n = getLongResult();
        String rows = Long.toBinaryString(n);
        String zeros = StringUtils.repeat("0", 64 - rows.length());
        rows = zeros + rows;
        String row1 = rows.substring(0, 32);
        String row2 = rows.substring(32, 64);
        String row1WithZeros = "";
        String row2WithZeros = "";
        for (int i = 0; i < 32; i++)
            row1WithZeros += i % 4 == 0 ? " " + row1.charAt(i) : row1.charAt(i);
        for (int i = 0; i < 32; i++)
            row2WithZeros += i % 4 == 0 ? " " + row2.charAt(i) : row2.charAt(i);

        textFieldBinDigitsRow1.setText(row1WithZeros);
        textFieldBinDigitsRow2.setText(row2WithZeros);
    }

    private void resultAddKey(String key) {
        String current = textFieldResult.getText();
        if (current.equals("0"))
            resultSet(key);
        else
            resultSet(current + key);
    }

    private void resultSubKey() {
        String current = textFieldResult.getText();
        resultSet(current.substring(0, current.length() - 1));
    }

    private void setMode(int newMode) {
        // Deactivate all mode buttons
        btnModBin.getStyleClass().remove("active");
        btnModOct.getStyleClass().remove("active");
        btnModDec.getStyleClass().remove("active");
        btnModHex.getStyleClass().remove("active");
        // Enable all digits
        btnDigitA.setDisable(false);
        btnDigitB.setDisable(false);
        btnDigitC.setDisable(false);
        btnDigitD.setDisable(false);
        btnDigitE.setDisable(false);
        btnDigitF.setDisable(false);
        btnDigit0.setDisable(false);
        btnDigit1.setDisable(false);
        btnDigit2.setDisable(false);
        btnDigit3.setDisable(false);
        btnDigit4.setDisable(false);
        btnDigit5.setDisable(false);
        btnDigit6.setDisable(false);
        btnDigit7.setDisable(false);
        btnDigit8.setDisable(false);
        btnDigit9.setDisable(false);

        switch (newMode) {
            case 2:
                btnModBin.getStyleClass().add("active");
                btnDigitA.setDisable(true);
                btnDigitB.setDisable(true);
                btnDigitC.setDisable(true);
                btnDigitD.setDisable(true);
                btnDigitE.setDisable(true);
                btnDigitF.setDisable(true);
                btnDigit2.setDisable(true);
                btnDigit3.setDisable(true);
                btnDigit4.setDisable(true);
                btnDigit5.setDisable(true);
                btnDigit6.setDisable(true);
                btnDigit7.setDisable(true);
                btnDigit8.setDisable(true);
                btnDigit9.setDisable(true);
                break;
            case 8:
                btnModOct.getStyleClass().add("active");
                btnDigitA.setDisable(true);
                btnDigitB.setDisable(true);
                btnDigitC.setDisable(true);
                btnDigitD.setDisable(true);
                btnDigitE.setDisable(true);
                btnDigitF.setDisable(true);
                btnDigit8.setDisable(true);
                btnDigit9.setDisable(true);
                break;
            case 10:
                btnModDec.getStyleClass().add("active");
                btnDigitA.setDisable(true);
                btnDigitB.setDisable(true);
                btnDigitC.setDisable(true);
                btnDigitD.setDisable(true);
                btnDigitE.setDisable(true);
                btnDigitF.setDisable(true);
                break;
            case 16:
                btnModHex.getStyleClass().add("active");
                break;
            default:
                throw new RuntimeException("Nieobsługiwany mode (" + newMode + ")");
        }
        int oldMode = mode;
        // set mode
        mode = newMode;
        // convert result from oldMode to the new one
        convertResult(oldMode);
    }

    private void convertResult(int from) {
        long n = Long.parseLong(textFieldResult.getText(), from);
        String result = null;
        switch (mode) {
            case 2:
                result = Long.toBinaryString(n);
                break;
            case 8:
                result = Long.toOctalString(n);
                break;
            case 10:
                result = Long.toString(n);
                break;
            case 16:
                result = Long.toHexString(n);
                break;
            default:
                throw new RuntimeException("Nieobsługiwany mode (" + mode + ")");
        }
        resultSet(result);
    }

    public void actionBtnFlip(ActionEvent actionEvent) {
    }

    public void actionBtnMod(ActionEvent actionEvent) {
    }

    public void actionBtnSqrt(ActionEvent actionEvent) {
    }

    public void actionBtnMemSub(ActionEvent actionEvent) {
    }

    public void actionBtnMemAdd(ActionEvent actionEvent) {
    }

    public void actionBtnMemSave(ActionEvent actionEvent) {
    }

    public void actionBtnMemClr(ActionEvent actionEvent) {
    }

    public void actionBtnDigitA(ActionEvent actionEvent) {
        resultAddKey("A");
    }

    public void actionBtnDigitB(ActionEvent actionEvent) {
        resultAddKey("B");
    }

    public void actionBtnDigitC(ActionEvent actionEvent) {
        resultAddKey("C");
    }

    public void actionBtnDigitD(ActionEvent actionEvent) {
        resultAddKey("D");
    }

    public void actionBtnDigitE(ActionEvent actionEvent) {
        resultAddKey("E");
    }

    public void actionBtnDigitF(ActionEvent actionEvent) {
        resultAddKey("F");
    }

    public void actionBtnDigit0(ActionEvent actionEvent) {
        resultAddKey("0");
    }

    public void actionBtnDigit1(ActionEvent actionEvent) {
        resultAddKey("1");
    }

    public void actionBtnDigit2(ActionEvent actionEvent) {
        resultAddKey("2");
    }

    public void actionBtnDigit3(ActionEvent actionEvent) {
        resultAddKey("3");
    }

    public void actionBtnDigit6(ActionEvent actionEvent) {
        resultAddKey("6");
    }

    public void actionBtnDigit5(ActionEvent actionEvent) {
        resultAddKey("5");
    }

    public void actionBtnDigit4(ActionEvent actionEvent) {
        resultAddKey("4");
    }

    public void actionBtnDigit7(ActionEvent actionEvent) {
        resultAddKey("7");
    }

    public void actionBtnDigit8(ActionEvent actionEvent) {
        resultAddKey("8");
    }

    public void actionBtnDigit9(ActionEvent actionEvent) {
        resultAddKey("9");
    }

    public void actionBtnBack(ActionEvent actionEvent) {
        resultSubKey();
    }

    public void actionBtnCE(ActionEvent actionEvent) {
    }

    public void actionBtnC(ActionEvent actionEvent) {
    }

    public void actionBtnOpo(ActionEvent actionEvent) {
    }

    public void actionBtnDivi(ActionEvent actionEvent) {
    }

    public void actionBtnMul(ActionEvent actionEvent) {
    }

    public void actionBtnSub(ActionEvent actionEvent) {
    }

    public void actionBtnAdd(ActionEvent actionEvent) {
    }

    public void actionBtnComa(ActionEvent actionEvent) {

    }

    public void actionBtnModBin(ActionEvent actionEvent) {
        setMode(2);
    }

    public void actionBtnModOct(ActionEvent actionEvent) {
        setMode(8);
    }

    public void actionBtnModDec(ActionEvent actionEvent) {
        setMode(10);
    }

    public void actionBtnModHex(ActionEvent actionEvent) {
        setMode(16);
    }

    public void resultUpdatedByUser(Event event) {
        resultSet(textFieldResult.getText());
    }
}
