package parkingSystem.parking.billing.strategy;

import java.time.Duration;
import java.time.LocalDateTime;

import parkingSystem.parking.billing.Bill;
import parkingSystem.parking.billing.SimpleBillBuilder;

public class HourBilling extends AbstractBilling {
    // ------------------------------------------------------
    public float getFirstHourTax() {
        return super.baseTax;
    }

    // ------------------------------------------------------
    public float getAdditionalHourTax() {
        return super.additionalTax;
    }

    // ----------------------------------------------------------------------------------
    @Override
    protected Bill calculateBill(LocalDateTime parkingEntry, LocalDateTime parkingExit) {
        SimpleBillBuilder billBuilder = new SimpleBillBuilder();
        billBuilder
                .parkingEntry(parkingEntry)
                .parkingExit(parkingExit)
                .appendDescriptionItem("First hour", getFirstHourTax());

        long minutes = Duration.between(parkingEntry, parkingExit).toMinutes();
        long hours = (minutes - 60) / 60;
        minutes = minutes % 60;
        float additionalTime = hours + ((minutes / 15) * 0.25f);

        if(hours < 1){
            return billBuilder.build();
        }
        if (additionalTime > 0) {
            float additionalValue = additionalTime * getAdditionalHourTax();
            String description = String.format("Additional %f hrs", additionalTime);
            billBuilder.appendDescriptionItem(description, additionalValue);
        }

        return billBuilder.build();
    }
}