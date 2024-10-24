package br.com.carlohcs.api.repository;

import br.com.carlohcs.api.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {

}
