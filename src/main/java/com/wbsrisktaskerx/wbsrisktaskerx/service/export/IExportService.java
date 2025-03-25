package com.wbsrisktaskerx.wbsrisktaskerx.service.export;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface IExportService {
    ByteArrayInputStream getCustomerList()throws IOException;
}
