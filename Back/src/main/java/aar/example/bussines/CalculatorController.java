package aar.example.bussines;

import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

	@CrossOrigin
	@RequestMapping(value = "plus", method = RequestMethod.POST)
	public Integer sumar(@RequestBody PlusRequest request) throws AuthenticationException {
		return request.getNumber1() + request.getNumber2();
	}
}
