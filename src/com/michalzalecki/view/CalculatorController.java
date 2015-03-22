package com.michalzalecki.view;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

/**
 * Created by Michal on 2015-03-21.
 */
public class CalculatorController {
    public Button btnResult;
    public Button btnMod;
    public Button btnRoot;
    public Button btnPow;
    public Button btnMemSub;
    public Button btnMemAdd;
    public Button btnMemSave;
    public Button btnMemClr;
    public Button btnMemRead;
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
    public Button btnDiv;
    public Button btnMul;
    public Button btnSub;
    public Button btnAdd;
    public TextField textFieldResult;
    public TextField textFieldBinDigitsRow2;
    public TextField textFieldBinDigitsRow1;
    public Button btnModBin;
    public Button btnModOct;
    public Button btnModDec;
    public Button btnModHex;
    public Button btnBitNot;
    public Button btnBitAnd;
    public Button btnBitOr;
    public Button btnBitXor;
    public Button btnBitLsh;
    public Button btnBitRsh;
    public Button btnComa;

    private int mode = 10;
    private long prev;
    private long mem = 0;

    private int operation = 0;
    private static final int OP_RESET = 0;
    private static final int OP_ADD = 1;
    private static final int OP_SUB = 2;
    private static final int OP_MUL = 3;
    private static final int OP_DIV = 4;
    private static final int OP_MOD = 5;
    private static final int OP_ROOT = 6;
    private static final int OP_POW = 7;
    private static final int OP_AND = 8;
    private static final int OP_OR = 9;
    private static final int OP_XOR = 10;
    private static final int OP_LSH = 11;
    private static final int OP_RSH = 12;

    private int entering = 0;
    private static final int EN_APPEND = 0;
    private static final int EN_NEW = 1;

    private Scene rootScene;

    public void initialize() {
        setMode(10);
    }

    private void resultSet(String result) {
        String finalResult;
        if (result.length() == 0 || result.equals("-"))
            finalResult = "0";
        else
            finalResult = result.toUpperCase();

        textFieldResult.setText(finalResult);
        setRows();
    }

    private void resultSet(long n) {
        String result = null;
        boolean isNegative = n < 0;
        // Use abs to avoid leading ffff... after convert
        n = Math.abs(n);
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
        if (isNegative)
            negate(result);
        else
            resultSet(result);
    }

    // negate number with "-" instead of leading "ffff..."
    private void negate(String current) {
        if (current.indexOf("-") == 0)
            resultSet(current.substring(1, current.length()));
        else if (!current.equals("0"))
            resultSet("-" + current);
    }

    // Get result with appropriate mode
    private long getLongResult(int from) {
        return Long.parseLong(textFieldResult.getText(), from);
    }
    // Use default mode
    private long getLongResult() {
        return getLongResult(mode);
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

        textFieldBinDigitsRow1.setText(row1WithZeros.trim());
        textFieldBinDigitsRow2.setText(row2WithZeros.trim());
    }

