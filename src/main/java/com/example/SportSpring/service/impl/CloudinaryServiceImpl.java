package com.example.SportSpring.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.SportSpring.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;

        Map<String, Object> res = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "public_id", UUID.randomUUID().toString(),
                        "resource_type", "auto",
                        "unique_filename", false,
                        "overwrite", true
                )
        );

        Object url = res.get("secure_url");
        if (url == null) url = res.get("url");
        return url != null ? url.toString() : null;
    }

    @Override
    public String uploadFile(MultipartFile file, String folder) throws IOException {
        if (file == null || file.isEmpty()) return null;

        LocalDate t = LocalDate.now();
        String base = (folder == null || folder.isBlank()) ? "misc" : folder;
        String datedFolder = String.format("%s/%d/%02d", base, t.getYear(), t.getMonthValue());

        Map<String, Object> res = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "folder", datedFolder,
                        "public_id", UUID.randomUUID().toString(),
                        "resource_type", "auto",
                        "unique_filename", false,
                        "overwrite", true
                )
        );

        Object url = res.get("secure_url");
        if (url == null) url = res.get("url");
        return url != null ? url.toString() : null;
    }

    // === Upload nhiều file ===
    @Override
    public List<String> uploadFiles(List<MultipartFile> files, String folder) throws IOException {
        List<String> out = new ArrayList<>();
        if (files == null) return out;
        for (MultipartFile f : files) {
            String u = uploadFile(f, folder);
            if (u != null) out.add(u);
        }
        return out;
    }
}
