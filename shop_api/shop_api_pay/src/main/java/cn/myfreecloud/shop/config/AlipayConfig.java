package cn.myfreecloud.shop.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

	// ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016091500516568";

	// 商户私钥，您的PKCS8格式RSA2私钥
	public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCCEIbK6RNnV/6geWDjB2f8/qyTrcbcwwiDyEmxXtXNJsnU2uTia4akvgyp2Ph2mLvqjKmqgQ5LdlPoKJnndZf9fbdsvUSvVuSx1fK5ywt08UEkq1Yr3aDO/eO4HC6mEe1fYqrnuEN2Jxdii1pYjcvbHJxQSZxqIdCWhRnWhSKC6FwVcWVdizIv72omhMI/bo+mlV5PdNMsWh2OWH4frh/6UYugxxhkMBDikgsICvuQqinCwaokfEZYfRLN7c1tthZZFR4zGvwj6l/3Kz4SyXQGjOylNqt3ExB+eYePNanTn1yh1KyTMBy+9g/fuBtg90bt5n7381fUpiada9kGI+dnAgMBAAECggEAL44PWr1hhNWdmdsHW7rngI81VkGhhjIhON3QY/PCxGG8RgFK0qZx8mYyhx93qRUrEBUiK99H9Lts7mN58jECw4JlwHJrXJiuwX7yYpAkvjs/kEK9HQjJtSULT9OuErZXThrrVhH/SIH+gN9mxx4BPTn2xTxE3mFCvOYpjk/bUHVSyBla72KJ7Se7TN63KB1UBPZiCFcDm4AY8xZ14c0BCA9BQg4ZiVfo1D4UklGhcOmVrxzLhWAf4T7BJbMbLWicDb20Nft6xysb8/jaWbSBXk52kqwxLhq3JFPwvNY4tXG3Ap82pPvkgtWu1HszvvDPI6B0AXaC/YIYZH1ohhTXMQKBgQDH5g9A3yq+nDiq6uWxB7A0h2GzQE8+zlXynbGJHIYlE6JfG2mbFE+/t6xM3I27v1YaqCStKZiSmlam/qZR6yCdK+sC0G34BFuGaCbAkHmuVJ3Ey42UwhH68S7/2dFBoktYwgDfaAZQgT5iiWz5QoVxlEjKWIMyAayMKYgDTAvl0wKBgQCmkSh2JquQfXPyyRJitAf8Wvuciws2id1nI9qtN0LfWPPrW8Uq1zjGeL/qDcPvNwZs+9xbQAmSm+nBbE3Gwn7O+7O7v9cJhbG6/ff3Nd9DPWZEqZOCam8XJK/y20mNpyRnfATYz8Up8x9lmxL04hOU/M0qfCfFtuMP4WChMMYXnQKBgFpoKi3kwKhEbzyd0BQorgCT2hP/2ddF6ok32LtyMxlBsAXjl/eTUB4dK8f3WAK5nm+f7DaEwYRMWTRdp0sVVELBQiCbs6CgF+TJ5fzhWqcFjfUbOoU04Hzl56O/F2zfwcP6ufpQScrPKS/Xh3mUJFYiAmnBblSHfUpG3kTfd+IRAoGABuM9vxT+Ry1I3CNAZWB4bBD/EowVXJ36z8cFxd/hHgc5nuPelVTs/hgEJ5OGJKAr3dnUVNmjC7LyyZLG7I8iW0DBP+RA6h3jKY5S8KADqW4iinhQpzXrfhOASxSSSA8/TCAuuyBsUL3C8JqnAAVRNfn2KemUY3KalQ+GxWOU7WkCgYAM6C3NUbifxtHSkLKa8enjyetkObRJO/ayQGcvgaD8GoIAFee8JMrmsQOKKwzEN2vzm5DK4Z8jBl4UzgazlmoaZETkHZhq55OH6rglJj8AtSFIaBywn06gz7rtYVqtvyExRzqiJ3rJ8/7dB6fvXYnR3hV0Aegqu3gRyug982luNA==";

	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm
	// 对应APPID下的支付宝公钥。
	public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApcPHiLpBpHrJ+mV7s97DbrN5r0wmj8/rk3XnvGr7Dm1OLSv+HE+L9zqK3zWsHDdo4JY5L7vMR42s3cEcpbUjoEQW+z6LCGEpQ4fESwmn/pJ6nZVJkTnlQNWf59e970X5NW8tC1LJKPfBFlZcaadI0tX2sI5V5ESMy+9Iqrd4cxIt7if4VfNPu72ma5x3y2/qfmmvFkT2OH22bmdC1MA/5FdaTWT3iUArJoYV1+c0mX4HKTJ/gfKh7I3lChUbQ8H6RlvkjfkKrF+sCVdIP20gmlz7VpVU640tZ9La3hJXhjJ4fRt80vOTe6F8pGKUop6P7sKOhh5m4nx9dSzK7cUQdQIDAQAB";

	// 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "https://zfb.free.qydev.com/callBack/asynCallBack";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "https://zfb.free.qydev.com/callBack/synCallBack";

	// 签名方式
	public static String sign_type = "RSA2";

	// 字符编码格式
	public static String charset = "utf-8";

	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

	// 支付宝网关
	public static String log_path = "C:\\";

	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	/**
	 * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	 * 
	 * @param sWord
	 *            要写入日志里的文本内容
	 */
	public static void logResult(String sWord) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
			writer.write(sWord);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