    private void resultAddKey(String key) {
        String current = textFieldResult.getText();
        if (current.equals("0") || entering == EN_NEW) {
            resultSet(key);
            entering = EN_APPEND;
        } else {
            resultSet(current + key);
        }
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

        switch (newMode) {
            case 2:
                btnModBin.getStyleClass().add("active");
                unlockBinDigitButtons();
                break;
            case 8:
                btnModOct.getStyleClass().add("active");
                unlockOctDigitButtons();
                break;
            case 10:
                btnModDec.getStyleClass().add("active");
                unlockDecDigitButtons();
                break;
            case 16:
                unlockHexDigitButtons();
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
        resultSet(getLongResult(from));
    }

    // Locking digits
    private void unlockHexDigitButtons() {
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
    }

    private void unlockDecDigitButtons() {
        unlockHexDigitButtons();
        btnDigitA.setDisable(true);
        btnDigitB.setDisable(true);
        btnDigitC.setDisable(true);
        btnDigitD.setDisable(true);
        btnDigitE.setDisable(true);
        btnDigitF.setDisable(true);
    }

    private void unlockOctDigitButtons() {
        unlockDecDigitButtons();
        btnDigit8.setDisable(true);
        btnDigit9.setDisable(true);
    }

    private void unlockBinDigitButtons() {
        unlockOctDigitButtons();
        btnDigit2.setDisable(true);
        btnDigit3.setDisable(true);
        btnDigit4.setDisable(true);
        btnDigit5.setDisable(true);
        btnDigit6.setDisable(true);
        btnDigit7.setDisable(true);
    }

    public void actionBtnMemSub(ActionEvent actionEvent) {
        mem -= getLongResult();
    }

    public void actionBtnMemAdd(ActionEvent actionEvent) {
        mem += getLongResult();
    }

    public void actionBtnMemSave(ActionEvent actionEvent) {
        mem = getLongResult();
    }

    public void actionBtnMemClr(ActionEvent actionEvent) {
        mem = 0;
    }

    public void actionBtnMemRead(ActionEvent actionEvent) {
        entering = EN_NEW;
        resultSet(mem);
    }

    private boolean isKayAllowed(String k) {
        long n = Long.parseLong(k, 16);
        return n < mode;
    }

    public void actionBtnDigitA(ActionEvent actionEvent) {
        if (isKayAllowed("A")) resultAddKey("A");
    }

    public void actionBtnDigitB(ActionEvent actionEvent) {
        if (isKayAllowed("B")) resultAddKey("B");
    }

    public void actionBtnDigitC(ActionEvent actionEvent) {
        if (isKayAllowed("C")) resultAddKey("C");
    }

    public void actionBtnDigitD(ActionEvent actionEvent) {
        if (isKayAllowed("D")) resultAddKey("D");
    }

    public void actionBtnDigitE(ActionEvent actionEvent) {
        if (isKayAllowed("E")) resultAddKey("E");
    }

    public void actionBtnDigitF(ActionEvent actionEvent) {
        if (isKayAllowed("F")) resultAddKey("F");
    }

    public void actionBtnDigit0(ActionEvent actionEvent) {
        if (isKayAllowed("0")) resultAddKey("0");
    }

    public void actionBtnDigit1(ActionEvent actionEvent) {
        if (isKayAllowed("1")) resultAddKey("1");
    }

    public void actionBtnDigit2(ActionEvent actionEvent) {
        if (isKayAllowed("2")) resultAddKey("2");
    }

    public void actionBtnDigit3(ActionEvent actionEvent) {
        if (isKayAllowed("3")) resultAddKey("3");
    }

    public void actionBtnDigit6(ActionEvent actionEvent) {
        if (isKayAllowed("6")) resultAddKey("6");
    }

    public void actionBtnDigit5(ActionEvent actionEvent) {
        if (isKayAllowed("5")) resultAddKey("5");
    }

    public void actionBtnDigit4(ActionEvent actionEvent) {
        if (isKayAllowed("4")) resultAddKey("4");
    }

    public void actionBtnDigit7(ActionEvent actionEvent) {
        if (isKayAllowed("7")) resultAddKey("7");
    }

    public void actionBtnDigit8(ActionEvent actionEvent) {
        if (isKayAllowed("8")) resultAddKey("8");
    }

    public void actionBtnDigit9(ActionEvent actionEvent) {
        if (isKayAllowed("9")) resultAddKey("9");
    }

    public void actionBtnBack(ActionEvent actionEvent) {
        resultSubKey();
    }

    public void actionBtnCE(ActionEvent actionEvent) {
        operation = OP_RESET;
        resetOpButtons();
        resultSet("0");
    }

    public void actionBtnC(ActionEvent actionEvent) {
        resultSet("0");
    }

    public void actionBtnOpo(ActionEvent actionEvent) {
        entering = EN_NEW;
        negate(textFieldResult.getText());
    }

    public void actionBtnBitNot(ActionEvent actionEvent) {
        entering = EN_NEW;
        resultSet(~getLongResult());
    }

    public void actionBtnDiv(ActionEvent actionEvent) {
        newOperation();
        operation = OP_DIV;
        btnDiv.getStyleClass().add("active");
    }

    public void actionBtnMul(ActionEvent actionEvent) {
        newOperation();
        operation = OP_MUL;
        btnMul.getStyleClass().add("active");
    }

    public void actionBtnSub(ActionEvent actionEvent) {
        newOperation();
        operation = OP_SUB;
        btnSub.getStyleClass().add("active");
    }

    public void actionBtnAdd(ActionEvent actionEvent) {
        newOperation();
        operation = OP_ADD;
        btnAdd.getStyleClass().add("active");
    }

    public void actionBtnMod(ActionEvent actionEvent) {
        newOperation();
        operation = OP_MOD;
        btnMod.getStyleClass().add("active");
    }

    public void actionBtnRoot(ActionEvent actionEvent) {
        newOperation();
        operation = OP_ROOT;
        btnRoot.getStyleClass().add("active");
    }

    public void actionBtnPow(ActionEvent actionEvent) {
        newOperation();
        operation = OP_POW;
        btnPow.getStyleClass().add("active");
    }

    public void actionBtnBitAnd(ActionEvent actionEvent) {
        newOperation();
        operation = OP_AND;
        btnBitAnd.getStyleClass().add("active");
    }

    public void actionBtnBitOr(ActionEvent actionEvent) {
        newOperation();
        operation = OP_OR;
        btnBitOr.getStyleClass().add("active");
    }

    public void actionBtnBitXor(ActionEvent actionEvent) {
        newOperation();
        operation = OP_XOR;
        btnBitXor.getStyleClass().add("active");
    }

    public void actionBtnBitLsh(ActionEvent actionEvent) {
        newOperation();
        operation = OP_LSH;
        btnBitLsh.getStyleClass().add("active");
    }

    public void actionBtnBitRsh(ActionEvent actionEvent) {
        newOperation();
        operation = OP_RSH;
        btnBitRsh.getStyleClass().add("active");
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


    public void actionBtnResult(ActionEvent actionEvent) {
        switch (operation) {
            case OP_ADD:
                resultSet(prev + getLongResult());
                break;
            case OP_SUB:
                resultSet(prev - getLongResult());
                break;
            case OP_MUL:
                resultSet(prev * getLongResult());
                break;
            case OP_DIV:
                resultSet(prev / getLongResult());
                break;
            case OP_MOD:
                resultSet(prev % getLongResult());
                break;
            case OP_POW:
                resultSet((long)Math.pow(prev, getLongResult()));
                break;
            case OP_ROOT:
                resultSet((long)Math.pow(prev, 1.0/getLongResult()));
                break;
            case OP_AND:
                resultSet(prev & getLongResult());
                break;
            case OP_OR:
                resultSet(prev | getLongResult());
                break;
            case OP_XOR:
                resultSet(prev ^ getLongResult());
                break;
            case OP_LSH:
                resultSet(prev << getLongResult());
                break;
            case OP_RSH:
                resultSet(prev >> getLongResult());
                break;
            default:
        }
        entering = EN_NEW;
        operation = OP_RESET;
        resetOpButtons();
    }

    private void resetOpButtons () {
        btnAdd.getStyleClass().remove("active");
        btnSub.getStyleClass().remove("active");
        btnDiv.getStyleClass().remove("active");
        btnMul.getStyleClass().remove("active");
        btnMod.getStyleClass().remove("active");
        btnRoot.getStyleClass().remove("active");
        btnPow.getStyleClass().remove("active");
        btnBitAnd.getStyleClass().remove("active");
        btnBitOr.getStyleClass().remove("active");
        btnBitXor.getStyleClass().remove("active");
        btnBitLsh.getStyleClass().remove("active");
        btnBitRsh.getStyleClass().remove("active");
    }

    private void newOperation() {
        entering = EN_NEW;
        prev = getLongResult();
        resetOpButtons();
    }

    public void setRootScene(Scene scene) {
        this.rootScene = scene;
        this.rootScene.setOnKeyPressed((KeyEvent e) -> {
            KeyCode keyCode = e.getCode();

//            System.out.println(keyCode);

            if (keyCode == KeyCode.DIGIT0 || keyCode == KeyCode.NUMPAD0)
                actionBtnDigit0(new ActionEvent());
            else if (keyCode == KeyCode.DIGIT1 || keyCode == KeyCode.NUMPAD1)
            actionBtnDigit1(new ActionEvent());
            else if (keyCode == KeyCode.DIGIT2 || keyCode == KeyCode.NUMPAD2)
                actionBtnDigit2(new ActionEvent());
            else if (keyCode == KeyCode.DIGIT3 || keyCode == KeyCode.NUMPAD3)
                actionBtnDigit3(new ActionEvent());
            else if (keyCode == KeyCode.DIGIT4 || keyCode == KeyCode.NUMPAD4)
                actionBtnDigit4(new ActionEvent());
            else if (keyCode == KeyCode.DIGIT5 || keyCode == KeyCode.NUMPAD5)
                actionBtnDigit5(new ActionEvent());
            else if (keyCode == KeyCode.DIGIT6 || keyCode == KeyCode.NUMPAD6)
                actionBtnDigit6(new ActionEvent());
            else if (keyCode == KeyCode.DIGIT7 || keyCode == KeyCode.NUMPAD7)
                actionBtnDigit7(new ActionEvent());
            else if (keyCode == KeyCode.DIGIT8 || keyCode == KeyCode.NUMPAD8)
                actionBtnDigit8(new ActionEvent());
            else if (keyCode == KeyCode.DIGIT9 || keyCode == KeyCode.NUMPAD9)
                actionBtnDigit9(new ActionEvent());
            else if (keyCode == KeyCode.A)
                actionBtnDigitA(new ActionEvent());
            else if (keyCode == KeyCode.B)
                actionBtnDigitB(new ActionEvent());
            else if (keyCode == KeyCode.C)
                actionBtnDigitC(new ActionEvent());
            else if (keyCode == KeyCode.D)
                actionBtnDigitD(new ActionEvent());
            else if (keyCode == KeyCode.E)
                actionBtnDigitE(new ActionEvent());
            else if (keyCode == KeyCode.F)
                actionBtnDigitF(new ActionEvent());
            else if (keyCode == KeyCode.BACK_SPACE)
                resultSubKey();
            else if (keyCode == KeyCode.DELETE)
                actionBtnCE(new ActionEvent());
            else if (keyCode == KeyCode.MULTIPLY)
                actionBtnMul(new ActionEvent());
            else if (keyCode == KeyCode.DIVIDE)
                actionBtnDiv(new ActionEvent());
            else if (keyCode == KeyCode.ADD)
                actionBtnAdd(new ActionEvent());
            else if (keyCode == KeyCode.SUBTRACT || keyCode == KeyCode.MINUS)
                actionBtnSub(new ActionEvent());
            else if (keyCode == KeyCode.ENTER || keyCode == KeyCode.EQUALS)
                actionBtnResult(new ActionEvent());
        });
    }
}
