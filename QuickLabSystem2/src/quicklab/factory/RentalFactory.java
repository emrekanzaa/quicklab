package quicklab.factory;
import quicklab.decorator.PriorityDeliveryDecorator;
import quicklab.service.BaseRental;
import quicklab.service.RentalService;
public class RentalFactory {
    public static RentalService create(String type) {
        BaseRental base = new BaseRental();
        if ("priority".equalsIgnoreCase(type)) return new PriorityDeliveryDecorator(base);
        return base;
    }
}

// factory pattern