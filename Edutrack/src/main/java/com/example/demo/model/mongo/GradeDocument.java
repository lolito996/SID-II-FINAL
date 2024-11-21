package com.example.demo.model.mongo;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Document(collection = "grades")
@CompoundIndexes({
    @CompoundIndex(name = "studentId_idx", def = "{'studentId': 1}"),
    @CompoundIndex(name = "subjectId_idx", def = "{'subjectId': 1}")
})
@Data
public class GradeDocument {
    @Id
    private String id;
    private Integer studentId;
    private String studentName;
    private Integer subjectId;
    private String subjectName;

    // Lista de notas individuales
    private List<SubGrade> subGrades;

    private BigDecimal finalGrade; // Calculada a partir de las subGrades, o ingresada directamente
    private LocalDate gradeDate;

    // Observaciones generales
    private List<Observation> observations;

    public void addSubGrade(String subGradeName, BigDecimal newGrade, BigDecimal weight) {
        SubGrade subGrade = new SubGrade(subGradeName, newGrade, weight);
        if (subGrade.getGrade() == null) {
            subGrade.setGrade(BigDecimal.ZERO); // Asignar un valor por defecto si es null
        }
        this.subGrades.add(subGrade);
    }
    
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class SubGrade {
        private String name; // Ej: "Tarea 1", "Examen Final"
        private BigDecimal grade;
        private BigDecimal weight; // Opcional, porcentaje del peso para el cálculo final
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Observation {
        private String observation;
        private LocalDate observationDate;
    }
}
