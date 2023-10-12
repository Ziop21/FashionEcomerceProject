package project.fashionecomerce.backend.fashionecomerceproject.controller.stockDiary.models;

public record StockDiaryRequest(
    String stockId,
    Long quantity,
    Long errorQuantity,
    String note
) {


}

