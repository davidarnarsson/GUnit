import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import edu.chl.bank.Wallet;
import org.junit.Before;
import org.junit.Test;

public class TestWallet {


    @Test
    public void testWithdrawShouldNotResultInNegative() {
        Wallet w = new Wallet();

        double withdrawn = w.withdraw(100);

        assertEquals("Amount == 0", withdrawn, 0, 1);
    }

    @Test
    public void testWithdrawAcceptableAmount() {
        Wallet w = new Wallet();
        w.deposit(100);
        double withdrawn = w.withdraw(50);

        assertEquals("Withdrawn == 50", withdrawn, 00, 1);
        assertEquals("Remainder == 50", w.getAmount(), 50, 1);
    }
}