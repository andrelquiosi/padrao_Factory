package parkingSystem.parking.billing.factory;

import java.nio.file.Path;
import java.util.Arrays;

import parkingSystem.parking.billing.BillingType;
import parkingSystem.parking.billing.strategy.BillCalculator;
import parkingSystem.parking.billing.strategy.DayBilling;
import parkingSystem.parking.billing.strategy.HourBilling;
import parkingSystem.parking.billing.strategy.MonthBilling;
import parkingSystem.util.Config;

public final class BillCalculatorFactoryImpl implements BillCalculatorFactory {

    static BillCalculatorFactory instance = null;

    static BillCalculatorFactory getInstance() {
        if (instance == null)
            instance = new BillCalculatorFactoryImpl();
        return instance;

    }

    float hourTex;
    float additionalHourTax;
    float dayTax;
    float additionalDayTax;
    float monthTax;

    private BillCalculatorFactoryImpl() {

        Path path = Path.of(System.getProperty("user.dir"), "billCalculator.cfg");

        Config config = new Config();
        config.readFromFile(path);

        String strHourTax = config.getPropertyValue("hourTax");
        hourTex = Float.parseFloat(strHourTax);
        String strAdditionalHourTax = config.getPropertyValue("additionalHourTax");
        additionalHourTax = Float.parseFloat(strAdditionalHourTax);

        String strdayTax = config.getPropertyValue("dayTax");
        dayTax = Float.parseFloat(strdayTax);
        String strAdditionaldayTax = config.getPropertyValue("additionalDayTax");
        additionalDayTax = Float.parseFloat(strAdditionaldayTax);

        String strmonthTax = config.getPropertyValue("monthTax");
        monthTax = Float.parseFloat(strmonthTax);

    }

    private BillCalculator createHourBillCalculator() {

        BillCalculator calculator = new HourBilling();
        calculator.setBaseTax(hourTex);
        calculator.setAdditionalTax(additionalHourTax);
        return calculator;
    }

    private BillCalculator createDayBillCalculator() {

        // Acessar o database ou config para buscar os valores
        BillCalculator calculator = new DayBilling();
        calculator.setBaseTax(dayTax);
        calculator.setAdditionalTax(additionalDayTax);
        return calculator;
    }

    private BillCalculator createMonthBillCalculator() {

        // Acessar o database ou config para buscar os valores
        BillCalculator calculator = new MonthBilling();
        calculator.setBaseTax(monthTax);
        return calculator;
    }

    @FunctionalInterface
    static private interface MethodFactory {
        BillCalculator create();
    }

    MethodFactory[] factories = {
            () -> createHourBillCalculator(),
            this::createDayBillCalculator,
            this::createMonthBillCalculator
    };

    @Override
    public BillCalculator createBillCalculator(BillingType type) {
        return factories[type.ordinal()].create();
    }

    BillCalculator[] instances = new BillCalculator[BillingType.values().length];

    {
        Arrays.fill(instances, null);

    }

    @Override
    public BillCalculator createBillCalculatorAsSingleton(BillingType type) {
        if (instances[type.ordinal()] == null)
            instances[type.ordinal()] = createBillCalculator(type);

        return instances[type.ordinal()];
    }

}
