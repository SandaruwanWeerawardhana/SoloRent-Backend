package edu.icet.solorent.controller;

import edu.icet.solorent.dto.Payment;
import edu.icet.solorent.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/solorent/payment")
@CrossOrigin
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/add")
    public void add(@RequestBody Payment payment) {
        paymentService.add(payment);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("id")  Long id) {
        paymentService.delete(id);
    }

    @PutMapping("/update")
    public void update(@RequestBody Payment payment) {
        paymentService.update(payment);
    }

    @GetMapping("/get-all")
    public List<Payment> getAll() {
        return paymentService.getAll();
    }

}
