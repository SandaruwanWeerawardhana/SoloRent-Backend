package edu.icet.solorent.controller;

import edu.icet.solorent.dto.Vehicle;
import edu.icet.solorent.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/solorent/vehicle")
@CrossOrigin
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping("/add")
    public void add(@RequestBody Vehicle vehicle) {
        vehicleService.add(vehicle);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("id")  Long id) {
        vehicleService.delete(id);
    }

    @PutMapping("/update")
    public void update(@RequestBody Vehicle vehicle) {
        vehicleService.update(vehicle);
    }

    @GetMapping("/get-all")
    public List<Vehicle> getAll() {
        return vehicleService.getAll();
    }

    @GetMapping("/search-by-brand")
    public  List<Vehicle>  searchByName(@RequestParam("brand") String brand) {
        return vehicleService.searchByName(brand);
    }
}
