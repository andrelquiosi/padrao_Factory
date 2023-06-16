package parkingSystem.parking.billing.factory;

import java.util.Arrays;

import parkingSystem.parking.billing.BillingType;
import parkingSystem.parking.billing.strategy.BillCalculator;
import parkingSystem.parking.billing.strategy.DayBilling;
import parkingSystem.parking.billing.strategy.HourBilling;
import parkingSystem.parking.billing.strategy.MonthBilling;

public final class BillCalculatorFactoryImpl implements BillCalculatorFactory {

    static BillCalculatorFactory instance = null;

    public BillCalculatorFactory geInstance() {
        if (instance == null)
            instance = new BillCalculatorFactoryImpl();
        return instance;

    }

    private BillCalculator createHourBillCalculator() {

        // Acessar o database ou config para buscar os valores
        BillCalculator calculator = new HourBilling();
        calculator.setBaseTax(10.00f);
        calculator.setAdditionalTax(5.00f);
        return calculator;
    }

    private BillCalculator createDayBillCalculator() {

        // Acessar o database ou config para buscar os valores
        BillCalculator calculator = new DayBilling();
        calculator.setBaseTax(30.00f);
        calculator.setAdditionalTax(20.00f);
        return calculator;
    }

    private BillCalculator createMonthBillCalculator() {

        // Acessar o database ou config para buscar os valores
        BillCalculator calculator = new MonthBilling();
        calculator.setBaseTax(350.00f);
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
