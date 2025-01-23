package com.example.hotelmanagement.web.ExceptionHandler;


import com.example.hotelmanagement.models.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoGuestsException.class)
    public ResponseEntity<String> handleNoGuestsException(NoGuestsException exception) {
        return new ResponseEntity<>("No Guests Found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoGuestException.class)
    public ResponseEntity<String> handleNoGuestException(NoGuestException exception) {
        String message = "No Guest Found with ID: " + exception.getGuestId();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoEmployeesException.class)
    public ResponseEntity<String> handleNoGuestsException(NoEmployeesException exception) {
        return new ResponseEntity<>("No Employees Found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoEmployeeFoundException.class)
    public ResponseEntity<String> handleNoEmployeeException(NoEmployeeFoundException exception) {
        String message = "No Employee Found with ID: " + exception.getEmployeeId();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoReservationsException.class)
    public ResponseEntity<String> handleNoReservationsExceptions(NoReservationsException exception) {
        return new ResponseEntity<>("No Reservations Found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoReservationFoundException.class)
    public ResponseEntity<String> handleNoReservationException(NoReservationFoundException exception, WebRequest webRequest) {
        String message = "No Reservation Found with ID: " + exception.getReservationId();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoRoomsException.class)
    public ResponseEntity<String> handleNoRoomsExceptions(NoRoomsException exception){
        return new ResponseEntity<>("No Rooms Found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoRoomException.class)
    public ResponseEntity<String> handleNoRoomException(NoRoomException exception){
        String message = "No Room Found with ID: " + exception.getRoomId();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoHotelsException.class)
    public ResponseEntity<String> handleNoHotelsException(NoHotelsException exception){
        return new ResponseEntity<>("No Hotels Found.",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoHotelException.class)
    public ResponseEntity<String> handleNoHotelsException(NoHotelException exception){
        String message = "No Hotel Found with ID: " + exception.getHotelId();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoomSaveException.class)
    public ResponseEntity<String> handleRoomSaveException(RoomSaveException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ReservationSaveException.class)
    public ResponseEntity<String> handleRoomSaveException(ReservationSaveException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HotelDeleteException.class)
    public ResponseEntity<String> handleHotelDeleteException(HotelDeleteException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HotelSaveException.class)
    public ResponseEntity<String> handleHotelSaveException(HotelSaveException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeSaveException.class)
    public ResponseEntity<String> handleEmployeeSaveException(EmployeeSaveException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(GuestSaveException.class)
    public ResponseEntity<String> handleGuestSaveException(GuestSaveException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
