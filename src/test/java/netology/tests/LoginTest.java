package netology.tests;

import netology.data.DBHelper;
import netology.data.DataGenerator;
import netology.data.DataHelper;
import org.junit.jupiter.api.*;


public class LoginTest {
    @AfterAll
    public static void removeDB() {
        DBHelper.removeDB();
    }

    String token = DataGenerator.getToken();

    @Test
    void shouldSuccessTransferFromCard1ToCard2() {

        String from = DataHelper.card1().getNumberCard();
        String to = DataHelper.card2().getNumberCard();

        int initialBalanceCard1 = DataGenerator.getBalance(DataHelper.card1().getId(), token);
        int initialBalanceCard2 = DataGenerator.getBalance(DataHelper.card2().getId(), token);

        int amount = DataGenerator.generateValidAmount(initialBalanceCard1);

        DataHelper.TransferInfo transferInfo = new DataHelper.TransferInfo(from, to, amount);

        DataGenerator.transferFrom(transferInfo, token, 200);

        int actualBalanceCard1 = DataGenerator.getBalance(DataHelper.card1().getId(), token);
        int actualBalanceCard2 = DataGenerator.getBalance(DataHelper.card2().getId(), token);

        Assertions.assertEquals(initialBalanceCard1 - amount, actualBalanceCard1);
        Assertions.assertEquals(initialBalanceCard2 + amount, actualBalanceCard2);
    }

    @Test
    void shouldSuccessTransferFromCard2ToCard1() {
        String from = DataHelper.card2().getNumberCard();
        String to = DataHelper.card1().getNumberCard();

        int initialBalanceCard1 = DataGenerator.getBalance(DataHelper.card1().getId(), token);
        int initialBalanceCard2 = DataGenerator.getBalance(DataHelper.card2().getId(), token);

        int amount = DataGenerator.generateValidAmount(initialBalanceCard2);

        DataHelper.TransferInfo transferInfo = new DataHelper.TransferInfo(from, to, amount);

        DataGenerator.transferFrom(transferInfo, token, 200);

        int actualBalanceCard1 = DataGenerator.getBalance(DataHelper.card1().getId(), token);
        int actualBalanceCard2 = DataGenerator.getBalance(DataHelper.card2().getId(), token);

        Assertions.assertEquals(initialBalanceCard2 - amount, actualBalanceCard2);
        Assertions.assertEquals(initialBalanceCard1 + amount, actualBalanceCard1);
    }

    @Test
    void shouldUnsuccessfulTransferFromInvalidCardToCard1() {
        String from = DataHelper.invalidCard().getNumberCard();
        String to = DataHelper.card1().getNumberCard();

        int amount = 100;

        DataHelper.TransferInfo transferInfo = new DataHelper.TransferInfo(from, to, amount);
        DataGenerator.transferFrom(transferInfo, token, 500);
    }

    @Test
    void shouldUnsuccessfulTransferAboveCurrentBalance() {
        String from = DataHelper.card1().getNumberCard();
        String to = DataHelper.card2().getNumberCard();
        int initialBalanceCard1 = DataGenerator.getBalance(DataHelper.card1().getId(), token);

        int amount = DataGenerator.generateInvalidAmount(initialBalanceCard1);
        DataHelper.TransferInfo transferInfo = new DataHelper.TransferInfo(from, to, amount);
        DataGenerator.transferFrom(transferInfo, token, 500);
    }

    @Test
    void shouldUnsuccessfulTransferNegativeAmount() {
        String from = DataHelper.card1().getNumberCard();
        String to = DataHelper.card2().getNumberCard();

        int amount = -100;

        DataHelper.TransferInfo transferInfo = new DataHelper.TransferInfo(from, to, amount);
        DataGenerator.transferFrom(transferInfo, token, 500);
    }

    @Test
    void shouldUnsuccessfulTransferToTheSameCard() {
        int initialBalanceCard1 = DataGenerator.getBalance(DataHelper.card1().getId(), token);

        String from = DataHelper.card1().getNumberCard();
        String to = DataHelper.card1().getNumberCard();

        int amount = DataGenerator.generateValidAmount(initialBalanceCard1);

        DataHelper.TransferInfo transferInfo = new DataHelper.TransferInfo(from, to, amount);

        DataGenerator.transferFrom(transferInfo, token, 500);
    }
}



