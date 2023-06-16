package parkingSystem.parking.billing.strategy;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import parkingSystem.parking.billing.Bill;
import parkingSystem.parking.billing.SimpleBillBuilder;

public class MonthBilling extends AbstractBilling
{
   //-------------------------------------------------------------------------------
   public float getMonthTax()
   {
      return super.baseTax;
   }
	
   //-------------------------------------------------------------------------------
   @Override
   public void setAdditionalTax(float additionalTax) 
   {
	   throw new RuntimeException ("Month billing does not support additional tax");
   }
	
   //-------------------------------------------------------------------------------
   @Override
   protected Bill calculateBill(LocalDateTime parkingEntry, LocalDateTime parkingExit)
   {
	   SimpleBillBuilder billBuilder = new SimpleBillBuilder();
   	   billBuilder
   	      .parkingEntry(parkingEntry)
   	      .parkingExit(parkingExit);
      
   	   long completeMonths = ChronoUnit.MONTHS.between(parkingEntry, parkingExit);
       LocalDateTime completeMonthsDate = parkingEntry.plusMonths(completeMonths);
       long additionalDays = ChronoUnit.DAYS.between(completeMonthsDate, parkingExit);
      
       float totalMonths = completeMonths + additionalDays / 30.0f; 
       String description = String.format("%.2d Months", totalMonths);
       float value = totalMonths * getMonthTax();
       billBuilder.appendDescriptionItem(description, value);
       
       return billBuilder.build();
    }
}
