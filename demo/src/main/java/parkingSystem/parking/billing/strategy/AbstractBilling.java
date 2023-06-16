package parkingSystem.parking.billing.strategy;

import java.time.LocalDateTime;

import parkingSystem.parking.billing.Bill;

public abstract class AbstractBilling implements BillCalculator
{
	protected float baseTax = 0.0f;
	protected float additionalTax = 0.0f;
    
	//-----------------------------------------------------------------------------
	@Override
	public void setBaseTax(float baseTax) 
	{
		this.baseTax = baseTax;
	}
	
	//-----------------------------------------------------------------------------
	@Override
	public void setAdditionalTax(float additionalTax) 
	{
		this.additionalTax = additionalTax;
	}
	
	//-----------------------------------------------------------------------------
	@Override
	public Bill getBillFor(LocalDateTime parkingEntry, LocalDateTime parkingExit) 
	{
		return calculateBill(parkingEntry, parkingExit);
	}
	
	//-----------------------------------------------------------------------------
	protected abstract
	Bill calculateBill (LocalDateTime parkingEntry, LocalDateTime parkingExit);
}
