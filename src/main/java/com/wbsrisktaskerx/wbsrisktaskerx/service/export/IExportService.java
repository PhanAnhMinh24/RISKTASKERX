package com.wbsrisktaskerx.wbsrisktaskerx.service.export;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.ExportCustomerResponse;
import java.io.IOException;

public interface IExportService {
    ExportCustomerResponse getCustomerList()throws IOException;
}
