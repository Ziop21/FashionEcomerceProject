package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.color;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.color.models.ColorRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.color.models.ColorResponse;

@RequestMapping("/api/admin/manager/color/{colorId}")
public interface ColorAPI {
    @PutMapping
    void update(@PathVariable String colorId, @RequestBody @Valid ColorRequest colorRequest);
    @GetMapping
    ResponseEntity<ColorResponse> findById(@PathVariable String colorId);
    @DeleteMapping
    void delete(@PathVariable String colorId);
}
