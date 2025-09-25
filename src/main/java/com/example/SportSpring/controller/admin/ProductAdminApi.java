//package com.example.SportSpring.controller.admin;
//
//import com.example.SportSpring.dto.request.ProductRequest;
//import com.example.SportSpring.dto.request.SearchRequest;
//import com.example.SportSpring.dto.response.ProductResponse;
//import com.example.SportSpring.enums.StatusEnum;
//import com.example.SportSpring.service.ImageService;
//import com.example.SportSpring.service.ProductService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.net.URI;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/admin/products")
//@RequiredArgsConstructor
//public class ProductAdminApi {
//
//    private final ProductService productService;
//    private final ImageService imageService;
//
//    /**
//     * List + filter + paging + sort
//     * FE gửi sort dạng "field,asc|desc"
//     * Ví dụ: /api/admin/products?name=abc&status=Active&page=0&size=10&sort=id,asc
//     */
//    @GetMapping
//    public Page<ProductResponse> list(
//            @RequestParam(defaultValue = "") String name,
//            @RequestParam(defaultValue = "Active") StatusEnum status,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "id,asc") String sort
//    ) {
//        String[] s = sort.split(",", 2);
//        String sortField = s.length > 0 ? s[0] : "id";
//        String sortDir   = (s.length > 1 ? s[1] : "asc").toLowerCase();
//        return productService.getAllProductsPaginated(page, size, sortField, sortDir, name, status);
//    }
//
//    /** Lấy chi tiết sản phẩm */
//    @GetMapping("/{id}")
//    public ProductResponse getById(@PathVariable Long id) {
//        return productService.getProductById(id);
//    }
//
//    /** Tạo sản phẩm: nhận JSON thuần (giữ đúng hợp đồng đang chạy trước đây) */
//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest payload) {
//        ProductResponse created = productService.createProduct(payload);
//        return ResponseEntity.created(URI.create("/api/admin/products/" + created.getId())).body(created);
//    }
//
//    /** Cập nhật sản phẩm bằng JSON */
//    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ProductResponse update(@PathVariable Long id, @RequestBody ProductRequest payload) {
//        return productService.updateProduct(id, payload);
//    }
//
//    /** Soft delete (đổi trạng thái về Closed) */
//    @PostMapping("/soft-delete/{id}")
//    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
//        productService.softDeleteProduct(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    /** Restore (đổi trạng thái về Active) */
//    @PostMapping("/restore/{id}")
//    public ResponseEntity<Void> restore(@PathVariable Long id) {
//        productService.restoreProduct(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    /** Delete cứng */
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        productService.deleteProduct(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    /**
//     * Upload ảnh cho sản phẩm đã tạo (PHẦN 2)
//     * FE gửi multipart với field tên "images" (đổi thành "files" nếu BE bạn đang dùng tên đó).
//     * Ví dụ FE: POST /api/admin/products/{id}/images (FormData: images[]=file1, images[]=file2)
//     */
//    @PostMapping(path = "/{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Void> uploadImages(
//            @PathVariable Long id,
//            @RequestPart("images") List<MultipartFile> images
//    ) {
//        // Tuỳ vào ImageService của bạn: tạo record + upload Cloudinary
//        imageService.createImage(images, id);
//        return ResponseEntity.noContent().build();
//    }
//
//    /**
//     * (Tuỳ chọn) Upload 1 ảnh đơn
//     * Nếu FE cần endpoint nhận 1 file duy nhất:
//     */
//    @PostMapping(path = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Void> uploadOneImage(
//            @PathVariable Long id,
//            @RequestPart("file") MultipartFile file
//    ) {
//        imageService.createImage(file, id);
//        return ResponseEntity.noContent().build();
//    }
//
//    /** Xoá ảnh theo imageId */
//    @DeleteMapping("/image/{imageId}")
//    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
//        imageService.deleteImage(imageId);
//        return ResponseEntity.noContent().build();
//    }
//
//    /** Search nâng cao (body là SearchRequest) */
//    @PostMapping(path = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public Page<ProductResponse> search(
//            @RequestBody SearchRequest request,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "12") int size
//    ) {
//        return productService.searchProduct(request, page, size);
//    }
//
//    /** Top sale */
//    @GetMapping("/sale")
//    public List<ProductResponse> topSale() {
//        return productService.getProductSale();
//    }
//
//    /** Newest */
//    @GetMapping("/newest")
//    public List<ProductResponse> newest() {
//        return productService.getProductNewest();
//    }
//
////    /** (Tuỳ chọn) Sao chép sản phẩm nếu bạn cần dùng trên FE */
////    @PostMapping("/copy/{id}")
////    public ProductResponse copy(@PathVariable Long id) {
////        return productService.copyProduct(id); // implement trong ProductServiceImpl
////    }
//}
