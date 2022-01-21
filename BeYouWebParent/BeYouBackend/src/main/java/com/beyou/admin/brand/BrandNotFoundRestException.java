package com.beyou.admin.brand;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Brand not founds")
public class BrandNotFoundRestException extends Exception {

}
