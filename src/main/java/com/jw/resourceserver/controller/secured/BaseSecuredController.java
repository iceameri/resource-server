package com.jw.resourceserver.controller.secured;

import com.jw.resourceserver.controller.BaseController;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class BaseSecuredController extends BaseController {

    public static final String SECURED_API_PREFIX = BaseController.API_PREFIX + "/opened";
}
