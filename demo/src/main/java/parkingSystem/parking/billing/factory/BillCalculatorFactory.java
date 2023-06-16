package parkingSystem.parking.billing.factory;

import parkingSystem.parking.billing.BillingType;
import parkingSystem.parking.billing.strategy.BillCalculator;

public interface BillCalculatorFactory {
    
    BillCalculator createBillCalculator(BillingType type);

    BillCalculator createBillCalculatorAsSingleton(BillingType type);
}
