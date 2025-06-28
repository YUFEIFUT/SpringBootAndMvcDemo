package com.fei.controller.stream;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

/**
 * 聊天流控制器 - 基于Spring MVC的服务器发送事件(SSE)实现
 * <p>
 * 该类演示了如何使用Spring MVC的StreamingResponseBody实现服务器向客户端的实时数据流推送。
 * 主要用于聊天应用、实时通知、数据流处理等需要服务器主动推送数据的场景。
 * <p>
 * 核心原理：
 * StreamingResponseBody 是一个函数式接口，其内部通过 OutputStream 将数据逐步写入响应流。
 * Spring 在处理该返回值时会延迟执行该函数，直到响应提交前才调用 writeTo(OutputStream) 方法。
 * 每次写入后调用 flush() 强制刷新缓冲区，使客户端能实时接收内容。
 * <p>
 * 使用场景：
 * 1. 聊天应用的消息推送
 * 2. 实时数据监控和展示
 * 3. 文件上传进度反馈
 * 4. 长时间运行任务的进度更新
 * 5. 实时日志流展示
 * <p>
 * 优点：
 * 1. 实时性好：数据可以立即推送给客户端，无需客户端轮询
 * 2. 资源效率高：相比轮询方式，减少了不必要的HTTP请求
 * 3. 用户体验佳：用户可以看到实时的数据更新
 * 4. 实现简单：基于Spring MVC，代码简洁易懂
 * 5. 兼容性好：支持所有现代浏览器
 * <p>
 * 缺点：
 * 1. 连接占用：每个客户端连接会一直占用服务器资源直到断开
 * 2. 扩展性限制：大量并发连接时可能影响服务器性能
 * 3. 网络敏感：对网络稳定性要求较高，连接断开需要重连机制
 * 4. 同步阻塞：当前实现是同步的，会阻塞线程
 * 5. 错误处理复杂：需要处理连接断开、超时等异常情况
 * <p>
 * 注意事项：
 * 1. 必须设置正确的Content-Type为text/event-stream
 * 2. 每次写入数据后要调用flush()确保数据及时发送
 * 3. 需要处理InterruptedException等异常
 * 4. 考虑添加心跳机制保持连接活跃
 * 5. 实现适当的错误处理和重连机制
 * 6. 注意内存使用，避免在流中处理大量数据
 * 7. 考虑使用异步处理避免阻塞线程池
 * 8. 添加适当的超时机制
 * <p>
 * 客户端使用示例：
 * ```javascript
 * const eventSource = new EventSource('/chat');
 * eventSource.onmessage = function(event) {
 *     console.log('收到数据:', event.data);
 * };
 * eventSource.onerror = function(event) {
 *     console.error('连接错误:', event);
 *     eventSource.close();
 * };
 * ```
 * <p>
 * 生产环境建议：
 * 1. 使用WebSocket替代SSE以获得更好的双向通信
 * 2. 考虑使用Spring WebFlux的响应式编程模型
 * 3. 实现连接池管理避免资源泄露
 * 4. 添加监控和日志记录
 * 5. 实现优雅的关闭机制
 */
@RestController
public class ChatStreamController {

    /**
     * 聊天流接口 - 模拟实时聊天消息推送
     * <p>
     * 该方法演示了如何使用StreamingResponseBody实现服务器发送事件(SSE)。
     * 服务器会每隔500毫秒向客户端推送一条消息，总共推送10条消息。
     * <p>
     * 访问方式：
     * GET /chat
     * <p>
     * 响应格式：
     * Content-Type: text/event-stream
     * 数据格式：data chunk 0, data chunk 1, ..., data chunk 9
     * <p>
     * 实现细节：
     * 1. 创建StreamingResponseBody实例，定义数据写入逻辑
     * 2. 在循环中生成数据并写入输出流
     * 3. 每次写入后调用flush()确保数据及时发送
     * 4. 使用Thread.sleep()模拟处理延迟
     * 5. 设置正确的HTTP头部信息
     * <p>
     * 异常处理：
     * - 捕获InterruptedException并转换为RuntimeException
     * - 在实际应用中应该实现更完善的错误处理机制
     * <p>
     * 性能考虑：
     * - 当前实现是同步的，会占用一个线程直到流结束
     * - 在生产环境中建议使用异步处理
     * - 考虑添加超时机制避免长时间占用资源
     * 
     * @return ResponseEntity<StreamingResponseBody> 包含流式响应体的HTTP响应
     */
    @GetMapping("/chat")
    public ResponseEntity<StreamingResponseBody> chat() {
        // 创建流式响应体，定义数据写入逻辑
        StreamingResponseBody body = outputStream -> {
            // 模拟推送10条消息
            for (int i = 0; i < 10; i++) {
                // 构造消息数据，每条消息以换行符结尾
                String data = "data chunk " + i + "\n";
                
                // 将数据写入输出流
                outputStream.write(data.getBytes(StandardCharsets.UTF_8));
                
                // 强制刷新缓冲区，确保数据立即发送到客户端
                outputStream.flush();
                
                try {
                    // 模拟处理延迟，实际应用中可能是数据库查询、API调用等
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // 线程被中断时抛出运行时异常
                    // 注意：在实际应用中应该实现更优雅的错误处理
                    throw new RuntimeException(e);
                }
            }
        };

        // 构建HTTP响应，设置正确的头部信息
        return ResponseEntity.ok()
                // 设置Content-Type为text/event-stream，这是SSE的标准MIME类型
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_EVENT_STREAM_VALUE)
                // 设置响应体
                .body(body);
    }

    /*
    SSE（Server-Sent Events，服务器推送事件） 是一种基于 HTTP 协议的单向通信机制，允许服务器主动将实时数据推送给客户端（比如我们的浏览器），而不是客户端频繁轮询请求数据。

    Spring 为 SSE 提供了专门的支持：SseEmitter，其中的send()方法用于发送数据到客户端，complete()用于正常结束连接，而completeWithError()则是错误终止连接。

    这个方案可以做成异步，即通过线程池不断的向SseEmitter中send数据。
     */
    @GetMapping("/chat1")
    public SseEmitter sse() {
        SseEmitter emitter = new SseEmitter(60_000L); // 设置超时时间

        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                for (int i = 0; i < 30; i++) {
                    emitter.send("Message " + i);
                    Thread.sleep(1000);
                }
                emitter.complete();
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });

        return emitter;
    }
}
