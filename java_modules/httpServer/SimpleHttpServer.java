package java_modules.httpServer;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

public class SimpleHttpServer {
    public static Object onStart() throws IOException {
        int port = 8088; // 设置服务器端口号
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
        // 创建上下文和处理程序
        server.createContext("/", new MyHandler());
        server.createContext("/api/common/header", new HeaderHandler());
		
             // 设置线程池（可选）
        // server.setExecutor(...);
        
        // 启动服务器
        server.start();
        
        System.out.println("Server is running on port " + port);
		return true;
    }
    
    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Hello, this is the response from the server!";
            
            // 设置响应头
            exchange.getResponseHeaders().set("Content-Type", "text/plain");
            
            // 发送响应代码和内容
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    static class HeaderHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // 获取请求的头部信息
            Map<String, List<String>> headers = exchange.getRequestHeaders();

            // 构建JSON格式的响应字符串
            StringBuilder jsonResponse = new StringBuilder();
            jsonResponse.append("{");
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                String key = entry.getKey();
                List<String> values = entry.getValue();
                jsonResponse.append("\"").append(escapeJsonString(key)).append("\":");
                if (values.size() == 1) {
                    jsonResponse.append("\"").append(escapeJsonString(values.get(0))).append("\"");
                } else {
                    jsonResponse.append("[");
                    for (int i = 0; i < values.size(); i++) {
                        jsonResponse.append("\"").append(escapeJsonString(values.get(i))).append("\"");
                        if (i < values.size() - 1) {
                            jsonResponse.append(",");
                        }
                    }
                    jsonResponse.append("]");
                }
                jsonResponse.append(",");
            }
            jsonResponse.deleteCharAt(jsonResponse.length() - 1); // 移除最后一个多余的逗号
            jsonResponse.append("}");

            // 设置响应头
            exchange.getResponseHeaders().set("Content-Type", "application/json");

            // 发送响应
            String jsonResponseString = jsonResponse.toString();
            exchange.sendResponseHeaders(200, jsonResponseString.length());
            OutputStream os = exchange.getResponseBody();
            os.write(jsonResponseString.getBytes());
            os.close();
        }

        // 将JSON字符串中的特殊字符进行转义
        private String escapeJsonString(String input) {
            return input
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\b", "\\b")
                    .replace("\f", "\\f")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
        }
    }
}
