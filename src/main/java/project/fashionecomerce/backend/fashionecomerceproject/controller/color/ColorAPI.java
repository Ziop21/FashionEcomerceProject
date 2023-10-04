package project.fashionecomerce.backend.fashionecomerceproject.controller.color;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.fashionecomerce.backend.fashionecomerceproject.controller.color.models.ColorRequest;
import project.fashionecomerce.backend.fashionecomerceproject.controller.color.models.ColorResponse;

@RequestMapping("/color/{colorId}")
public interface ColorAPI {
    @PutMapping
    ResponseEntity<ColorResponse> update(@PathVariable String colorId, @RequestBody @Valid ColorRequest colorRequest);
    @GetMapping
    ResponseEntity<ColorResponse> findById(@PathVariable String colorId);
    @DeleteMapping
    ResponseEntity<ColorResponse> delete(@PathVariable String colorId);
}
