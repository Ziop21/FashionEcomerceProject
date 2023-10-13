package project.fashionecommerce.backend.fashionecommerceproject.controller.size;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecommerce.backend.fashionecommerceproject.controller.size.models.SizeRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.size.models.SizeResponse;

@RequestMapping("/api/admin/size/{sizeId}")
public interface SizeAPI {
    @PutMapping
    void update(@PathVariable String sizeId, @RequestBody @Valid SizeRequest sizeRequest);

    @GetMapping
    ResponseEntity<SizeResponse> findById(@PathVariable String sizeId);

    @DeleteMapping
    void delete(@PathVariable String sizeId);
}
