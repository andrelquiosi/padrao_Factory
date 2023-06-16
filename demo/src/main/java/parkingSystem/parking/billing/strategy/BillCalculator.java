package parkingSystem.parking.billing.strategy;

import java.time.LocalDateTime;

import parkingSystem.parking.billing.Bill;

public 
interface BillCalculator
{
    void setBaseTax(float baseTax);
    
    void setAdditionalTax(float additionalTax);
    
    Bill getBillFor(LocalDateTime parkingEntry, LocalDateTime parkingExit);
}