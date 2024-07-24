
import java_modules.globalThis.console;
import java_modules.globalThis.Date;
import java_modules.globalThis.fetch;
import java_modules.httpServer.SimpleHttpServer;
import java_modules.mysqlClient.JDBCApp;

public class app {
    public static void main(String[] args) {
		var dateNum = Date.valueOf();
		console.log("Date: " + dateNum);
        var number = 666;
        console.log("Hello, world？", number);
        console.log("❤️启动http服务器");
		try{
			var http = SimpleHttpServer.onStart();
		}catch(Exception e){
			console.log(e);
		};
		console.log("🚩启动http客户端");
		var res = fetch._fetch("https://ip.3322.net/","GET");
        console.log("返回值:");
		console.log(res);
        JDBCApp jdbc = new JDBCApp();
        jdbc.queryDatabase();
    }
}
