package be.spring.app.controller.REST;

import be.spring.app.controller.AbstractSecurityController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User: Tom De Dobbeleer
 * Date: 1/20/14
 * Time: 1:43 PM
 * Remarks: none
 */
@RequestMapping("/api/v1")
public abstract class AbstractRestController extends AbstractSecurityController {
}
