package quicklab.decorator;
import quicklab.service.RentalService;
public class PriorityDeliveryDecorator implements RentalService {
    private final RentalService wrapped;
    public PriorityDeliveryDecorator(RentalService w) { this.wrapped = w; }
    @Override public double calculatePrice() { return wrapped.calculatePrice() + 5.0; }
}


//decorator pattern