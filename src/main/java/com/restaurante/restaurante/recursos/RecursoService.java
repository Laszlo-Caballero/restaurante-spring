package com.restaurante.restaurante.recursos;

import java.io.File;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.restaurante.restaurante.recursos.entity.Recurso;
import com.restaurante.restaurante.recursos.repository.RecursoRepository;
import com.restaurante.restaurante.recursos.response.RecursoRaw;
import com.restaurante.restaurante.utils.ApiResponse;

@Service
public class RecursoService {

    private static final String FILE_DIRECTORY = System.getProperty("user.dir") + "/uploads/";

    @Autowired
    private RecursoRepository recursoRepository;

    public ResponseEntity<ApiResponse<RecursoRaw>> crearRecurso(MultipartFile file) {
        String[] fileSplit = Objects.requireNonNull(file.getOriginalFilename()).replace("", "").split("\\.");

        String fileName = fileSplit[0] + System.currentTimeMillis() + "." + fileSplit[1];

        File directory = new File(FILE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }

        try {
            file.transferTo(new File(FILE_DIRECTORY + fileName));
        } catch (Exception e) {

            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>(500, "Error al guardar el archivo", null));
        }

        var newFile = Recurso.builder()
                .nombre(fileName)
                .build();

        recursoRepository.save(newFile);

        var newFileResponse = RecursoRaw.fromEntity(newFile);

        return ResponseEntity.ok(new ApiResponse<>(200, "Archivo guardado exitosamente", newFileResponse));
    }

}
