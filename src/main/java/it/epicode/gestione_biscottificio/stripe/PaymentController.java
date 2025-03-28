package it.epicode.gestione_biscottificio.stripe;

import com.stripe.exception.StripeException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final StripeService stripeService;

    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/create-payment-intent")
    public String createPaymentIntent(@RequestParam Long amount) throws StripeException {
        return stripeService.createPaymentIntent(amount, "eur");
    }
}
