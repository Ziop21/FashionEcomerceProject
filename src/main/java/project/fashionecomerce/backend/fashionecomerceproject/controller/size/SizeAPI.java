package project.fashionecomerce.backend.fashionecomerceproject.controller.size;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecomerce.backend.fashionecomerceproject.controller.size.models.SizeRequest;
import project.fashionecomerce.backend.fashionecomerceproject.controller.size.models.SizeResponse;

@RequestMapping("/size/{sizeId}")
public interface SizeAPI {
    @PutMapping
    ResponseEntity<SizeResponse> update(@PathVariable String sizeId, @RequestBody @Valid SizeRequest sizeRequest);

    @GetMapping
    ResponseEntity<SizeResponse> findById(@PathVariable String sizeId);

    @DeleteMapping
    ResponseEntity<SizeResponse> delete(@PathVariable String sizeId);
}
