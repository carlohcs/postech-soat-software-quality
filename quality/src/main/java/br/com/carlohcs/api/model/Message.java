package br.com.carlohcs.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GenericGenerator(name = "uuid")
    private UUID id;

    @Column(nullable = false)
    @NotEmpty(message = "User could not be empty")
    private String username;

//    @Column(nullable = false)
    @NotEmpty(message = "Message could not be empty")
    private String content;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSS")
    private LocalDateTime createdAt;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSS")
    private LocalDateTime updatedAt;

    @Default
    private int likes = 0;

    @PrePersist
    public void prePersist() {
        var timestamp = LocalDateTime.now();
        createdAt = timestamp;
        updatedAt = timestamp;
    }
}
