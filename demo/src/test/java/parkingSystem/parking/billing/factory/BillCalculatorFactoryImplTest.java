package parkingSystem.parking.billing.factory;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import parkingSystem.parking.billing.Bill;
import parkingSystem.parking.billing.BillingType;
import parkingSystem.parking.billing.strategy.BillCalculator;

public class BillCalculatorFactoryImplTest {

    static Path path = Path.of(System.getProperty("user.dir"), "billCalculator.cfg");

    @BeforeAll
    static void crateBillCalculatorConfig() {
        assertDoesNotThrow(() -> {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path.toFile()));
            bw.append(
                    "hourTax : 10.00\r\n" +
                            "additionalHourTax : 5.00\r\n" +
                            "dayTax : 30.00\r\n" +
                            "additionalDayTax : 20.00\r\n" +
                            "monthTax : 350.00");
            bw.close();
        });

    }

    @AfterAll
    static void deleteBillCalculatorConfig() {
        path.toFile().delete();
    }

    @Test
    void shouldCalculateTheCorrectBill() {
        BillCalculatorFactory factory = BillCalculatorFactoryImpl.getInstance();
        BillCalculator billCalculator = factory.createBillCalculator(BillingType.HOUR_BILLING);

        LocalDate date = LocalDate.of(2023, 6, 16);
        LocalTime entry = LocalTime.of(12, 15);
        LocalTime exit = LocalTime.of(15, 35);

        LocalDateTime parkingEntry = LocalDateTime.of(date, entry);
        LocalDateTime parkingExit = LocalDateTime.of(date, exit);
        Bill bill = billCalculator.getBillFor(parkingEntry, parkingExit);
        System.out.println(bill.getDescription());

        float expected = 10 + (2 * 5) + (5.00f / 4);
        float actual = bill.getTotal();
        assertTrue(Math.abs(expected - actual) < 0.01);
    }

    @Test
    void shouldCalculateLessThanHourBill() {
        BillCalculatorFactory factory = BillCalculatorFactoryImpl.getInstance();
        BillCalculator billCalculator = factory.createBillCalculator(BillingType.HOUR_BILLING);

        LocalDate date = LocalDate.of(2023, 6, 16);
        LocalTime entry = LocalTime.of(12, 15);
        LocalTime exit = LocalTime.of(13, 00);

        LocalDateTime parkingEntry = LocalDateTime.of(date, entry);
        LocalDateTime parkingExit = LocalDateTime.of(date, exit);
        Bill bill = billCalculator.getBillFor(parkingEntry, parkingExit);
        System.out.println(bill.getDescription());

        float expected = 10;
        float actual = bill.getTotal();
        assertTrue(Math.abs(expected - actual) < 0.01);
    }

    @Test
    void shouldCalculateLessThanDayBill() {
        BillCalculatorFactory factory = BillCalculatorFactoryImpl.getInstance();
        BillCalculator billCalculator = factory.createBillCalculator(BillingType.DAY_BILLING);

        LocalDate date = LocalDate.of(2023, 6, 16);
        LocalTime entry = LocalTime.of(12, 30);
        LocalTime exit = LocalTime.MIDNIGHT;

        LocalDateTime parkingEntry = LocalDateTime.of(date, entry);
        LocalDateTime parkingExit = LocalDateTime.of(date, exit);
        Bill bill = billCalculator.getBillFor(parkingEntry, parkingExit);
        System.out.println(bill.getDescription());

        float expected = 30;
        float actual = bill.getTotal();
        assertTrue(Math.abs(expected - actual) < 0.01);
    }

    @Test
    void shouldCalculateMoreThanDayBill() {
        BillCalculatorFactory factory = BillCalculatorFactoryImpl.getInstance();
        BillCalculator billCalculator = factory.createBillCalculator(BillingType.DAY_BILLING);

        LocalDate dateEntry = LocalDate.of(2023, 6, 16);
        LocalTime entry = LocalTime.of(12, 30);
        LocalDate dateExit = LocalDate.of(2023, 6, 17);
        LocalTime exit = LocalTime.of(19, 00);

        LocalDateTime parkingEntry = LocalDateTime.of(dateEntry, entry);
        LocalDateTime parkingExit = LocalDateTime.of(dateExit, exit);
        Bill bill = billCalculator.getBillFor(parkingEntry, parkingExit);
        System.out.println(bill.getDescription());

        float expected = 30 + 20;
        float actual = bill.getTotal();
        assertTrue(Math.abs(expected - actual) < 0.01);
    }

    @Test
    void shouldCalculateMonthBill() {
        BillCalculatorFactory factory = BillCalculatorFactoryImpl.getInstance();
        BillCalculator billCalculator = factory.createBillCalculator(BillingType.MONTH_BILLING);

        LocalDateTime parkingDateEntry = LocalDateTime.of(2023, 6, 16, 1, 0, 0);
        LocalDateTime parkingDateExit = LocalDateTime.of(2023, 7, 16, 1, 0, 0);
        Bill bill = billCalculator.getBillFor(parkingDateEntry, parkingDateExit);
        System.out.println(bill.getDescription());

        float expected = 350;
        float actual = bill.getTotal();
        assertTrue(Math.abs(expected - actual) < 0.01);
    }

    @Test
    void shouldCalculateMonthLessThan15DaysBill() {
        BillCalculatorFactory factory = BillCalculatorFactoryImpl.getInstance();
        BillCalculator billCalculator = factory.createBillCalculator(BillingType.MONTH_BILLING);

        LocalDateTime parkingDateEntry = LocalDateTime.of(2023, 6, 1, 0, 0, 0);
        LocalDateTime parkingDateExit = LocalDateTime.of(2023, 6, 14, 0, 0, 0);
        Bill bill = billCalculator.getBillFor(parkingDateEntry, parkingDateExit);
        System.out.println(bill.getDescription());

        float expected = (13f/30f)*350;
        float actual = bill.getTotal();
        assertTrue(Math.abs(expected - actual) < 0.01);
    }

    @Test
    void shouldCalculateMonthMoreThan15DaysBill() {
        BillCalculatorFactory factory = BillCalculatorFactoryImpl.getInstance();
        BillCalculator billCalculator = factory.createBillCalculator(BillingType.MONTH_BILLING);

        LocalDateTime parkingDateEntry = LocalDateTime.of(2023, 6, 1, 0, 0, 0);
        LocalDateTime parkingDateExit = LocalDateTime.of(2023, 6, 20, 0, 0, 0);
        Bill bill = billCalculator.getBillFor(parkingDateEntry, parkingDateExit);
        System.out.println(bill.getDescription());

        float expected = (19f / 30f) * 350;
        float actual = bill.getTotal();
        assertTrue(Math.abs(expected - actual) < 0.01);
    }

}
