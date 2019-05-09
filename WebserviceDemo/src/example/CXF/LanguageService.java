package example.CXF;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(serviceName="languageManager")
public interface LanguageService {
    public @WebResult(name="language")String getLanguage(@WebParam(name="position")int position);

}

