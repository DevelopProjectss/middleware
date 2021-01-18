package maven.springcloud.quickstart.feign.example;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.wyyt.tool.rpc.Result;

import java.io.IOException;
import java.util.concurrent.Future;

/**
 * SpringCloud服务消费方代码, 建议服务提供方将此代码封装到jar包中, 这样, 服务消费方直接注入即可
 * <p>
 *
 * @author Ning.Zhang(Pegasus)
 * *****************************************************************
 * Name               Action            Time          Description  *
 * Ning.Zhang       Initialize       01/01/2021       Initialize   *
 * *****************************************************************
 */
@Api("Feign服务示例说明")
@FeignClient(value = "maven-springcloud-quickstart", fallbackFactory = AFeignFallback.class)
public interface AFeign {
    @ApiOperation(value = "feign同步调用示例")
    @ApiImplicitParam(name = "value", value = "参数A", required = true, dataType = "String")
    @GetMapping(path = "/feign/{value}")
    Result<String> invoke(@PathVariable(value = "value") final String value);

    @ApiOperation(value = "feign异步调用示例")
    @ApiImplicitParam(name = "value", value = "参数B", required = true, dataType = "String")
    @GetMapping(path = "/feign-async/{value}")
    Future<Result<String>> invokeAsync(@PathVariable(value = "value") final String value);

    @ApiOperation(value = "feign多线程调用示例")
    @ApiImplicitParam(name = "value", value = "参数C", required = true, dataType = "String")
    @GetMapping(path = "/feign-thread/{value}")
    Result<String> invokeThread(@PathVariable(value = "value") final String value);

    // 传文件
    @ApiOperation(value = "feign文件上传测试")
    @ApiImplicitParam(name = "file", value = "参数D", required = true, dataType = "MultipartFile")
    @PostMapping(value = "feign-uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result<String> uploadFile(@RequestPart("file") MultipartFile file) throws IOException;
}