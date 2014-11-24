import org.junit.BeforeClass;
import org.junit.Test;

import com.dinglan.weixin.api.ApiConfig;
import com.dinglan.weixin.api.ApiResult;
import com.dinglan.weixin.api.MessageApi;
import com.dinglan.weixin.kit.SignatureCheckKit;
import com.dinglan.weixin.kit.WxCryptUtil;
import com.dinglan.weixin.msg.InMsg;
import com.dinglan.weixin.msg.InMsgParaser;
import com.dinglan.weixin.msg.JsonTextMsg;
import com.dinglan.weixin.msg.OutMsgXmlBuilder;
import com.dinglan.weixin.msg.OutTextMsg;


/*
 * 测试类
 */
public class MsgTest {
	String timestamp="1416187783";
	String nonce="1766739666";
	String msg_signature="c4eb00b041686352e79086a41431eebe60b682c0";
	String echostr="PPlWjtSU58hBx/LjqqC+sGp3vYw3pvj+s5piC9IcwLrXMoATImSOX9+0Mo8eCW80UDEEUOX21WWVHZf6dtNfQw==";
	
	/*
	 * 初始化
	 */
	@BeforeClass
	public static void init(){
		ApiConfig.setAppId("wxb21adacab9c87409");
		ApiConfig.setAppSecret("22ix3QfnYy690tvDdsJN7NkoEhj485X98H9Gpn8grU7VUxLOsFaAOGPrx9gTPF0s");
		ApiConfig.setEncodingAESKey("vuSWZYdBrwWqeFEHZQVM6kFngXaz6AhRXuTwO1b1MCH");
		ApiConfig.setAgentId("1");
		ApiConfig.setToken("xad1");
	}
	/*
	 * echostr验证
	 */
	@Test
	public void validEchostr(){
        boolean isOk = SignatureCheckKit.me.checkSignature(msg_signature, timestamp, nonce,echostr);
		if (isOk){
			WxCryptUtil pc = getWxCryptUtil();
			echostr= pc.decrypt(echostr);
			System.out.println("验证成功，解密后的echostr:"+echostr);
		}else
			System.out.println("验证失败！");
	}
	/*
	 * 接收消息
	 */
	@Test
	public void receiveMsg(){
		String xml="<xml><Encrypt><![CDATA[mCi6L35PDb2EOIfQdWN1KyTAbg2EaZikBg96umG/Og0fagBzgoa7k+qGpI72FLg2BZt+hnaLTV1Y7jpZXRqqJR4YFmKQ7B00AbFuRHt+64Ds7xODi1mmhLp8CKHSqYl25ujoM9BMnChpJAbGwRdDPKc+Y4F2qX7kbwEsczWElQHmfkv2iM12AScoDLZzR8eH2/CfB9KKjPsIJ+dd017OrhQjIK1uMZ6IoND7+sJSE13N6ZRbrD4pj+yg36IJ5m/K7+vqHKJkTZ7YFnkYDhDtQjUbsKu1w+rY4AFSrRjWjFLMtHXsVY/Bn7nt9/uUemplppMoNvXRj6dZesu6fqi/FLqcHabXqycDS48QsJs3neKxQ6+Kw8WtDGqtWWVGjIXyQ2GICVYsUshoBcRVAXhLcXEou7ZHnFPSOcH8lR4lJPEMKQ9oi69JXt1pAObxqw1X8CDq2YxmxP4oqHfSuzOcyBJ94iV3JfOvZQLW3RqpmDDo9aTw67z2xy7X7rShxfHb]]></Encrypt><MsgSignature><![CDATA[f0a674c0cdcb5bfa42bbbe019c388ca0628980e7]]></MsgSignature><TimeStamp>1416467263</TimeStamp><Nonce><![CDATA[xmP0FxUsgjxiCKkh]]></Nonce></xml>";
		WxCryptUtil pc = getWxCryptUtil();
		
		//解密接收消息
		xml= pc.decrypt(msg_signature, timestamp, nonce, xml);
		InMsg inMsg = InMsgParaser.parse(xml); 
		
		OutTextMsg outMsg= new OutTextMsg(inMsg);
		outMsg.setContent("收到");
		
		//加密返回消息
		String outMsgXml = OutMsgXmlBuilder.build(outMsg);
		String destMsgXml= pc.encrypt(outMsgXml);
		
		System.out.println(destMsgXml);
	}
	/*
	 * 发送消息
	 */
	@Test
	public void sendMsg(){
		
		JsonTextMsg msg=new JsonTextMsg();
		msg.agentid=ApiConfig.getAgentId();
		msg.touser="15102828125";
		msg.content="hello";
		
		ApiResult ret =MessageApi.sendMsg(msg);
		System.out.println(ret.getJson());
	}
	
	private WxCryptUtil cryptUtil=null;
	private WxCryptUtil getWxCryptUtil(){
		if(cryptUtil==null){
			cryptUtil=new WxCryptUtil(ApiConfig.getToken(),ApiConfig.getEncodingAESKey(),ApiConfig.getAppId());
		}
		return cryptUtil;
	}
}
