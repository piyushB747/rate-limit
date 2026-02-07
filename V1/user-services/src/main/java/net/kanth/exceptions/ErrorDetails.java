package net.kanth.exceptions;

import java.time.LocalDateTime;

public record ErrorDetails(String message,String status,LocalDateTime timeStamp,String path) {

}
