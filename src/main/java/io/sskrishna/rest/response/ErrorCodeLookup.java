package io.sskrishna.rest.response;

import io.sskrishna.rest.exception.detail.ErrorDetail;
import io.sskrishna.rest.exception.detail.StatusErrorDetail;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorCodeLookup {
    private Map<String, ErrorDetail> errorCodeMap = new HashMap<>();

    public ErrorCodeLookup() {

    }

    public void addFromLookupFile(Resource resource) throws IOException {
        InputStream stream = resource.getInputStream();
        List<Map<String, String>> _conf = (List<Map<String, String>>) new Yaml().load(stream);
        for (Map<String, String> map : _conf) {
            this.toErrorCode(map);
        }
    }

    public ErrorDetail getErrorCode(String code) {
        ErrorDetail errorCode = this.errorCodeMap.get(code);
        if (errorCode == null) {
            throw new RuntimeException("Error code not found: " + code);
        }
        return errorCode;
    }

    private void toErrorCode(Map<String, String> map) {
        int status = this.getDefault(map, "status", 0);
        String msg = this.getDefault(map, "message", "");
        String devMsg = this.getDefault(map, "devMessage", "");
        String code = this.getDefault(map, "code", "");
        this.assetCodeIsNotEmpty(code);

        ErrorDetail errorCode = null;
        if (status == 0) {
            errorCode = new ErrorDetail(code, msg, devMsg);
        } else {
            errorCode = new StatusErrorDetail(status, code, msg, devMsg);
        }
        this.errorCodeMap.put(errorCode.getCode(), errorCode);
    }

    private String getDefault(Map<String, String> map, String key, String defaultValue) {
        String value = map.get(key);
        return StringUtils.isEmpty(value) ? defaultValue : value;
    }

    private int getDefault(Map<String, String> map, String key, int defaultValue) {
        Object value = map.get(key);
        if (value == null) return defaultValue;
        if (value instanceof Integer) {
            return (int) value;
        } else if (value instanceof String) {
            try {
                if (StringUtils.isEmpty(value)) {
                    return defaultValue;
                } else {
                    return Integer.parseInt(value.toString());
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException("Rest Error Detail status code is expected as int. but got string", e);
            }
        }
        return defaultValue;
    }

    private void assetCodeIsNotEmpty(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new RuntimeException("Error Code should not empty");
        }
    }
}