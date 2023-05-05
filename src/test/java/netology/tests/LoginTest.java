package netology.tests;

import netology.data.DBHelper;
import netology.data.DataGenerator;
import netology.data.DataHelper;
import org.junit.jupiter.api.*;


public class LoginTest {
    @AfterAll
    public void removeDB() {
        DBHelper.removeDB();
    }

    String token = DataGenerator.getToken();


    @Test
    void shouldSuccessTransferFromCard1ToCard2() {
        String from = DataHelper.card1().getNumberCard();
        int initialBalanceCard1 = DataGenerator.getBalance(DataHelper.card1().getId(), token);
        int initialBalanceCard2 = DataGenerator.getBalance(DataHelper.card2().getId(), token);

        int amount = DataGenerator.generateValidAmount(initialBalanceCard1);
        DataGenerator.transferFrom(from, amount, token);

        int actualBalanceCard1 = DataGenerator.getBalance(DataHelper.card1().getId(), token);
        int actualBalanceCard2 = DataGenerator.getBalance(DataHelper.card2().getId(), token);

        Assertions.assertEquals(initialBalanceCard1 - amount, actualBalanceCard1);
        Assertions.assertEquals(initialBalanceCard2 + amount, actualBalanceCard2);
    }

    @Test
    void shouldSuccessTransferFromCard2ToCard1() {
        String from = DataHelper.card2().getNumberCard();
        int initialBalanceCard1 = DataGenerator.getBalance(DataHelper.card1().getId(), token);
        int initialBalanceCard2 = DataGenerator.getBalance(DataHelper.card2().getId(), token);

        int amount = DataGenerator.generateValidAmount(initialBalanceCard2);

        DataGenerator.transferFrom(from, amount, token);

        int actualBalanceCard1 = DataGenerator.getBalance(DataHelper.card1().getId(), token);
        int actualBalanceCard2 = DataGenerator.getBalance(DataHelper.card2().getId(), token);

        Assertions.assertEquals(initialBalanceCard2 - amount, actualBalanceCard2);
        Assertions.assertEquals(initialBalanceCard1 + amount, actualBalanceCard1);
    }

    @Test
    void shouldSuccessTransferFromInvalidCardToCard1() {
        String from = DataHelper.invalidCard().getNumberCard();
        int initialBalanceCard1 = DataGenerator.getBalance(DataHelper.card1().getId(), token);

        int amount = 100;

        DataGenerator.transferFrom(from, amount, token);

        int actualBalanceCard1 = DataGenerator.getBalance(DataHelper.card1().getId(), token);

        Assertions.assertEquals(initialBalanceCard1, actualBalanceCard1);
    }

    @Test
    void shouldSuccessTransferAboveCurrentBalance() {
        String from = DataHelper.card1().getNumberCard();
        int initialBalanceCard1 = DataGenerator.getBalance(DataHelper.card1().getId(), token);
        int initialBalanceCard2 = DataGenerator.getBalance(DataHelper.card2().getId(), token);

        int amount = DataGenerator.generateInvalidAmount(initialBalanceCard1);

        DataGenerator.transferFrom(from, amount, token);

        int actualBalanceCard1 = DataGenerator.getBalance(DataHelper.card1().getId(), token);
        int actualBalanceCard2 = DataGenerator.getBalance(DataHelper.card2().getId(), token);

        Assertions.assertEquals(initialBalanceCard1, actualBalanceCard1);
        Assertions.assertEquals(initialBalanceCard2, actualBalanceCard2);
    }

    @Test
    void shouldSuccessTransferNegativeAmount() {
        String from = DataHelper.card1().getNumberCard();
        int initialBalanceCard1 = DataGenerator.getBalance(DataHelper.card1().getId(), token);
        int initialBalanceCard2 = DataGenerator.getBalance(DataHelper.card2().getId(), token);

        int amount = -100;

        DataGenerator.transferFrom(from, amount, token);

        int actualBalanceCard1 = DataGenerator.getBalance(DataHelper.card1().getId(), token);
        int actualBalanceCard2 = DataGenerator.getBalance(DataHelper.card2().getId(), token);

        Assertions.assertEquals(initialBalanceCard1, actualBalanceCard1);
        Assertions.assertEquals(initialBalanceCard2, actualBalanceCard2);
    }
}



