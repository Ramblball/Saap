package model;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Класс модели данных сообщения
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message {

    String senderId;
    String receiverId;
    String senderName;
    String receiverName;
    String message;
}
